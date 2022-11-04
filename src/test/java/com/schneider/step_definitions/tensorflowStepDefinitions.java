package com.schneider.step_definitions;

import com.schneider.pages.BasePage;
import com.schneider.utilities.ConfigurationReader;
import com.schneider.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.support.ui.*;


public class tensorflowStepDefinitions {

    /** I implemented Page Object Model and saved web elements under the BasePage
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

//        JavascriptExecutor executor = (JavascriptExecutor) Driver.getDriver();
//        executor.executeScript("arguments[0].click();", basePage.noiseSliderBar);
//        executor.executeScript("arguments[0].setAttribute('style', ' flex: 0.1 1 0%; ')", basePage.noiseSliderBar);
//        executor.executeScript("arguments[0].setAttribute('style', ' flex: 0.9 1 0%; ')", basePage.noiseSliderBar);

//        Point point = basePage.noiseSliderBar.getLocation();
//        int xAxis = point.getX();
//        int yAxis = point.getY();
//
//        System.out.println("xAxis = " + xAxis);
//        System.out.println("yAxis = " + yAxis);
//
//        Actions actions = new Actions(Driver.getDriver());
//        actions.dragAndDropBy(basePage.noiseSliderBar, xAxis, yAxis).perform();

//        Actions(driver).moveToElement(element).moveByOffset(dx, dy).click().perform();
//        Action action = actions.dragAndDropBy(basePage.noiseSliderBar, xAxis, yAxis).build();




    }

    @Then("Selects two more features")
    public void selects_two_more_features() {
        //Select XSquared feature
        basePage.featureXSquaredButton.click();

        //Select YSquared feature
        basePage.featureYSquaredButton.click();
    }

    @Then("Removes one neuron from the left and one neuron from the right row")
    public void removesOneNeuronFromTheLeftAndOneNeuronFromTheRightRow() {
        //Remove neuron from the left row
        basePage.removeNeuronButtonLeft.click();

        //Remove neuron from the right row
        basePage.removeNeuronButtonRight.click();
    }

    @Then("Changes learning rate to {double}")
    public void changes_learning_rate_to(Double value) {
        Select select = new Select(basePage.learningRateDropdownMenu);
        select.selectByValue(String.valueOf(value));
    }

    @And("Clicks to run the simulation button")
    public void clicks_to_run_the_simulation_button() {
        basePage.runSimulationButton.click();
    }



    @And("Waits until epoch value is more than {double}")
    public void waitsUntilEpochValueIsMoreThan(Double value) throws InterruptedException {

        basePage.waitEpochValue();

    }

    @Then("Reports new test loss value in the console")
    public void reportsNewTestLossValueInTheConsole() {
        System.out.println("Final loss test value = " + basePage.getTotalLossValue());

    }
}
