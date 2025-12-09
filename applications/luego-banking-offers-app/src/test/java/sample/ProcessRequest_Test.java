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

class GenerateResponse1_Test {
  
    static LuegoRunner appRunner;

    @BeforeAll
    static void setup() {
        System.out.println("@BeforeAll - executes once before all test methods in this class");

        LuegoAppInfo appInfo = LuegoAppInfo.read(".");
        appRunner = LuegoRunner.apply("./target/luego/app/" + appInfo.appId() + "/" + appInfo.appVersion());
    }  


    @Test
    void GenerateResponse1() {
        String parametersString = 
        """
        { 
            "the request": {
                "LGType_": "sample.banking.Request",
                "date": "2025-12-10"
            },
            "the customer": {
                "LGType_": "sample.banking.Person",
                "dateOfBirth": "2000-10-10"
            }            
        }     
        """;
     
      Either<PreEvaluationError, Tuple2<Result<?>, LGType>> evalRes = appRunner.evaluate("sample.banking.ProcessRequest", 
                                                                                         parametersString, "en");

      System.out.println("evalRes = " + evalRes);        
      assertTrue(evalRes.toOption().nonEmpty());
      assertEquals(ResultUtil.hasKnownValue(evalRes.toOption().get(), 
        """
        {
          "LGType_": "sample.banking.SpecialPriceOffer",
          "percentageDiscount": 0.1,
          "validUntil": "2025-12-31"
        }
        """,
        appRunner.dataModel()), scala.None$.MODULE$);
    }
}