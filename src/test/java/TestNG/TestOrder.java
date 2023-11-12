package TestNG;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

import Method.OrderMethod;

public class TestOrder {
    private WebDriver driver;
    OrderMethod orderMethod = new OrderMethod();

    private WebElement productPage;
    private WebElement addToCartButton;
    
    private void setProductPage() {
        productPage = driver.findElement(By.xpath("//a[contains(text(), 'Sản phẩm')]"));
        orderMethod.clickWithJS(productPage);
    }

    private void checkMess(WebElement element, String expectedOutput, String messError) {
        String actualMess = element.getText();
        org.testng.Assert.assertEquals(actualMess, expectedOutput, messError);
        orderMethod.waitTime(2000);
    }

    @BeforeTest
    public void init() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://foodstorehq.000webhostapp.com/food_store_web/build/product.php");
        driver.manage().window().maximize();
        orderMethod.setDriver(driver);
    }

    //Kiểm tra khi không có gì trong giỏ hàng
    @Test (priority = 0)
    public void EmptyCartTest() {
        WebElement image = driver.findElement(By.cssSelector("img[src='../img/icon/cart.jpg']"));
        if (image.isDisplayed()) {
            image.click();
            String expectedOutput = "Giỏ hàng trống, vui lòng chọn sản phẩm để tiếp tục.";
            WebElement messEmptyCart = driver.findElement(By.cssSelector("p.text-center"));
            checkMess(messEmptyCart,expectedOutput, "Giỏ hàng không rỗng hoặc Lỗi thông báo.");
        } else {
            System.out.println("Hình ảnh không được hiển thị.");
        }
    }

    // Thêm sản phẩm đã hết vào giỏ.
    @Test (priority = 1)
    public void AddOutOfStockProduct() {
        setProductPage();

        orderMethod.clickNavBar("#com");
        orderMethod.addCardButton(addToCartButton, "Cơm thịt nướng");

        Alert alert = driver.switchTo().alert();
        String output = alert.getText();

        Assert.assertTrue(output.contains("Sản phẩm này đã hết. Bạn có muốn quay lại trang sản phẩm không?"), "Lỗi không hiện hộp thoại");
        alert.accept();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement productTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1.text-center.introduce")));

        org.testng.Assert.assertEquals(productTitle.getText(),"Sản phẩm", "Lỗi không quay lại được trang sản phẩm!");
    }

    // Thêm 1 sản phẩm vào giỏ
    @Test (priority = 2)
    public void Add1Product(){
        orderMethod.scrollByxpath("h2", "Gà");

        orderMethod.addCardButton(addToCartButton, "Gà rán sốt cay");
    }

    // Xóa giỏ hàng
    @Test (priority = 3)
    public void DeleteCart() {
        WebElement deleteCartButton = driver.findElement(By.xpath("//input[@value='XÓA GIỎ HÀNG']"));
        deleteCartButton.click();

        String expectedOutput = "Giỏ hàng trống, vui lòng chọn sản phẩm để tiếp tục.";
        WebElement messEmptyCart = driver.findElement(By.cssSelector("p.text-center"));
        checkMess(messEmptyCart,expectedOutput, "Giỏ hàng không rỗng hoặc Lỗi thông báo.");
    }

    // Thêm sản phẩm với số lượng nhiều (Tối đa là 10)
    @Test (priority = 4)
    public void AddBulkProduct() {
        setProductPage();

        orderMethod.clickNavBar("#douong");

        orderMethod.addWithAmount(addToCartButton, "Coca Cola", "8");
    }

    // Ấn nút tiếp tục đặt hàng
    @Test (priority = 5)
    public void ContinueOrder() {
        WebElement continueButton = driver.findElement(By.xpath("//input[@value='TIẾP TỤC ĐẶT HÀNG']"));
        continueButton.click();
        
        orderMethod.scrollByxpath("h2", "Cơm");

        orderMethod.addCardButton(addToCartButton, "Cơm rang kim chi");
    }

    private WebElement fullname;
    private WebElement phone;
    private WebElement address;
    private WebElement note;


    // Không điền thông tin bắt buộc
    @Test (priority = 6)
    public void Order1() {
        WebElement orderButton = driver.findElement(By.xpath("//input[@value='ĐẶT HÀNG']"));
        orderMethod.clickWithJS(orderButton);

        orderMethod.scrollByid("infoCustomer");

        fullname = driver.findElement(By.id("hoten"));
        phone = driver.findElement(By.id("dienthoai"));
        address = driver.findElement(By.id("diachi"));
        note = driver.findElement(By.id("floatingTextarea"));

        orderMethod.setForm(fullname, phone, address, note);

        orderMethod.sendForm("", "", "", "");

        orderMethod.assertMessage(fullname, true);
        orderMethod.assertMessage(phone, true);
        orderMethod.assertMessage(address, true);

        WebElement continueButton = driver.findElement(By.id("continueButton"));
        orderMethod.clickWithJS(continueButton);

        String expectedOutput = "Vui lòng điền đầy đủ thông tin để tiếp tục.";
        WebElement message = driver.findElement(By.id("error-message"));
        checkMess(message, expectedOutput, "Lỗi hiện thông báo!");
    }

    // Điền sai định dạng SĐT
    @Test (priority = 7)
    public void Order2() {
        orderMethod.sendForm("Nguyen Tu Nhi", "abnchd", "144 - Xuan Thuy - Cau Giay", "Thêm cay.");

        orderMethod.assertMessage(fullname, false);
        orderMethod.assertMessage(phone, false);
        orderMethod.assertMessage(address ,false);

        WebElement continueButton = driver.findElement(By.id("continueButton"));
        orderMethod.clickWithJS(continueButton);

        String expectedOutput = "Định dạng số điện thoại sai. Vui lòng điền lại.";
        WebElement message = driver.findElement(By.id("error-message"));
        checkMess(message, expectedOutput, "Lỗi hiện thông báo!");
    }

    // Điền đúng hết thông tin
    @Test (priority = 8)
    public void Order3() {
        orderMethod.sendForm("Hoàng Ánh Dương", "0356651308",
                "1a P. Thọ Lão, Đống Mác, Hai Bà Trưng, Hà Nội", "Không cay, thêm đá");

        orderMethod.assertMessage(fullname, false);
        orderMethod.assertMessage(phone, false);
        orderMethod.assertMessage(address, false);

        WebElement continueButton = driver.findElement(By.id("continueButton"));
        orderMethod.clickWithJS(continueButton);

        WebElement paymentMethod = driver.findElement(By.id("pttt2"));
        paymentMethod.click();
        orderMethod.waitTime(2000);

        WebElement orderButton = driver.findElement(By.name("dongydathang"));
        orderMethod.clickWithJS(orderButton);

        String expectedOutput = "CHÚC MỪNG BẠN ĐÃ ĐẶT HÀNG THÀNH CÔNG";
        WebElement message = driver.findElement(By.xpath("//h4[contains(text(), 'CHÚC MỪNG BẠN ĐÃ ĐẶT HÀNG THÀNH CÔNG')]"));
        checkMess(message, expectedOutput, "Lỗi đặt hàng chưa thành công!");
    }

    @AfterTest
    public void quit() {
        driver.quit();
    }
}
