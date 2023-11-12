package Method;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class Method {
    public WebDriver driver;
    public void setDriver(WebDriver _driver) {
        driver = _driver;
    }


    // Hàm chờ
    public void waitTime(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    // Kiểm tra trường nhập form Required
    public void assertMessage(WebElement element, Boolean set) {
        String validationMessage = element.getAttribute("validationMessage");
        if (set) {
            Assert.assertTrue(validationMessage.contains("Please fill out this field"));
        } else {
            Assert.assertFalse(validationMessage.contains("Please fill out this field"));
        }
    }
}
