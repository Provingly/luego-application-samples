package newco.crm

import luego.runtime.application.LuegoRunner
import luego.runtime.results.Known
import org.scalatest.funsuite.AnyFunSuite

class capitalize_Test extends AnyFunSuite {

  val appRunner: LuegoRunner = TestedApp.appRunner
  val language = "en"

  test("Capitalize OK") {
    val json = ujson.read(""" { "s": "Hello" }""").obj
    val evalRes = appRunner.evaluate("newco.crm.capitalize", json, language)
    evalRes match {
      case Left(error) => assert(false, "Unexpected error: " + error)
      case Right((Known(v), _)) =>
        assert(v == "HELLO")
    }
  }
  test("Greetings OK") {
    val json = ujson.read(""" { "s": "World" }""").obj
    val evalRes = appRunner.evaluate("newco.crm.greetings", json, language)
    evalRes match {
      case Left(error) => assert(false, "Unexpected error: " + error)
      case Right((Known(v), _)) =>
        assert(v == "Hello World!")
    }
  }
}
