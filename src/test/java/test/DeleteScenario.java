package test;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.fail;

/**
 * Created by oscar on 3/03/15.
 */
public class DeleteScenario {
    WebDriver webDriver = null;

    @Given("^The person is displayed in the browser$")
    public void the_person_is_displayed_in_the_browser() throws Throwable {
        webDriver = new FirefoxDriver();
        webDriver.navigate().to("http://localhost:8080/Agenda/html/index.html");
    }

    @When("^I click the delete button$")
    public void i_click_the_delete_button() throws Throwable {
        webDriver.findElement(By.id("delete:123X")).click();
    }

    @Then("^The person is deleted from the database$")
    public void the_person_is_deleted_from_the_database() throws Throwable {
        try {
            webDriver.findElement(By.id("delete:123X"));
            fail("The person exists");
        } catch (NoSuchElementException e) {
            // OK, everything is correct.
            webDriver.close();
        }
    }
}
