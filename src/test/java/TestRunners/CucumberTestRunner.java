package TestRunners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = {
                "src/test/resources/spot/01_Spot_Limit_Filled.feature",
                "src/test/resources/spot/02_Spot_Limit_Cancelled.feature",
                "src/test/resources/spot/03_Spot_Limit_Partially_Filled_and_Cancelled.feature",
                "src/test/resources/spot/04_Spot_Limit_Terminated.feature",
                "src/test/resources/spot/05_Spot_Limit_Partially_Terminated.feature",

        },// Path to feature files

        glue = "StepDefinitions", // Path to step definition classes
        plugin = {
                "pretty", // Output format for console
                "html:target/htmlreport.html" // HTML report path
        },
        tags = "" // Tags for filtering scenarios
)
public class CucumberTestRunner extends AbstractTestNGCucumberTests {
    // You can add additional configurations here if needed
}
