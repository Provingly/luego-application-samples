package runners;

import luego.runners.Scenario;
import luego.runners.ScenarioRunner;

public class MyScenarioRunner extends ScenarioRunner {

  public Scenario[] getScenarios() {
    Scenario[] scenarios = {

      new Scenario("Call GenerateResponse1", 
                    "sample.starter.GenerateResponse1", 
                        """
                        {
                          "the request": {
                            "LGType_": "sample.starter.Request",
                            "message": "Hello"
                          }
                        }"""
                  ),
      new Scenario("Call GenerateResponse1 with incomplete data", 
                    "sample.starter.GenerateResponse1", 
                        """
                        {
                          "the request": {
                            "LGType_": "sample.starter.Request"
                          }
                        }"""
                  ),
      new Scenario("Call GenerateResponse2", 
                    "sample.starter.GenerateResponse2", 
                        """
                        {
                          "request": {
                            "LGType_": "sample.starter.Request",
                            "message": "Hello"
                          }
                        }"""
                  ),
      new Scenario("Call GenerateResponse2 with missing data", 
                    "sample.starter.GenerateResponse2", 
                        """
                        {
                          "request": {
                            "LGType_": "sample.starter.Request"
                          }
                        }"""
                  )

    };
    return scenarios;
  }

  static void main(String[] args) {
    new MyScenarioRunner().run(args);
  }
}
