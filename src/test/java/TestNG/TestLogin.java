package TestNG;

import Method.LogInMethod;
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
    private WebElement loginButton;
    private WebElement InputUsername;
    private WebElement InputPassWord;
    private WebElement errorElement;
    
    LogInMethod logMethod = new LogInMethod();
    
    private void setElement() {
        loginButton = driver.findElement(By.name("dangnhap"));
        InputUsername = driver.findElement(By.name("username"));
        InputPassWord = driver.findElement(By.name("password"));
        logMethod.setForm(InputUsername, InputPassWord, loginButton);
    }

    private void setError() {
        errorElement = driver.findElement(By.id("error_message"));
    }

    @BeforeTest
    public void init() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://foodstorehq.000webhostapp.com/management_web/account/");
        driver.manage().window().maximize();
    }
    @Test
    public void testEmptyInput() {
        setElement();

        loginButton.click();
        logMethod.waitTime(2000);

        setError();
        Assert.assertEquals(errorElement.getText(), "Bạn chưa nhập tên đăng nhập và mật khẩu.");
    }

    @Test
    public void testEmptyUser() {
        setElement();

        logMethod.sendForm("", "123");

        setError();
        Assert.assertEquals(errorElement.getText(), "Bạn chưa nhập tên đăng nhập.");
    }

    @Test
    public void testEmptyPassWord() {
        setElement();

        logMethod.sendForm("admin", "");

        setError();
        Assert.assertEquals(errorElement.getText(), "Bạn chưa nhập mật khẩu.");
    }

    @Test
    public void testInvalidUser() {
        setElement();

        logMethod.sendForm("ad", "123");

        setError();
        Assert.assertEquals(errorElement.getText(), "Tài khoản hoặc mật khẩu không hợp lệ!");
    }

    @Test
    public void testInvalidPassWord() {
        setElement();

        logMethod.sendForm("admin", "a123");

        setError();
        Assert.assertEquals(errorElement.getText(), "Tài khoản hoặc mật khẩu không hợp lệ!");
    }

    @Test
    public void testInvalidUserAndPassWord() {
        setElement();

        logMethod.sendForm("admin12", "a123");

        setError();
        Assert.assertEquals(errorElement.getText(), "Tài khoản hoặc mật khẩu không hợp lệ!");
    }

    @Test
    public void testValidUserAndPassWord() {
        setElement();

        logMethod.sendForm("admin", "1234");

        WebElement title = driver.findElement(By.xpath("//h1[contains(text(), 'BẢNG THỐNG KÊ')]"));
        Assert.assertTrue(title.isDisplayed(), "Đăng nhập thành công");
    }

    @AfterTest
    public void quit() {

        driver.quit();
    }
}
