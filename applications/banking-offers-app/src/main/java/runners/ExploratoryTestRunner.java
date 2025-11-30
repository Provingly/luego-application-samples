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
    ExploratoryTestManager exploratoryTest = new ExploratoryTestManager(appRunner, "sample.banking.ProcessRequest", language);
    exploratoryTest.setLogLevel("Trace");
    exploratoryTest.play(
        """
        {
          "the request": {
            "LGType_": "sample.banking.Request"
          },
          "the customer": {
            "LGType_": "sample.banking.Person"
          }
        }
        """
    );

  }
}
