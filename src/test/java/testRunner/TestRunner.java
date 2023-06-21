package testRunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "D:\\IntelliJ\\CucumberAPI\\Features\\Book_API.feature",
        glue = {"StepDefinition"},
        tags = "@LibraryWork01",
        monochrome = true,
        plugin = {"pretty","html:target/HtmlReports"} )
public class TestRunner {
}
