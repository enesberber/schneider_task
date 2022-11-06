package com.schneider.pages;

import com.schneider.utilities.BrowserUtils;
import com.schneider.utilities.Driver;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import java.util.ArrayList;
import java.util.List;

public class BasePage {

    private double lossTestValue;
    private double epochValue;

    public BasePage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//div[@id='loss-test']")
    public WebElement lossTestValueElement;

    @FindBy(xpath = "//div[@title='Exclusive or']/canvas")
    public WebElement datasetExclusive;

    @FindBy(xpath = "//div[@class='ui-noise']/p/div/div/div[2]")
    public WebElement noiseSliderBar;

    @FindBy(xpath = "//div[@id='canvas-xSquared']")
    public WebElement featureXSquaredButton;

    @FindBy(xpath = "//div[@id='canvas-ySquared']")
    public WebElement featureYSquaredButton;

    @FindBy(xpath = "//div[@class='ui-numNodes1']/button[2]")
    public WebElement removeNeuronButtonLeft;

    @FindBy(xpath = "//div[@class='ui-numNodes1']/following-sibling::div")
    public WebElement numberOfNeuronsLeft;

    @FindBy(xpath = "//div[@class='ui-numNodes2']/button[2]")
    public WebElement removeNeuronButtonRight;

    @FindBy(xpath = "//div[@class='ui-numNodes2']/following-sibling::div")
    public WebElement numberOfNeuronsRight;

    @FindBy(xpath = "//select[@id='learningRate']")
    public WebElement learningRateDropdownMenu;

    @FindBy(xpath = "//button[@id='play-pause-button']")
    public WebElement runSimulationButton;

    @FindBy(xpath = "//span[@id='iter-number']")
    public WebElement epochValueElement;


    /**
     * This method is for reading total loss value
     */
    public double getTotalLossValue() {
        return lossTestValue = Double.parseDouble(lossTestValueElement.getText());
    }


    /**
     * This method is for changing dataset to Exclusive
     * And making Assertion whether the dataset has changed or not
     */
    public void changeDatasetToExclusive() {
        datasetExclusive.click();
        boolean isExclusiveSelected = datasetExclusive.getAttribute("class").contains("selected");
        Assert.assertTrue("Dataset is not selected to \"Exclueive\"", isExclusiveSelected);
    }


    /**
     * This method is for adding two additional features
     * And making Assertion whether the features are added or not
     */
    public void selectFeatures() {
        featureXSquaredButton.click();     //Select XSquared feature
        boolean isXSqSelected = featureXSquaredButton.getAttribute("class").contains("inactive");
        Assert.assertFalse("X-Squared feature is not selected", isXSqSelected);

        featureYSquaredButton.click();     //Select YSquared feature
        boolean isYSqSelected = featureYSquaredButton.getAttribute("class").contains("inactive");
        Assert.assertFalse("Y-Squared feature is not selected", isYSqSelected);
    }


    /**
     * This method is for removing neurons
     * And making Assertion whether the neurons are removed or not
     */
    public void removeNeurons(){
        removeNeuronButtonLeft.click();    //Remove one neuron from the left row
        String expectedLeftNeuronNumber = "3 neurons";
        boolean isLeftNeuronNumberCorrect = numberOfNeuronsLeft.getText().equals(expectedLeftNeuronNumber);
        Assert.assertTrue("Neuron is not removed from the left", isLeftNeuronNumberCorrect);

        removeNeuronButtonRight.click();   //Remove one neuron from the right row
        String expectedRightNeuronNumber = "1 neuron";
        boolean isRightNeuronNumberCorrect = numberOfNeuronsRight.getText().equals(expectedRightNeuronNumber);
        Assert.assertTrue("Neuron is not removed from the right", isRightNeuronNumberCorrect);
    }


    /**
     * I created a dynamic structure. The user enters the input value
     * Method rounds the input value to the nearest 5.
     * If value is (valid = implement), (else = assign noise value to default = 0 and continue test)
     *
     * @param value = from user (Valid values are between 0-50)
     */
    public void setNoiseValue(int value) {
        value = Math.round(value / 5) * 5;  //Rounded input value to nearest x5

        if (value < 0 || value > 50) {
            System.err.println("Invalid noise value: Default value of 0 is selected");
            System.exit(-1);
        }

        // Assigned factor value corresponding to the user input value ==> for validation and actions.sendKeys purposes
        int factor = (value == 0) ? 5 : (value == 5) ? 4 : (value == 10) ? 3 : (value == 15) ? 2 : (value == 20) ? 1 : (value == 25) ? 0 :
                (value == 30) ? -1 : (value == 35) ? -2 : (value == 40) ? -3 : (value == 45) ? -4 : (value == 50) ? -5 : 5;

        Actions actions = new Actions(Driver.getDriver());
        actions.clickAndHold(noiseSliderBar);
        if (factor > 0) {
            for (int i = 1; i <= factor; i++) {
                actions.clickAndHold().sendKeys(Keys.ARROW_LEFT).perform();
            }
        } else if (factor < 0) {
            for (int i = -1; i >= factor; i--) {
                actions.clickAndHold().sendKeys(Keys.ARROW_RIGHT).perform();
            }
        } else {
            actions.release().perform();
        }
    }


    /**
     * I created a dynamic structure; this method gets learning rate value from the user
     * and checks if it is a valid value (that are listed under the dropdown menu)
     * (if valid = implement), (else = assign default rate value and continue to the test)
     *
     * @param value=from user
     */
    public void selectLearningRate(String value) {
        Select select = new Select(learningRateDropdownMenu);
        List<WebElement> learningRateValues = select.getOptions();
        List<String> validRateValues = new ArrayList<>();

        for (int i = 0; i < learningRateValues.size(); i++) {     //Iterate through dropdown webElements and getText
            validRateValues.add(learningRateValues.get(i).getText());
        }

        if (validRateValues.contains(value)) {                    //Check whether the user input value is valid or not
            select.selectByValue(String.valueOf(value));
        }
        boolean isLearningRateTrue = validRateValues.contains(value);
        Assert.assertTrue("Invalid learning rate value is selected", isLearningRateTrue);
    }


    /**
     * Epoch value is a String value and seperated with comma
     * This method is parsing epochValue into double for manipulating the data
     */
    public double checkEpochValue() {
        StringBuilder epochStringValue = new StringBuilder(epochValueElement.getText());
        epochStringValue.setCharAt(3, '.');
        return epochValue = Double.parseDouble(String.valueOf(epochStringValue));
    }


    /**
     * Epoch value is iterating and upon requirement we need to wait until epochValue>0.3
     * Method is for waiting the epochValue reach the expected value
     */
    public double waitEpochValue(double value) {
        for (int i = 0; i <= 1000; i++) {
            epochValue = checkEpochValue(); //Calling above method to read the current epochValue
            BrowserUtils.waitFor();
            if (epochValue > value) {
                i = 1000; //Will break the loop when expected condition is met
            }
        }
        Assert.assertTrue("Epoch value is not greater than 3.0", checkEpochValue()>value);
        return lossTestValue = getTotalLossValue();
    }


}
