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
    ExploratoryTestManager exploratoryTest = new ExploratoryTestManager(appRunner, "sample.starter.GenerateResponse1", language);
    exploratoryTest.play(
        """
        {
          "the request": {
            "LGType_": "sample.starter.Request"
          }
        }
        """
    );

  }
}
