package runners;

import java.util.*;

import luego.runtime.application.*;
import luego.runtime.results.Result;
import luego.runtime.results.ResultWrite;
import luego.runtime.values.PreEvaluationError;
import luego.types.LGType;
import scala.Console;
import scala.Tuple2;
import scala.util.Either;

public class ScenarioRunner {

  public record Scenario(String name, String decisionName, String parameters) {}

  public static List<Scenario> getScenarios() {
    return Arrays.asList(new Scenario("capitalize 'Hello'", 
                              "newco.crm.capitalize", 
                                            """
                                            { "s": "Hello" }
                                            """
                                      ),
                         new Scenario("Call a decision model with complete info", 
                              "newco.crm.DiscountEligibility", 
                                            """
                                            {
                                              "request": {
                                                "LGType_": "newco.crm.Request",
                                                "customerName": "Jane",
                                                "value": 120,
                                                "products": []
                                              }
                                            }"""
                                      ),
                         new Scenario("Call a decision model with missing info", 
                              "newco.crm.DiscountEligibility", 
                                            """
                                            {
                                              "request": {
                                                "LGType_": "newco.crm.Request",
                                                "products": []
                                              }
                                            }"""
                                      ),
                         new Scenario("Call a decision model with erroneous input", 
                              "newco.crm.DiscountEligibility", 
                                            """
                                            {
                                              "request": {
                                                "LGType_": "newco.crm.Request",
                                                "customerName": 120,
                                                "products": []
                                              }
                                            }"""
                                      )

                        );
  }

  static void main(String[] args) {

    LuegoAppInfo appInfo = LuegoAppInfo.read(".");
    LuegoRunner appRunner = LuegoRunner.apply("./target/luego/app/" + appInfo.appId() + "/" + appInfo.appVersion());
    String language = "en";


    ResultWrite rw = new ResultWrite(appRunner.dataModel());

    System.out.println("=====================================");
    for (Scenario scenario: getScenarios()) {
      System.out.println();
      System.out.println("** SCENARIO " + scenario.name() + " - calling " + scenario.decisionName());
      Either<PreEvaluationError, Tuple2<Result<?>, LGType>> evalRes = appRunner.evaluate(scenario.decisionName, scenario.parameters, language);

      if (evalRes.toOption().isDefined()) {
        String resultJson = rw.toJSON(evalRes.toOption().get()._1(), evalRes.toOption().get()._2(), 2);

        if (evalRes.toOption().get()._1.isKnown())
          System.out.println(Console.BLUE() + resultJson + Console.WHITE());
        else
          System.out.println(Console.YELLOW() + resultJson + Console.WHITE());
      }
      else {
        System.out.println(Console.RED() + evalRes.left().toOption().get() + Console.WHITE());
      }

    }
  }
}
