package sample;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import luego.runtime.application.LuegoAppInfo;
import luego.runtime.application.LuegoRunner;
import luego.runtime.results.*;
import luego.runtime.values.PreEvaluationError;
import luego.types.LGType;
import scala.Tuple2;
import scala.util.Either;

class Greetings_Test {
  
    static LuegoRunner appRunner;

    @BeforeAll
    static void setup() {
        System.out.println("@BeforeAll - executes once before all test methods in this class");

        LuegoAppInfo appInfo = LuegoAppInfo.read(".");
        appRunner = LuegoRunner.apply("./target/luego/app/" + appInfo.appId() + "/" + appInfo.appVersion());
    }  


    @Test
    void Greetings() {
        String parametersString = 
        """
        { 
            "the request": {
                "LGType_": "sample.greetings.Request",
                "person": {
                    "LGType_": "sample.greetings.Person",
                    "name": "Jane",
                    "honorific": []
                }
            }
        }     
        """;
     
      Either<PreEvaluationError, Tuple2<Result<?>, LGType>> evalRes = appRunner.evaluate("sample.greetings.Greetings", 
                                                                                         parametersString, "en");

      System.out.println("evalRes = " + evalRes);        
      assertTrue(evalRes.toOption().nonEmpty());
      assertEquals(ResultUtil.hasKnownValue(evalRes.toOption().get(), 
        """
        {
            "LGType_": "sample.greetings.Response",
            "message":"Hello Jane!"
        }
        """,
        appRunner.dataModel()), scala.None$.MODULE$);
    }

    @Test
    void Greetings_IncompleteData() {
        String parametersString = 
        """
        { 
            "the request": {
                "LGType_": "sample.greetings.Request"
            }
        }     
        """;
     
      Either<PreEvaluationError, Tuple2<Result<?>, LGType>> evalRes = appRunner.evaluate("sample.greetings.Greetings", 
                                                                                         parametersString, "en");

      System.out.println("evalRes = " + evalRes);        
      assertTrue(evalRes.toOption().nonEmpty());
      assertEquals(ResultUtil.hasMissingInfo(evalRes.toOption().get(), 
        """
        {
          "type":"MissingData",
          "elements":[
            {
              "target":"the request",
              "targetType":"sample.greetings.Request",
              "member":"person",
              "memberType":"sample.greetings.Person",
              "kind":"has"
            }
          ]
        }
        """,
        appRunner.dataModel()), scala.None$.MODULE$);
    }

}
