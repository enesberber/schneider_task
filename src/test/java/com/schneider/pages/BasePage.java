package com.schneider.pages;

import com.schneider.utilities.BrowserUtils;
import com.schneider.utilities.Driver;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BasePage {

    double initialLossTestValue;
    double epochValue;
    double finalLossTestValue;

    public BasePage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//div[@id='loss-test']")
    public WebElement lossTestValueElement;

    @FindBy(xpath = "//div[@title='Exclusive or']")
    public WebElement datasetExclusive;

    @FindBy(xpath = "//div[@class='ui-noise']/p/div/div/div[2]")
    public WebElement noiseSliderBar;

    @FindBy(xpath = "//div[@id='canvas-xSquared']")
    public WebElement featureXSquaredButton;

    @FindBy(xpath = "//div[@id='canvas-ySquared']")
    public WebElement featureYSquaredButton;

    @FindBy(xpath = "//div[@class='ui-numNodes1']/button[2]")
    public WebElement removeNeuronButtonLeft;

    @FindBy(xpath = "//div[@class='ui-numNodes2']/button[2]")
    public WebElement removeNeuronButtonRight;

    @FindBy(xpath = "//select[@id='learningRate']")
    public WebElement learningRateDropdownMenu;

    @FindBy(xpath = "//button[@id='play-pause-button']")
    public WebElement runSimulationButton;

    @FindBy(xpath = "//span[@id='iter-number']")
    public WebElement epochValueElement;

    /**
     * Reading total loss value
     */
    public double getTotalLossValue() {
        return initialLossTestValue = Double.parseDouble(lossTestValueElement.getText());
    }

    /**
     * Setting noise value according to the requirement
     * @param value is between 0-50
     */
    public void setNoiseValue(int value) {

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
        return finalLossTestValue = getTotalLossValue();
    }


}
