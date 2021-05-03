package stepdefinition;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import main.CucumberRunner;
import org.testng.Assert;
import pages.MobilePage;

public class MyAppFlow extends CucumberRunner {


    MobilePage pageMobile = new MobilePage();

    @Given("^Open camera to get an image$")
    public void openCameraToGetAnImage() {
        pageMobile.openCamera();
    }

    @When("^Allow camera permision$")
    public void allowCameraPermision() {
        pageMobile.allowCamera();
    }

    @Then("^Click on button OK$")
    public void clickOnButtinOK() throws InterruptedException {
        pageMobile.clickElement();
    }

    @And("^Validate pairs$")
    public void validatePairs() {
        Assert.assertEquals("1", "2");
    }

}
