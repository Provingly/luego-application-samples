package newco.crm;

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

class DiscountEligibility_Test {
  
    static LuegoRunner appRunner;

    @BeforeAll
    static void setup() {
        System.out.println("@BeforeAll - executes once before all test methods in this class");

        LuegoAppInfo appInfo = LuegoAppInfo.read(".");
        appRunner = LuegoRunner.apply("./target/luego/app/" + appInfo.appId() + "/" + appInfo.appVersion());
    }  


    @Test
    void discountEligibility() {
        String parametersString = 
        """
        {
        "request": {
            "LGType_": "newco.crm.Request",
            "products": [],
            "loyaltyLevel": [],
            "customerName": "Jane"
        }
        } 
        """;
     
      Either<PreEvaluationError, Tuple2<Result<?>, LGType>> evalRes = appRunner.evaluate("newco.crm.DM_DiscountAndPoints", 
                                                                                         parametersString, "en");

      System.out.println("evalRes = " + evalRes);        
      assertTrue(evalRes.toOption().nonEmpty());
      assertEquals(ResultUtil.hasKnownValue(evalRes.toOption().get(), 
        """
        {
            "LGType_": "newco.crm.Response",
            "discount": 0.0,
            "message": "Hello Jane",
            "loyaltyPointIncrement": 0
        }
        """,
        appRunner.dataModel()), scala.None$.MODULE$);
    }

    @Test
    void discountEligibility_IncompleteData() {
        String parametersString = 
        """
        { 
            "request": {
                "LGType_": "newco.crm.Request"
            }
        }     
        """;
     
      Either<PreEvaluationError, Tuple2<Result<?>, LGType>> evalRes = appRunner.evaluate("newco.crm.DM_DiscountAndPoints", 
                                                                                         parametersString, "en");

      System.out.println("evalRes = " + evalRes);        
      assertTrue(evalRes.toOption().nonEmpty());
      assertEquals(ResultUtil.hasMissingInfo(evalRes.toOption().get(), 
        """
        {
          "type":"MissingData",
          "elements":[
            {"target":"request","targetType":"newco.crm.Request","member":"products","memberType":"List[newco.crm.Product]","kind":"has"},
            {"target":"request","targetType":"newco.crm.Request","member":"loyaltyLevel","memberType":"Option[newco.crm.LoyaltyLevel]","kind":"has"},
            {"target":"request","targetType":"newco.crm.Request","member":"customerName","memberType":"Text","kind":"has"}
          ]
        }
        """,
        appRunner.dataModel()), scala.None$.MODULE$);
    }

}