package runners;

import luego.runners.Scenario;
import luego.runners.ScenarioRunner;

public class MyScenarioRunner extends ScenarioRunner {

  public Scenario[] getScenarios() {
    Scenario[] scenarios = {
      new Scenario("capitalize 'Hello'", 
                    "newco.crm.capitalize", 
                        """
                        { "s": "Hello" }
                        """
                  ),
      new Scenario("Call a decision model with complete info", 
                    "newco.crm.DiscountEligibility", 
                        """
                        {
                          "request": {
                            "LGType_": "newco.crm.Request",
                            "customerName": "Jane",
                            "value": 120,
                            "products": []
                          }
                        }"""
                  ),
      new Scenario("Call a decision model with missing info", 
                    "newco.crm.DiscountEligibility", 
                        """
                        {
                          "request": {
                            "LGType_": "newco.crm.Request",
                            "products": []
                          }
                        }"""
                  ),
      new Scenario("Call a decision model with erroneous input", 
                    "newco.crm.DiscountEligibility", 
                        """
                        {
                          "request": {
                            "LGType_": "newco.crm.Request",
                            "customerName": 120,
                            "products": []
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
