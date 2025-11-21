package runners;

import luego.runners.Scenario;
import luego.runners.ScenarioRunner;

public class MyScenarioRunner extends ScenarioRunner {

  public Scenario[] getScenarios() {
    Scenario[] scenarios = {

      new Scenario("Call generateResponse", 
                    "sample.greetings.generateResponse", 
                        """
                        {
                          "the request": {
                            "LGType_": "sample.greetings.Request",
                            "message": "Hello"
                          }
                        }"""
                  ),
      new Scenario("Call generartResponse with incomplete data", 
                    "sample.greetings.generateResponse", 
                        """
                        {
                          "the request": {
                            "LGType_": "sample.greetings.Request"
                          }
                        }"""
                  ),
      new Scenario("Call GenerateResponse2", 
                    "sample.greetings.GenerateResponse2", 
                        """
                        {
                          "request": {
                            "LGType_": "sample.greetings.Request",
                            "message": "Hello"
                          }
                        }"""
                  ),
      new Scenario("Call GenerateResponse2 with missing data", 
                    "sample.greetings.GenerateResponse2", 
                        """
                        {
                          "request": {
                            "LGType_": "sample.greetings.Request"
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
