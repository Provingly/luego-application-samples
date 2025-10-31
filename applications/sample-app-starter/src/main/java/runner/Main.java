package runner;

import java.nio.file.Paths;
import java.util.*;

import luego.build.application.LuegoAppBuilder;
import luego.runtime.application.*;
import luego.runtime.results.Result;
import luego.runtime.values.PreEvaluationError;
import luego.types.LGType;
import os.*;
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

    System.out.println("Running ");

    java.nio.file.Path applicationPathNio = Paths.get(".").toAbsolutePath();
    
    Path applicationPath = new Path(applicationPathNio);
    LuegoAppInfo appInfo = LuegoAppBuilder.getLuegoAppInfo(applicationPath);

    java.nio.file.Path applicationRuntimePathNio = Paths.get("./target/luego/app/" + appInfo.appId() + "/" + appInfo.appVersion()).toAbsolutePath();
    Path applicationRuntimePath = new Path(applicationRuntimePathNio);
    LuegoRunner appRunner = new LuegoRunner(applicationRuntimePath);
    String language = "en";

    for (Scenario scenario: getScenarios()) {

      Either<PreEvaluationError, Tuple2<Result<?>, LGType>> evalRes = appRunner.evaluate(scenario.decisionName, scenario.parameters, language);

      System.out.println("evalRes = " + evalRes);
    }
  }
}
