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
public class PostScenario {
    private WebDriver webDriver = null;
    private final By idNombre = By.id("nombre");
    private final By idApellidos = By.id("apellidos");
    private final By idNif = By.id("nif");
    private final By idBotonNuevo = By.id("nuevo");

    @Given("^I'm in the index\\.html page$")
    public void i_m_in_the_index_html_page() throws Throwable {
        webDriver = new FirefoxDriver();
        webDriver.navigate().to("http://localhost:8080/Agenda/html/index.html");
    }

    @When("^I write the name of the person$")
    public void i_write_the_name_of_the_person() throws Throwable {
        webDriver.findElement(idNombre).sendKeys("Pedro");
    }

    @When("^I write the surname of the person$")
    public void i_write_the_surname_of_the_person() throws Throwable {
        webDriver.findElement(idApellidos).sendKeys("Paramo");
    }

    @When("^I write the nif of the person$")
    public void i_write_the_nif_of_the_person() throws Throwable {
        webDriver.findElement(idNif).sendKeys("666X");
    }

    @When("^I click the Nuevo button$")
    public void i_click_the_Nuevo_button() throws Throwable {
        webDriver.findElement(idBotonNuevo).click();
    }

    @Then("^The person is added to the table$")
    public void the_person_is_added_to_the_table() throws Throwable {
        try {
            webDriver.navigate().to("http://localhost:8080/Agenda/html/index.html");
            webDriver.findElement(By.id("delete:666X"));
        } catch (NoSuchElementException e) {
            fail("El nuevo contacto no se ha añadido");
        }
    }
}
