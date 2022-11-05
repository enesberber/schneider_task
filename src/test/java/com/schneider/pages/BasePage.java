package com.schneider.pages;

import com.schneider.utilities.BrowserUtils;
import com.schneider.utilities.Driver;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

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
     * I created a dynamic structure. The user enters the value between 0-50
     * If value is (valid = implement), (else = assign noise value to default = 0 and continue test)
     * @param value = from user
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
     * I created a dynamic structure; this method gets learning rate value from the user
     * and checks if it is a valid value (that are listed under the dropdown menu)
     * (if valid = implement), (else = assign default rate value and continue to the test)
     * @param value=from user
     */
    public void selectLearningRate(String value){
        Select select = new Select(learningRateDropdownMenu);
        List<WebElement> learningRateValues = select.getOptions();
        List<String> validRateValues = new ArrayList<>();

        for(int i = 0; i < learningRateValues.size(); i++){     //Iterate through dropdown webElements and getText
            validRateValues.add(learningRateValues.get(i).getText());
        }

        if(validRateValues.contains(value)){                    //Check whether the user input value is valid or not
            select.selectByValue(String.valueOf(value));
        }else{
            select.selectByValue(select.getFirstSelectedOption().getText());
            System.err.println("Invalid learning rate value: Default value of 0.03 is selected");
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
