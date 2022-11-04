package com.schneider.utilities;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class BrowserUtils {

/*    *//**
     * Sets the value on a horizontal angular.js slider
     *
     * @param value
     *            the desired value
     *//*
    public static void setHValue(WebElement slider, double value)
    {
        double minValue = Double.parseDouble(slider.getAttribute("min"));
        double maxValue = Double.parseDouble(slider.getAttribute("max"));
        int sliderH = slider.getSize().height;
        int sliderW = slider.getSize().width;
        System.out.println(sliderH);
        System.out.println(sliderW);
        Actions action = new Actions(Driver.getDriver());
        action.moveToElement(slider, (int) (value * sliderW / (maxValue - minValue)), sliderH / 2).click().build().perform();
    }*/

}
