package TestRunners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/spot/03_Spot_Limit_Cancelled.feature", // Path to feature files
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
