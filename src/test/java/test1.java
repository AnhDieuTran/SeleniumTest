import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class test1 {
    public static void main(String[] args) {


        //Khởi tạo browser với Chrome
        WebDriver driver;
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));

        // Mở trang Google
        driver.get("https://www.google.com");

        // Tìm element của ô tìm kiếm bằng ID (có thể sử dụng CSS selector hoặc XPath)
        WebElement searchBox = driver.findElement(By.name("q"));

        // Gửi ký tự tìm kiếm vào ô tìm kiếm
        searchBox.sendKeys("Selenium automation");

        // Submit tìm kiếm (ấn Enter)
        searchBox.submit();

        // Đợi kết quả xuất hiện
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("search")));

        // Đóng trình duyệt
        driver.quit();
    }
}
