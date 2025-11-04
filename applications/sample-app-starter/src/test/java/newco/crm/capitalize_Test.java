package newco.crm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import luego.runtime.application.LuegoAppInfo;
import luego.runtime.application.LuegoRunner;
import luego.runtime.results.Known;
import luego.runtime.results.Result;
import luego.runtime.results.ResultUtil;
import luego.runtime.values.PreEvaluationError;
import luego.types.LGType;
import scala.Tuple2;
import scala.util.Either;

class capitalize_Test {
  
    static LuegoRunner appRunner;

    @BeforeAll
    static void setup() {
        System.out.println("@BeforeAll - executes once before all test methods in this class");
        LuegoAppInfo appInfo = LuegoAppInfo.read(".");
        appRunner = LuegoRunner.apply("./target/luego/app/" + appInfo.appId() + "/" + appInfo.appVersion());
    }  


    @Test
    void capitalize() {
        String parametersString = 
        """
            { "s": "Hello" }     
        """;

      Either<PreEvaluationError, Tuple2<Result<?>, LGType>> evalRes = appRunner.evaluate("newco.crm.capitalize", 
                                                                                         parametersString, "en");

      System.out.println("evalRes = " + evalRes);        
      assertEquals("Right((Known(value = HELLO, type = class java.lang.String),Text))", evalRes.toString());
      assertTrue(evalRes.toOption().nonEmpty());
      assertEquals(ResultUtil.hasKnownValue(evalRes.toOption().get(), 
        """
        "HELLO"
        """,
        appRunner.dataModel()), scala.None$.MODULE$);

    }
}
