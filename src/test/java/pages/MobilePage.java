package pages;

import io.appium.java_client.MobileElement;
import main.CucumberRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class MobilePage extends CucumberRunner {

    String btn1 = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.widget.Button[2]";
    String allowButton = "com.android.permissioncontroller:id/permission_allow_button";

        public void openCamera ()  {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(btn1))).click();
        }

        public void allowCamera ()  {
            wait.until(ExpectedConditions.elementToBeClickable(By.id(allowButton))).click();
        }


        public void clickElement () throws InterruptedException {
            Thread.sleep(5000);
            List<MobileElement> elementName = driver.findElements(By.id("com.android.documentsui:id/app_icon"));
            System.out.println("Element Amount "+elementName.size());
            wait.until(ExpectedConditions.elementToBeClickable(elementName.get(2))).click();
        }


}
