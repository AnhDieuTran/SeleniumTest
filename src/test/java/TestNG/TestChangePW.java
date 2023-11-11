package TestNG;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.Assert;


public class TestChangePW {
    WebDriver driver;
    private String username = "admin";
    private String password = "1234";
    private String newpassword = "1234";
    private String newpassword2 = "abcdef";

    public WebElement changeButton;
    public WebElement matkhaucu;
    public WebElement matkhaumoi_1;
    public WebElement matkhaumoi_2;

    @BeforeTest
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://foodstorehq.000webhostapp.com/management_web/account/");
        driver.manage().window().maximize();

        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.name("dangnhap"));

        // Nhập thông tin đăng nhập
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);

        // Click nút đăng nhập
        loginButton.click();
        driver.get("https://foodstorehq.000webhostapp.com/management_web/manage/password/");
    }

    @Test
    public void EmptyInput() {
        driver.navigate().refresh();
        changeButton = driver.findElement(By.name("btndoimatkhau"));
        matkhaucu = driver.findElement(By.id("matkhaucu"));
        matkhaumoi_1 = driver.findElement(By.id("matkhaumoi_1"));
        matkhaumoi_2 = driver.findElement(By.id("matkhaumoi_2"));

        matkhaucu.sendKeys("");

        changeButton.click();

        String matkhaucuError = matkhaucu.getAttribute("validationMessage");

        // Kiểm tra xem thông báo lỗi có hiển thị đúng không

        Assert.assertTrue(matkhaucuError.contains("Please fill out this field"));
    }

    @Test
    public void EmptyPassword() {
        driver.navigate().refresh();
        changeButton = driver.findElement(By.name("btndoimatkhau"));
        matkhaucu = driver.findElement(By.id("matkhaucu"));
        matkhaumoi_1 = driver.findElement(By.id("matkhaumoi_1"));
        matkhaumoi_2 = driver.findElement(By.id("matkhaumoi_2"));

        matkhaucu.sendKeys(password);
        matkhaumoi_1.sendKeys("");

        changeButton.click();

        String matkhaucuError = matkhaucu.getAttribute("validationMessage");
        String matkhaumoi_1Error = matkhaumoi_1.getAttribute("validationMessage");


        // Kiểm tra xem thông báo lỗi có hiển thị đúng không

        Assert.assertFalse(matkhaucuError.contains("Please fill out this field"));
        Assert.assertTrue(matkhaumoi_1Error.contains("Please fill out this field"));

    }

    @Test
    public void EmptyRePassword() {
        driver.navigate().refresh();
        changeButton = driver.findElement(By.name("btndoimatkhau"));
        matkhaucu = driver.findElement(By.id("matkhaucu"));
        matkhaumoi_1 = driver.findElement(By.id("matkhaumoi_1"));
        matkhaumoi_2 = driver.findElement(By.id("matkhaumoi_2"));

        matkhaucu.sendKeys(password);
        matkhaumoi_1.sendKeys(newpassword);
        matkhaumoi_2.sendKeys("");

        changeButton.click();

        String matkhaucuError = matkhaucu.getAttribute("validationMessage");
        String matkhaumoi_1Error = matkhaumoi_1.getAttribute("validationMessage");
        String matkhaumoi_2Error = matkhaumoi_2.getAttribute("validationMessage");

        // Kiểm tra xem thông báo lỗi có hiển thị đúng không

        Assert.assertFalse(matkhaucuError.contains("Please fill out this field"));
        Assert.assertFalse(matkhaumoi_1Error.contains("Please fill out this field"));
        Assert.assertTrue(matkhaumoi_2Error.contains("Please fill out this field"));
    }
    @Test
    public void WrongPassword() {
    // Mật khẩu cũ không đúng
        driver.navigate().refresh();
        changeButton = driver.findElement(By.name("btndoimatkhau"));
        matkhaucu = driver.findElement(By.id("matkhaucu"));
        matkhaumoi_1 = driver.findElement(By.id("matkhaumoi_1"));
        matkhaumoi_2 = driver.findElement(By.id("matkhaumoi_2"));

        matkhaucu.sendKeys("password");
        matkhaumoi_1.sendKeys("newpassword");
        matkhaumoi_2.sendKeys("newpassword");

        changeButton.click();

        WebElement noti = driver.findElement(By.id("noti"));
        String expected_noti = "Mật khẩu cũ không đúng!";
        String actual_noti = noti.getText();

        org.testng.Assert.assertEquals(actual_noti, expected_noti);
    }

    @Test
    public void WrongNewPassword() {
        driver.navigate().refresh();
        changeButton = driver.findElement(By.name("btndoimatkhau"));
        matkhaucu = driver.findElement(By.id("matkhaucu"));
        matkhaumoi_1 = driver.findElement(By.id("matkhaumoi_1"));
        matkhaumoi_2 = driver.findElement(By.id("matkhaumoi_2"));

        matkhaucu.sendKeys(password);
        matkhaumoi_1.sendKeys(newpassword);
        matkhaumoi_2.sendKeys("newpassword");

        changeButton.click();

        WebElement noti = driver.findElement(By.id("noti"));
        String expected_noti = "Mật khẩu mới không khớp. Vui lòng nhập lại!";
        String actual_noti = noti.getText();

        org.testng.Assert.assertEquals(actual_noti, expected_noti, "Khong thanh cong!");
    }

    @Test
    public void WrongOldAndNew() {
        driver.navigate().refresh();
        changeButton = driver.findElement(By.name("btndoimatkhau"));
        matkhaucu = driver.findElement(By.id("matkhaucu"));
        matkhaumoi_1 = driver.findElement(By.id("matkhaumoi_1"));
        matkhaumoi_2 = driver.findElement(By.id("matkhaumoi_2"));

        matkhaucu.sendKeys("password");
        matkhaumoi_1.sendKeys(newpassword);
        matkhaumoi_2.sendKeys(newpassword2);

        changeButton.click();

        WebElement noti = driver.findElement(By.id("noti"));
        String expected_noti = "Mật khẩu cũ không đúng!\nMật khẩu mới không khớp. Vui lòng nhập lại!";
        String actual_noti = noti.getText();

        org.testng.Assert.assertEquals(actual_noti, expected_noti, "Khong thanh cong!");
    }
    @Test
    public void Successful() {
        driver.navigate().refresh();
        changeButton = driver.findElement(By.name("btndoimatkhau"));
        matkhaucu = driver.findElement(By.id("matkhaucu"));
        matkhaumoi_1 = driver.findElement(By.id("matkhaumoi_1"));
        matkhaumoi_2 = driver.findElement(By.id("matkhaumoi_2"));

        matkhaucu.sendKeys(password);
        matkhaumoi_1.sendKeys(newpassword);
        matkhaumoi_2.sendKeys(newpassword);

        changeButton.click();

        WebElement noti = driver.findElement(By.id("noti"));
        String expected_noti = "Cập nhật mật khẩu thành công!";
        String actual_noti = noti.getText();

        org.testng.Assert.assertEquals(actual_noti, expected_noti, "Khong thanh cong!");
    }



    @AfterTest

    public void quit() {
        driver.quit();
    }

}
