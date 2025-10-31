package newco.crm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import luego.build.application.LuegoAppBuilder;
import luego.runtime.application.LuegoAppInfo;
import luego.runtime.application.LuegoRunner;
import luego.runtime.results.Known;
import luego.runtime.results.Result;
import luego.runtime.values.PreEvaluationError;
import luego.types.LGType;
import os.Path;
import scala.Tuple2;
import scala.util.Either;

class DiscountEligibility_Test {
  
    static LuegoRunner appRunner;

    @BeforeAll
    static void setup() {
        System.out.println("@BeforeAll - executes once before all test methods in this class");

        java.nio.file.Path applicationPathNio = Paths.get(".").toAbsolutePath();
        
        Path applicationPath = new Path(applicationPathNio);
        LuegoAppInfo appInfo = LuegoAppBuilder.getLuegoAppInfo(applicationPath);

        java.nio.file.Path applicationRuntimePathNio = Paths.get("./target/luego/app/" + appInfo.appId() + "/" + appInfo.appVersion()).toAbsolutePath();
        Path applicationRuntimePath = new Path(applicationRuntimePathNio);
        appRunner = new LuegoRunner(applicationRuntimePath);
    }  


    @Test
    void discountEligibility() {
        String parametersString = 
        """
        { 
            "request": {
                "LGType_": "newco.crm.Request",
                "customerName": "Jane",
                "value": 120,
                "products": []
            }
        }     
        """;
     
      Either<PreEvaluationError, Tuple2<Result<?>, LGType>> evalRes = appRunner.evaluate("newco.crm.DiscountEligibility", 
                                                                                         parametersString, "en");

      System.out.println("evalRes = " + evalRes);        
      assertTrue(evalRes.toOption().nonEmpty());
      assertEquals(evalRes.toOption().get()._2().toString(), "newco.crm.Response");
      assertEquals(evalRes.toOption().get()._1().toString(), "Known(value = Struct(type = newco.crm.Response, loc = res, members = eligibilityStatus = false, message = Hello JANE, value = 220), type = class luego.runtime.values.StructVal)");
    }
}
