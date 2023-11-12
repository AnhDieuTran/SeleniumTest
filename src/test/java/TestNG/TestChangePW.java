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

import Method.ChangePWMethod;

public class TestChangePW {
    WebDriver driver;
    private String username = "admin";
    private String password = "1234";
    private String newpassword = "1234";
    private String newpassword2 = "abcdef";

    private WebElement changeButton;
    private WebElement matkhaucu;
    private WebElement matkhaumoi_1;
    private WebElement matkhaumoi_2;
    
    private WebElement noti;
    
    ChangePWMethod pwMethod = new ChangePWMethod();

    // Khởi tạo các element trong form.
    private void formElement() {
        driver.navigate().refresh();
        changeButton = driver.findElement(By.name("btndoimatkhau"));
        matkhaucu = driver.findElement(By.id("matkhaucu"));
        matkhaumoi_1 = driver.findElement(By.id("matkhaumoi_1"));
        matkhaumoi_2 = driver.findElement(By.id("matkhaumoi_2"));
        pwMethod.setForm(matkhaucu, matkhaumoi_1, matkhaumoi_2, changeButton);
    }


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
        formElement();
        pwMethod.sendForm("", "", "");
        pwMethod.assertMessage(matkhaucu, true);
    }

    @Test
    public void EmptyPassword() {
        formElement();
        
        pwMethod.sendForm(password, "", "");

        pwMethod.assertMessage(matkhaucu, false);
        pwMethod.assertMessage(matkhaumoi_1, true);
    }

    @Test
    public void EmptyRePassword() {
        formElement();
        
        pwMethod.sendForm(password, newpassword, "");

        pwMethod.assertMessage(matkhaucu, false);
        pwMethod.assertMessage(matkhaumoi_1, false);
        pwMethod.assertMessage(matkhaumoi_2, true);
    }

    // Mật khẩu cũ không đúng
    @Test
    public void WrongPassword() {
        formElement();

        pwMethod.sendForm("password", "newpassword", "newpassword");

        noti = driver.findElement(By.id("noti"));
        String expected_noti = "Mật khẩu cũ không đúng!";
        String actual_noti = noti.getText();

        org.testng.Assert.assertEquals(actual_noti, expected_noti);
    }

    @Test
    public void WrongNewPassword() {
        formElement();

        pwMethod.sendForm(password, newpassword, "newpassword");

        noti = driver.findElement(By.id("noti"));
        String expected_noti = "Mật khẩu mới không khớp. Vui lòng nhập lại!";
        String actual_noti = noti.getText();

        org.testng.Assert.assertEquals(actual_noti, expected_noti, "Khong thanh cong!");
    }

    @Test
    public void WrongOldAndNew() {
        formElement();

        pwMethod.sendForm("password", newpassword, newpassword2);

        noti = driver.findElement(By.id("noti"));
        String expected_noti = "Mật khẩu cũ không đúng!\nMật khẩu mới không khớp. Vui lòng nhập lại!";
        String actual_noti = noti.getText();

        org.testng.Assert.assertEquals(actual_noti, expected_noti, "Khong thanh cong!");
    }

    @Test
    public void Successful() {
        formElement();

        pwMethod.sendForm(password, newpassword, newpassword);

        noti = driver.findElement(By.id("noti"));
        String expected_noti = "Cập nhật mật khẩu thành công!";
        String actual_noti = noti.getText();

        org.testng.Assert.assertEquals(actual_noti, expected_noti, "Khong thanh cong!");
    }

    @AfterTest
    public void quit() {
        driver.quit();
    }

}
