package testcases;

import base.Method;
import helpers.CaptureHelper;
import helpers.ScreenShotHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import pages.ChangePWMethod;

public class TestChangePW extends Method {
    private static final int numOfTest = 7;
    private static final int numOfInput = 4;
    private String[][] test = new String[numOfTest][numOfInput];

    private String username = "admin";
    private String password = "1234";

    private WebElement changeButton;
    private WebElement matkhaucu;
    private WebElement matkhaumoi_1;
    private WebElement matkhaumoi_2;
    
    private WebElement noti;
    
    ChangePWMethod pwMethod = new ChangePWMethod();
    CaptureHelper capChangePW = new CaptureHelper();

    @BeforeClass
    public void setUp() throws Exception {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }
        driver.get("https://foodstorehq.000webhostapp.com/management_web/account/");
        driver.manage().window().maximize();
        test = initExcel(numOfTest, numOfInput, "Sheet2");
        capChangePW.startRecordATU("ChangPW");

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
        pwMethod.sendForm(test[0][0], test[0][1], test[0][2]);
        assertMessage(matkhaucu, true);
    }

    @Test
    public void EmptyPassword() {
        formElement();
        
        pwMethod.sendForm(test[1][0], test[1][1], test[1][2]);

        assertMessage(matkhaucu, false);
        assertMessage(matkhaumoi_1, true);
    }

    @Test
    public void EmptyRePassword() {
        formElement();
        
        pwMethod.sendForm(test[2][0], test[2][1], test[2][2]);

        assertMessage(matkhaucu, false);
        assertMessage(matkhaumoi_1, false);
        assertMessage(matkhaumoi_2, true);
    }

    // Mật khẩu cũ không đúng
    @Test
    public void WrongPassword() {
        formElement();

        pwMethod.sendForm(test[3][0], test[3][1], test[3][2]);

        noti = driver.findElement(By.id("noti"));
        String actual_noti = noti.getText();

        org.testng.Assert.assertEquals(actual_noti, test[3][3]);
    }

    @Test
    public void WrongNewPassword() {
        formElement();

        pwMethod.sendForm(test[4][0], test[4][1], test[4][2]);

        noti = driver.findElement(By.id("noti"));
        String actual_noti = noti.getText();

        org.testng.Assert.assertEquals(actual_noti, test[4][3], "Khong thanh cong!");
    }

    @Test
    public void WrongOldAndNew() {
        formElement();

        pwMethod.sendForm(test[5][0], test[5][1], test[5][2]);

        noti = driver.findElement(By.id("noti"));
        String actual_noti = noti.getText();

        org.testng.Assert.assertEquals(actual_noti, test[5][3], "Khong thanh cong!");
    }

    @Test
    public void Successful() {
        formElement();

        pwMethod.sendForm(test[6][0], test[6][1], test[6][2]);

        noti = driver.findElement(By.id("noti"));
        String actual_noti = noti.getText();

        org.testng.Assert.assertEquals(actual_noti, test[6][3], "Khong thanh cong!");
    }

    @AfterMethod
    public void screenshot(ITestResult result) {
        ScreenShotHelper.captureScreenshot(result.getName(), driver);
    }

    @AfterClass
    public void quit() throws Exception{
        capChangePW.stopRecordATU();
        driver.quit();
    }

    // Khởi tạo các element trong form.
    protected void formElement() {
        String currentUrl = driver.getCurrentUrl();
        driver.get(currentUrl);

        changeButton = driver.findElement(By.name("btndoimatkhau"));
        matkhaucu = driver.findElement(By.id("matkhaucu"));
        matkhaumoi_1 = driver.findElement(By.id("matkhaumoi_1"));
        matkhaumoi_2 = driver.findElement(By.id("matkhaumoi_2"));
        pwMethod.setForm(matkhaucu, matkhaumoi_1, matkhaumoi_2, changeButton);
    }
}
