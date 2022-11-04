package com.schneider.pages;

import com.schneider.utilities.BrowserUtils;
import com.schneider.utilities.Driver;
import org.openqa.selenium.WebElement;
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

    public double getTotalLossValue() {
        return initialLossTestValue = Double.parseDouble(lossTestValueElement.getText());
    }

    public double checkEpochValue() {
        StringBuilder epochStringValue = new StringBuilder(epochValueElement.getText());
        epochStringValue.setCharAt(3, '.');
        return epochValue = Double.parseDouble(String.valueOf(epochStringValue));
    }

    public double waitEpochValue() {
        for (int i = 0; i <= 1000; i++) {
            epochValue = checkEpochValue();
            BrowserUtils.waitFor();
            if (epochValue > 0.3) {
                i = 1000;
                runSimulationButton.click();
                System.out.println("currentEpochValue = " + epochValue);
            }
        }
        return finalLossTestValue = getTotalLossValue();
    }


}
