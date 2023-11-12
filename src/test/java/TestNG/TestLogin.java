package TestNG;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class TestLogin {
    WebDriver driver;

    @BeforeTest
    public void init() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://foodstorehq.000webhostapp.com/management_web/account/");
        // driver.quit();
    }
    @Test
    public void testEmptyInput() {
        WebElement loginButton = driver.findElement(By.name("dangnhap"));
        loginButton.click();
        WebElement errorElement = driver.findElement(By.id("error_message"));
        Assert.assertEquals(errorElement.getText(), "Bạn chưa nhập tên đăng nhập và mật khẩu.");
    }

    @Test
    public void testEmptyUser() {

        WebElement InputPassWord = driver.findElement(By.name("password"));
        InputPassWord.sendKeys("123");
        WebElement loginButton = driver.findElement(By.name("dangnhap"));
        loginButton.click();
        WebElement errorElement = driver.findElement(By.id("error_message"));
        Assert.assertEquals(errorElement.getText(), "Bạn chưa nhập tên đăng nhập.");

    }

    @Test
    public void testEmptyPassWord() {
        WebElement InputUsername = driver.findElement(By.name("username"));
        InputUsername.sendKeys("admin");
        WebElement loginButton = driver.findElement(By.name("dangnhap"));
        loginButton.click();
        WebElement errorElement = driver.findElement(By.id("error_message"));
        Assert.assertEquals(errorElement.getText(), "Bạn chưa nhập mật khẩu.");
    }

    @Test
    public void testInvalidUser() {
        WebElement InputUsername = driver.findElement(By.name("username"));
        InputUsername.sendKeys("admi");
        WebElement InputPassWord = driver.findElement(By.name("password"));
        InputPassWord.sendKeys("123");
        WebElement loginButton = driver.findElement(By.name("dangnhap"));
        loginButton.click();
        WebElement errorElement = driver.findElement(By.id("error_message"));
        Assert.assertEquals(errorElement.getText(), "Tài khoản hoặc mật khẩu không hợp lệ!");
    }

    @Test
    public void testInvalidPassWord() {
        WebElement InputUsername = driver.findElement(By.name("username"));
        InputUsername.sendKeys("admin");
        WebElement InputPassWord = driver.findElement(By.name("password"));
        InputPassWord.sendKeys("a123");
        WebElement loginButton = driver.findElement(By.name("dangnhap"));
        loginButton.click();
        WebElement errorElement = driver.findElement(By.id("error_message"));
        Assert.assertEquals(errorElement.getText(), "Tài khoản hoặc mật khẩu không hợp lệ!");
    }

    @Test
    public void testInvalidUserAndPassWord() {
        WebElement InputUsername = driver.findElement(By.name("username"));
        InputUsername.sendKeys("admi");
        WebElement InputPassWord = driver.findElement(By.name("password"));
        InputPassWord.sendKeys("a123");
        WebElement loginButton = driver.findElement(By.name("dangnhap"));
        loginButton.click();
        WebElement errorElement = driver.findElement(By.id("error_message"));
        Assert.assertEquals(errorElement.getText(), "Tài khoản hoặc mật khẩu không hợp lệ!");
    }

    @Test
    public void testValidUserAndPassWord() {
        WebElement InputUsername = driver.findElement(By.name("username"));
        InputUsername.sendKeys("admin");
        WebElement InputPassWord = driver.findElement(By.name("password"));
        InputPassWord.sendKeys("123");
        WebElement loginButton = driver.findElement(By.name("dangnhap"));
        loginButton.click();
    }

    @AfterTest
    public void quit() {

        driver.quit();
    }
}
