package build_and_run

import luego.build.application.*
import luego.runtime.application.*
import os.{Path, RelPath}
import scalaz.ValidationNel

import cats.syntax.either._


object Main {

  val projectName = "sample-app-starter"

  case class Scenario(name:String, decisionName: String, parameters: String)

  def getScenarios: List[Scenario] =
    Scenario("capitalize 'Hello'", "newco.crm.capitalize", """ { "s": "Hello" } """) ::
    Scenario("Call a decision model", "newco.crm.DiscountEligibility",
      """{
          |  "request": {
          |    "LGType_": "newco.crm.Request",
          |    "customerName": "Jane",
          |    "value": 120,
          |    "products": []
          |  }
          |}""".stripMargin) ::
    Nil

  @main def run(): Unit = {
    println("-----------------------------------------")

    val exitOnBuildError = true
    val applicationPath: Path = os.pwd / projectName
    val applicationSrcPath: Path = applicationPath / "src/main"

    val appInfo: LuegoAppInfo = LuegoAppBuilder.getLuegoAppInfo(applicationPath)

    val applicationRuntimePath: Path = applicationPath / "target/luego/app" / RelPath(appInfo.appId) / RelPath(appInfo.appVersion)

    println("----------- RUN by loading binary files -----------------------")
    println("Runtime path = " + applicationRuntimePath)
    for (scenario <- getScenarios) {
      println("Running " + scenario.decisionName + "...")

      val appRunner = new LuegoRunner(applicationRuntimePath)

      val language = "en"
      val jsonParams: ujson.Obj = ujson.read(scenario.parameters).obj
      val evalRes = appRunner.evaluate(scenario.decisionName, jsonParams, language)

      println("evalRes = " + evalRes)
    }
  }
}