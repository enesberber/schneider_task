package com.schneider.utilities;

public class BrowserUtils {

    /**
     * Performs a pause for 10 milliseconds
     */
    public static void waitFor() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
