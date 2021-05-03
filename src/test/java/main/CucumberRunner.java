package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.google.common.io.Files;


import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.junit.runner.RunWith;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import helpers.ReportHelper;


@CucumberOptions(strict = true,
		monochrome = true,
		features = "src/test/resources/features",
		glue = "stepdefinition",
		plugin = {"pretty","json:target/cucumber.json"}
		//tags = { "@Mobile,@Regression,@JunitScenario,@TestngScenario" }
		)


public class CucumberRunner extends AbstractTestNGCucumberTests {

	public static Properties config = null;
	public static AndroidDriver<MobileElement> driver;
	public static WebDriverWait wait;


	public void LoadConfigProperty() throws IOException {
		config = new Properties();
		FileInputStream ip = new FileInputStream(
				System.getProperty("user.dir") + "//src//test//resources//config//config.properties");
		config.load(ip);
	}

	public void configureDriverPath() throws IOException {
		LoadConfigProperty();
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("deviceName", config.getProperty("deviceName"));
		caps.setCapability("udid", config.getProperty("udid"));
		caps.setCapability("platformName", config.getProperty("platformName"));
		caps.setCapability("platformVersion", config.getProperty("platformVersion"));
		caps.setCapability("skipUnlock",config.getProperty("skipUnlock"));
		caps.setCapability("app",System.getProperty("user.dir") + config.getProperty("app"));
		caps.setCapability("automationName",config.getProperty("UiAutomator2"));


		driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
		wait = new WebDriverWait(driver, 20);

	}

	public void explicitWait(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 3000);
		wait.until(ExpectedConditions.visibilityOf(element));
	}


	public static String currentDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String cal1 = dateFormat.format(cal.getTime());
		return cal1;
	}

	//////////////////////////////////////////////////////////////
	@BeforeSuite(alwaysRun = true)
	public void setUp() throws Exception {
		configureDriverPath();
	}
// AFTER TEST RUNNER COMPLETED
	@AfterClass(alwaysRun = true)
	public void takeScreenshot() throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File trgtFile = new File(System.getProperty("user.dir") + "//screenshots/screenshot.png");
		trgtFile.getParentFile().mkdir();
		trgtFile.createNewFile();
		Files.copy(scrFile, trgtFile);

	}
// --  AFTER EACH SCENARIO
	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult result) throws IOException {
		if (!result.isSuccess()) {
			File imageFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String failureImageFileName = result.getMethod().getMethodName()
					+ new SimpleDateFormat("MM-dd-yyyy_HH-ss").format(new GregorianCalendar().getTime()) + ".png";
			File failureImageFile = new File(System.getProperty("user.dir") + "//screenshots//" + failureImageFileName);
			failureImageFile.getParentFile().mkdir();
			failureImageFile.createNewFile();
			Files.copy(imageFile, failureImageFile);
		}
		driver.resetApp();

	}
	@AfterSuite(alwaysRun=true)
	public void generateHTMLReports() {
		ReportHelper.generateCucumberReport();
	}

	@AfterSuite(alwaysRun = true)
	public void quit() throws IOException, InterruptedException {
		driver.closeApp();
	}
}
