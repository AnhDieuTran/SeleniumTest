package TestNG;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;



public class LogInTest {
    private WebDriver driver;

    @BeforeTest
    public void init() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://foodstorehq.000webhostapp.com/food_store_web/build/index.html");
    }

    // Test kiểm tra thông báo xem khi không nhập gì.
    @Test
    public void NoInfo1() {
        String expectedErrorMessage = "Bạn chưa nhập tên đăng nhập và mật khẩu.";

        WebElement image = driver.findElement(By.cssSelector("img[src='../img/icon/user.png']"));

        if (image.isDisplayed()) {
            image.click();
            WebElement loginButton = driver.findElement(By.name("dangnhap"));
            loginButton.click();

            // Kiểm tra xem thông báo lỗi có hiển thị hay không
            WebElement errorMessage = driver.findElement(By.xpath("//div[contains(text(), 'Bạn chưa nhập tên đăng nhập và mật khẩu.')]"));
            String actualErrorMessage = errorMessage.getText();
            assert errorMessage.isDisplayed() : "Thông báo lỗi không hiển thị.";

            // So sánh thực tế với mong đợi
            org.testng.Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "Thông báo lỗi không khớp với yêu cầu.");
        } else {
            System.out.println("Không tìm thấy hình ảnh.");
        }
    }

    @Test
    public void NoInfo2() {

    }

    @AfterTest
    public void quit() {
        driver.quit();
    }
}
