package CommonClasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverManager {
    private static WebDriver driver;

    // Private constructor to prevent instantiation
    private DriverManager() {}

    public static WebDriver getDriver() {
        if (driver == null) {
            // Set the path for the ChromeDriver executable if needed
//            System.setProperty("webdriver.chrome.driver", "C:/Program Files/Google/Chrome/Application");
            driver = new ChromeDriver();
//            driver = new ChromeDriver();
        }
        return driver;
    }

//    public static void quitDriver() {
//        if (driver != null) {
//            driver.quit();
//            driver = null;
//        }
//    }
}
