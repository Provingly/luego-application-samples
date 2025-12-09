package runners;

import luego.etest.ExploratoryTestManager;
import luego.runtime.application.*;

public class ExploratoryTestRunner {

  static void main(String[] args) {

    LuegoAppInfo appInfo = LuegoAppInfo.read(".");
    LuegoRunner appRunner = LuegoRunner.apply("./target/luego/app/" + appInfo.appId() + "/" + appInfo.appVersion());
    String language = "en";

    System.out.println("=====================================");
    System.out.println("Using the exploratory test tool");
    String model = "newco.crm.FDiscountAndPoints";
    //String model = "newco.crm.DM_DiscountAndPoints";
    ExploratoryTestManager exploratoryTest = new ExploratoryTestManager(appRunner, model , language);
    exploratoryTest.play(
        """
        {
          "the request": {
            "LGType_": "newco.crm.Request"
          }
        }
        """
    );

  }
}
