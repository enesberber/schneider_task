package com.schneider.step_definitions;

import com.schneider.pages.BasePage;
import com.schneider.utilities.ConfigurationReader;
import com.schneider.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.support.ui.*;


public class tensorflowStepDefinitions {

    /**
     * I implemented Page Object Model and saved web elements under the BasePage
     * I also used BrowserUtils page for storing common useful methods
     */
    BasePage basePage = new BasePage();

    @Given("User navigates to the {string}")
    public void user_navigates_to_the(String url) {
        Driver.getDriver().get(ConfigurationReader.getProperty("url"));
    }

    @And("Reports the test loss value in the console")
    public void reports_the_test_loss_value_in_the_console() {
        System.out.println("Initial loss test value = " + basePage.getTotalLossValue());
    }

    @Then("Changes dataset to exclusive")
    public void changes_dataset_to_exclusive() {
        basePage.datasetExclusive.click();
    }

    @Then("Changes noise to {int} percent")
    public void changes_noise_to_percent(Integer value) {
        basePage.setNoiseValue(value);
    }

    @Then("Selects two more features")
    public void selects_two_more_features() {
        basePage.featureXSquaredButton.click();     //Select XSquared feature
        basePage.featureYSquaredButton.click();     //Select YSquared feature
    }

    @Then("Removes one neuron from the left and one neuron from the right row")
    public void removesOneNeuronFromTheLeftAndOneNeuronFromTheRightRow() {
        basePage.removeNeuronButtonLeft.click();    //Remove one neuron from the left row
        basePage.removeNeuronButtonRight.click();   //Remove one neuron from the right row
    }

    @Then("Changes learning rate to {string}")
    public void changes_learning_rate_to(String value) {
        basePage.selectLearningRate(value);
    }

    @And("Clicks to run the simulation button")
    public void clicks_to_run_the_simulation_button() {
        basePage.runSimulationButton.click();
    }

    @And("Waits until epoch value is more than {double}")
    public void waitsUntilEpochValueIsMoreThan(Double value) {
        basePage.waitEpochValue(value);
    }

    @Then("Reports new test loss value in the console")
    public void reportsNewTestLossValueInTheConsole() {
        System.out.println("Final loss test value = " + basePage.getTotalLossValue());
    }
}
