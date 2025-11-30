package runners;

import luego.runners.Scenario;
import luego.runners.ScenarioRunner;

public class MyScenarioRunner extends ScenarioRunner {

  public Scenario[] getScenarios() {
    Scenario[] scenarios = {

      new Scenario("Call ProcessRequest", 
                    "sample.banking.ProcessRequest", 
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
                  )
    };
    return scenarios;
  }

  static void main(String[] args) {
    new MyScenarioRunner().run(args);
  }
}
