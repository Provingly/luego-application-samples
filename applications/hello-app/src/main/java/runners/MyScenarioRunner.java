package runners;

import luego.runners.Scenario;
import luego.runners.ScenarioRunner;

public class MyScenarioRunner extends ScenarioRunner {

  public Scenario[] getScenarios() {
    Scenario[] scenarios = {
      new Scenario("Say hello", 
                    "sample.hello.hello", 
                        """
                        { "s": "Joe" }
                        """
                  ),
      new Scenario("Say hello 2", 
                    "sample.hello.hello2", 
                        """
                        { "s": "Joe" }
                        """
                  ),
      new Scenario("Call greetings", 
                    "sample.greetings.greetings", 
                        """
                        {
                          "the request": {
                            "LGType_": "sample.greetings.Request",
                            "dateTime": "2023-11-06T11:00:00",
                            "person": {
                              "LGType_": "sample.greetings.Person",
                              "name": "Smith",
                              "honorific": []                            
                            }  
                          }
                        }"""
                  ),
      new Scenario("Call greetings with incomplete data", 
                    "sample.greetings.greetings", 
                        """
                        {
                          "the request": {
                            "LGType_": "sample.greetings.Request"
                          }
                        }"""
                  ),
      new Scenario("Call Greetings2", 
                    "sample.greetings.Greetings2", 
                        """
                        {
                          "request": {
                            "LGType_": "sample.greetings.Request",
                            "dateTime": "2023-11-06T13:00:00",
                            "person": {
                              "LGType_": "sample.greetings.Person",
                              "name": "Joe",
                              "honorific": ["Mr"]                            
                            }  
                          }
                        }"""
                  ),
      new Scenario("Call Greetings2 with missing data", 
                    "sample.greetings.Greetings2", 
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
