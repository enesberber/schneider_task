Feature: Automated test case for Schneider task on playgroung.tensorflow.org page

  @wip
  Scenario: Write an automated test case that ends up scenario in the required state
    Given User navigates to the "url"
    And Reports the test loss value in the console
    Then Changes dataset to exclusive
    And Changes noise to 5 percent
    And Selects two more features
    Then Removes one neuron from the left and one neuron from the right row
    Then Changes learning rate to 0.1
    And Clicks to run the simulation button
    And Waits until epoch value is more than 0.3
    And Reports new test loss value in the console


