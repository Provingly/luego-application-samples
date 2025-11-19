package newco.crm

import luego.runtime.application.LuegoRunner
import luego.runtime.errors.InputsErrorInfo
import luego.runtime.results.{Known, MissingElt, MissingGroup, ResultWrite}
import luego.runtime.values.Leg.MemberLeg
import luego.runtime.values.{DecisionNotFoundAtRuntime, InvalidJSONInput, JSONType, Leg, MultipleInputErrors, ValueWrite}
import luego.types.LGType
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

class StarterApp_DiscountEligibility_Test extends AnyFunSuite with BeforeAndAfter {

  val appRunner: LuegoRunner = TestedApp.appRunner
  val language = "en"
  val rw = ResultWrite(appRunner.dataModel)
  val vw = ValueWrite(appRunner.dataModel)

  test("Unknown decision call produces error") {
    val inputs = """{
                   |  "request": {
                   |    "LGType_": "newco.crm.Request",
                   |    "customerName": "Jane",
                   |    "value": 120,
                   |    "products": []
                   |  }
                   |}""".stripMargin
    val unknownFunName = "newco.crm.UnknownFun"
    val json = ujson.read(inputs).obj
    appRunner.evaluate(unknownFunName, json, language) match {
      case Left(error) => assert(error == DecisionNotFoundAtRuntime(unknownFunName))
      case Right((mg, _)) => assert(false, "Unexpected result - should get an error instead of " + mg)
    }

  }

  test("DiscountEligibility call with wrong input produces error") {
    val inputs = """{
                   |  "request": 42
                   |}""".stripMargin
    val json = ujson.read(inputs).obj
    appRunner.evaluate("newco.crm.DiscountEligibility", json, language) match {
      case Left(error: MultipleInputErrors) =>
        assert(error.errors.size == 1)
        assert(error.errors.head.isInstanceOf[InvalidJSONInput])
        assert(error.errors.head.asInstanceOf[InvalidJSONInput].path == Seq(MemberLeg("request")))
        assert(error.errors.head.asInstanceOf[InvalidJSONInput].expectedJSONType == JSONType.Object)
        assert(error.errors.head.asInstanceOf[InvalidJSONInput].actualJSONType == JSONType.Number)
      case Right((mg, _)) => assert(false, "Unexpected result - should get an error instead of " + mg)
    }
  }

  test("DiscountEligibility call produces result") {
    val inputs = """{
                   |  "request": {
                   |    "LGType_": "newco.crm.Request",
                   |    "customerName": "Jane",
                   |    "value": 120,
                   |    "products": []
                   |  }
                   |}""".stripMargin
    val json = ujson.read(inputs).obj
    val evalRes = appRunner.evaluate("newco.crm.DiscountEligibility", json, language)
    evalRes match {
      case Left(error) => assert(false, "Unexpected error: " + error)
      case Right((Known(v), t)) =>
        val responseJSON = vw.toJSON(v, t)
        assert(responseJSON ==
          """{"LGType_":"newco.crm.Response","message":"Hello JANE","value":220}""")
    }
  }

  test("DiscountEligibility call produces eligible outcome") {
    val inputs = """{
                   |  "request": {
                   |    "LGType_": "newco.crm.Request",
                   |    "customerName": "John",
                   |    "value": 120,
                   |    "loyaltyLevel": ["Gold"],
                   |    "products": [{
                   |      "LGType_": "newco.crm.Product",
                   |      "id": "tennis racket",
                   |      "price": 190
                   |    },
                   |    {
                   |      "LGType_": "newco.crm.Product",
                   |      "id": "tennis balls",
                   |      "price": 15
                   |    }]
                   |  }
                   |}""".stripMargin
    val json = ujson.read(inputs).obj
    val evalRes = appRunner.evaluate("newco.crm.DiscountEligibility", json, language)
    evalRes match {
      case Left(error) => assert(false, "Unexpected error: " + error)
      case Right((Known(v), t)) =>
        val responseJSON = vw.toJSON(v, t)
        assert(responseJSON ==
          """{"LGType_":"newco.crm.Response","message":"Hello JOHN","value":220}""")
    }
  }

  test("DiscountEligibility call identifies missing info") {
    val inputs =
      """{
        |  "request": {
        |    "LGType_": "newco.crm.Request"
        |  }
        |}""".stripMargin

    val json = ujson.read(inputs).obj
    appRunner.evaluate("newco.crm.DiscountEligibility", json, language) match {
      case Left(error) => assert(false, "Unexpected error: " + error)
      case Right(mg, t) =>
        val mgJSON = rw.toJSON(mg, t)
        assert(mgJSON ==
          """{"type":"MissingData","elements":[{"target":"request","targetType":"newco.crm.Request","member":"products","memberType":"List[newco.crm.Product]","kind":"has"},{"target":"request","targetType":"newco.crm.Request","member":"value","memberType":"Integer","kind":"has"},{"target":"request","targetType":"newco.crm.Request","member":"customerName","memberType":"Text","kind":"has"}]}""")
        /*assert(mg == MissingGroup(List(MissingElt("request", "newco.crm.Request", "products", "List[newco.crm.Product]", "has"),
          MissingElt("request", "newco.crm.Request", "value", "Integer", "has"),
          MissingElt("request", "newco.crm.Request", "customerName", "Text", "has"))))
        */
    }
  }
}