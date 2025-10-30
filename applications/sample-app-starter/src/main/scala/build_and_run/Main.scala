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

    println("Application path = " + applicationPath.toString)
    println("---------- CLEAN ------------------------")

    LuegoAppBuilder.clean(applicationPath)
    println("----------- READ APP CONFIG -----------------------")

    val luegoAppRes: ValidationNel[String, DTLuegoApp] = DTLuegoApp.readDTLuegoApp(applicationSrcPath)
    luegoAppRes match {
      case scalaz.Failure(s) =>
        println("applicationSrcPath: " + applicationSrcPath)
        println("applicationRuntimePath: " + applicationRuntimePath)
        println("** Error with reading application config " + s)
      case scalaz.Success(luegoApp) =>
        println("App configuration has been read successfully")
        //println("typePackagePaths = " + luegoApp.typePackagePaths)
        println("----------- COMPILE -----------------------")
        val appRes = LuegoAppBuilder.build(luegoApp, true)
        appRes match {
          case scalaz.Success(rtApp) =>

            println("----------- PRERUN CHECKS -----------------------")

            if (rtApp.failingModels.nonEmpty && exitOnBuildError) {
              println("Failing models: " + rtApp.failingModels.mkString(", "))
              return // exiting the program
            }
            println("Working models: " + rtApp.workingModels.map(_.name).mkString(", "))

            for (scenario <- getScenarios) {
              println()
              rtApp.workingModels.find(_.fullName == scenario.decisionName) match {
                case Some(rta) =>
                  val artefactType = rta match {
                    case _: RTFunction => "function"
                    case _: RTDecisionModel => "decision model"
                  }
                  println(s"==== ${scenario.name} - will call $artefactType '${scenario.decisionName}'")

                case None =>
                  println(s"** Error - no runtime artefact called '${scenario.decisionName}'")
              }

              println("----------- CHECK PRESENCE OF RUNTIME ARTEFACTS -----------------------")
              val runtimeArtefactFolderPath = applicationRuntimePath / os.RelPath(scenario.decisionName.replace(".", "/"))

              val folderExists = os.exists(runtimeArtefactFolderPath) && os.isDir(runtimeArtefactFolderPath)
              println("Folder " + runtimeArtefactFolderPath.toString + " " + folderExists)

            }

          case scalaz.Failure(e) => println("** Build error: " + e)
        }

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
}