package runner;

import java.nio.file.Paths;
import java.util.*;

import luego.etest.ExploratoryTestManager;
import luego.runtime.application.*;
import luego.runtime.results.Result;
import luego.runtime.values.PreEvaluationError;
import luego.types.LGType;
//import os.*;
import scala.Tuple2;
import scala.util.Either;

public class Main {

  public record Scenario(String name, String decisionName, String parameters) {}

  public static List<Scenario> getScenarios() {
    return Arrays.asList(new Scenario("capitalize 'Hello'", 
                              "newco.crm.capitalize", 
                                            """
                                            { "s": "Hello" }
                                            """
                                      ),
                         new Scenario("Call a decision model", 
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
                                      )                   
                        );
  }

  static void main(String[] args) {

    System.out.println("=====================================");
    System.out.println("Getting a LuegoRunner");
    LuegoAppInfo appInfo = LuegoAppInfo.read(".");
    LuegoRunner appRunner = LuegoRunner.apply("./target/luego/app/" + appInfo.appId() + "/" + appInfo.appVersion());
    String language = "en";

    System.out.println("=====================================");
    System.out.println("Running some scenarios");
    for (Scenario scenario: getScenarios()) {
      Either<PreEvaluationError, Tuple2<Result<?>, LGType>> evalRes = appRunner.evaluate(scenario.decisionName, scenario.parameters, language);
      System.out.println("evalRes = " + evalRes);
    }

    System.out.println("=====================================");
    System.out.println("Using the exploratory test tool");
    ExploratoryTestManager etm = new ExploratoryTestManager(appRunner, "newco.crm.DiscountEligibility", language);
    etm.play(
        """
        {
          "request": {
            "LGType_": "newco.crm.Request"
          }
        }
        """
    );

  }
}
