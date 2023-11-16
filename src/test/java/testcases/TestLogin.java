package testcases;

import base.Method;
import helpers.CaptureHelper;
import helpers.ScreenShotHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.LogInMethod;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class TestLogin extends Method {
    private static final int numOfTest = 7;
    private static final int numOfInput = 3;
    private String[][] test = new String[numOfTest][numOfInput];
    private WebElement loginButton;
    private WebElement InputUsername;
    private WebElement InputPassWord;
    private WebElement errorElement;

    LogInMethod logMethod = new LogInMethod();
    CaptureHelper capLogin = new CaptureHelper();
    
    private void setElement() {
        loginButton = driver.findElement(By.name("dangnhap"));
        InputUsername = driver.findElement(By.name("username"));
        InputPassWord = driver.findElement(By.name("password"));
        logMethod.setForm(InputUsername, InputPassWord, loginButton);
    }

    private void setError() {
        errorElement = driver.findElement(By.id("error_message"));
    }



    @BeforeClass
    public void init() throws Exception {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }
        driver.get("https://foodstorehq.000webhostapp.com/management_web/account/");
        driver.manage().window().maximize();
        test = initExcel(numOfTest, numOfInput, "Sheet1");
        capLogin.startRecordATU("Login");
    }
    @Test
    public void testEmptyInput() {
        setElement();
        loginButton.click();

        setError();
        Assert.assertEquals(errorElement.getText(), test[0][2]);
    }

    @Test
    public void testEmptyUser() {
        setElement();

        logMethod.sendForm(test[1][0], test[1][1]);

        setError();
        Assert.assertEquals(errorElement.getText(), test[1][2]);
    }

    @Test
    public void testEmptyPassWord() {
        setElement();

        logMethod.sendForm(test[2][0], test[2][1]);

        setError();
        Assert.assertEquals(errorElement.getText(), test[2][2]);
    }

    @Test
    public void testInvalidUser() {
        setElement();

        logMethod.sendForm(test[3][0], test[3][1]);

        setError();
        Assert.assertEquals(errorElement.getText(), test[3][2]);
    }

    @Test
    public void testInvalidPassWord() {
        setElement();

        logMethod.sendForm(test[4][0], test[4][1]);

        setError();
        Assert.assertEquals(errorElement.getText(), test[4][2]);
    }

    @Test
    public void testInvalidUserAndPassWord() {
        setElement();

        logMethod.sendForm(test[5][0], test[5][1]);

        setError();
        Assert.assertEquals(errorElement.getText(), test[5][2]);
    }

    @Test
    public void testValidUserAndPassWord() {
        setElement();

        logMethod.sendForm(test[6][0], test[6][1]);

        WebElement title = driver.findElement(By.xpath("//h1[contains(text(), 'BẢNG THỐNG KÊ')]"));
        Assert.assertTrue(title.isDisplayed(), "Đăng nhập không thành công!");
    }

    @AfterMethod
    public void screenshot(ITestResult result) {
        ScreenShotHelper.captureScreenshot(result.getName(), driver);
    }

    @AfterClass
    public void quit() throws Exception{
        capLogin.stopRecordATU();
        driver.quit();
    }
}
