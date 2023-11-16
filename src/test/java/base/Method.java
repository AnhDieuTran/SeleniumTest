package base;

import helpers.ExcelHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class Method {
    public WebDriver driver;


    //Hàm này để tùy chọn Browser. Cho chạy trước khi gọi class này (BeforeClass)
    private void setDriver(String browserType) {
        switch (browserType) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                System.out.println("Browser: " + browserType + " is invalid, Launching Chrome as browser of choice...");
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
        }
    }


    // Chạy hàm initializeTestBaseSetup trước hết khi class này được gọi
    @Parameters("browserType")
    @BeforeClass
    public void initializeTest(@Optional String browserType) {
        try {
            setDriver(browserType);
        } catch (Exception e) {
            System.out.println("Error..." + e.getStackTrace());
        }
    }

    @AfterClass
    public void tearDown() throws Exception {
        driver.quit();
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

    public String[][] initExcel(int numOfTest, int numOfInput, String SheetName) {
        String[][] test = new String[numOfTest][numOfInput];
        ExcelHelper excel = new ExcelHelper();
        try {
            excel.setExcelFile("./src/main/resources/testData.xlsx", SheetName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        for (int i = 0 ; i < numOfTest; i++) {
            for (int j = 0; j < numOfInput; j++) {
                test[i][j] = excel.getCellData(i+1, j+1);
            }
        }
        return test;
    }
}
