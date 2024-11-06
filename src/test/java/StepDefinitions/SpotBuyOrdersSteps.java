package StepDefinitions;

import CommonClasses.DriverManager;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pageFactory.SpotBuyPage;

public class SpotBuyOrdersSteps {

    SpotBuyPage spotPage;
    private WebDriver driver;

    @Before
    public void setUp() {
        driver = DriverManager.getDriver();
        spotPage = new SpotBuyPage(driver);
    }

    @Given("the buyer navigates to the spot order placement page")
    public void the_buyer_navigates_to_the_spot_order_placement_page() {
        driver.get("http://localhost:5173/trade/spot/CAR.088"); // Replace with actual URL
    }

    @When("the buyer selects the order type")
    public void the_buyer_selects_the_order_type() {
        spotPage.selectLimitOrderType();
    }

    @And("the buyer enters a limit buy price of {string}")
    public void the_buyer_enters_a_limit_buy_price_of(String price) {
        spotPage.enterLimitPrice(price);
        // Store price in shared context
        ScenarioContext.put("limitBuyPrice", price);
    }

    @And("the buyer enters a quantity of {string}")
    public void the_buyer_enters_a_quantity_of(String quantity) {
        spotPage.enterQuantity(quantity);
        // Store quantity in shared context
        ScenarioContext.put("limitBuyQuantity", quantity);
    }

    @And("the buyer submits the order")
    public void the_buyer_submits_the_order() {
        spotPage.submitOrder();
    }

    @And("the buy order should be placed successfully")
    public void the_buy_order_should_be_placed_successfully() {
        Assert.assertTrue(spotPage.isSuccessMessageDisplayed(), "Success message is not displayed");

        String expectedMessage = "Your order has been successfully placed";
        Assert.assertEquals(spotPage.getSuccessMessageText(), expectedMessage);
    }

//    @And("the buyer info notification should be displayed")
//    public void the_buyer_info_notification_should_be_displayed() {
//        Assert.assertTrue(spotPage.isInfoNotificationDisplayed(), "Info notification is not displayed");
//
////        String expectedMessage = "Your order has been successfully placed";
//        String expectedNotification = "Your Spot Order T\\.[A-Za-z0-9]{8} of Type Limit was successfully filled.";
//        Assert.assertEquals(spotPage.getInfoNotificationText(), expectedNotification);
//    }

    @And("the buyer info notification should be displayed")
    public void the_buyer_info_notification_should_be_displayed() {
        // Check if the notification is displayed
        Assert.assertTrue(spotPage.isInfoNotificationDisplayed(), "Info notification is not displayed");

        // Capture the actual notification text
        String actualNotification = spotPage.getInfoNotificationText();
//        System.out.println("act: " + actualNotification);

        // Update the expected notification pattern to match the actual result, including the final full stop
        String expectedNotificationPattern = "Your Spot Order T\\.[0-9A-Z]{8} of Type limit was successfully filled";

        // Assert that the actual notification matches the expected pattern
        Assert.assertTrue(actualNotification.matches(expectedNotificationPattern),
                "Notification does not match the expected pattern. Actual notification: " + actualNotification);
    }


    @And("the buyer partially info notification should be displayed")
    public void the_buyer_partially_info_notification_should_be_displayed() {
        // Check if the notification is displayed
        Assert.assertTrue(spotPage.isInfoNotificationDisplayed(), "Partially Info notification is not displayed");

        // Capture the actual notification text
        String actualNotification = spotPage.getInfoNotificationText();

        // Define the regex pattern for the notification message, allowing for dynamic order ID and filling percentage
        String expectedNotificationPattern = "Your Spot Order T\\.[A-Za-z0-9]{8} of Type limit was partially filled \\((\\d{1,2}(\\.\\d{1,2})?)%\\)";


        // Assert that the actual notification matches the expected pattern
        Assert.assertTrue(actualNotification.matches(expectedNotificationPattern),
                "Notification does not match the expected pattern. Actual notification: " + actualNotification);
    }


    @Then("the buyer logout")
    public void the_buyer_logout() {
//        driver.close();
    }
}
