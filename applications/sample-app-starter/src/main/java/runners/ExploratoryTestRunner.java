package runners;

import java.util.*;

import luego.etest.ExploratoryTestManager;
import luego.runtime.application.*;
import luego.runtime.results.Result;
import luego.runtime.values.PreEvaluationError;
import luego.types.LGType;
import scala.Tuple2;
import scala.util.Either;

public class ExploratoryTestRunner {

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

    LuegoAppInfo appInfo = LuegoAppInfo.read(".");
    LuegoRunner appRunner = LuegoRunner.apply("./target/luego/app/" + appInfo.appId() + "/" + appInfo.appVersion());
    String language = "en";

    System.out.println("=====================================");
    System.out.println("Using the exploratory test tool");
    ExploratoryTestManager exploratoryTest = new ExploratoryTestManager(appRunner, "newco.crm.DiscountEligibility", language);
    exploratoryTest.play(
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
