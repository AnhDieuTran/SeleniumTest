package testcases;

import base.Method;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

import pages.OrderMethod;

public class TestOrder extends Method {
    private static final int numOfTest = 9;
    private static final int numOfInput = 9;
    private String[][] test = new String[numOfTest][numOfInput];
    OrderMethod orderMethod;

    private WebElement productPage;
    private WebElement addToCartButton;
    WebElement message;
    
    private void setProductPage() {
        productPage = driver.findElement(By.xpath("//a[contains(text(), 'Sản phẩm')]"));
        orderMethod.clickWithJS(productPage);
        waitTime(1000);
    }

    private void checkMess(WebElement element, String expectedOutput, String messError) {
        String actualMess = element.getText();
        org.testng.Assert.assertEquals(actualMess, expectedOutput, messError);
    }

    @BeforeClass
    public void init() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }
        driver.get("https://foodstorehq.000webhostapp.com/food_store_web/build/product.php");
        orderMethod = new OrderMethod(driver);
        driver.manage().window().maximize();
        test = initExcel(numOfTest, numOfInput, "Sheet3");
    }

    //Kiểm tra khi không có gì trong giỏ hàng
    @Test (priority = 0)
    public void EmptyCartTest() {
        WebElement image = driver.findElement(By.cssSelector("img[src='../img/icon/cart.jpg']"));
        if (image.isDisplayed()) {
            image.click();

            WebElement messEmptyCart = driver.findElement(By.cssSelector("p.text-center"));
            checkMess(messEmptyCart,test[0][8], "Giỏ hàng không rỗng hoặc Lỗi thông báo.");
        } else {
            System.out.println("Hình ảnh không được hiển thị.");
        }
    }

    // Thêm sản phẩm đã hết vào giỏ.
    @Test (priority = 1)
    public void AddOutOfStockProduct() {
        setProductPage();
        orderMethod.clickNavBar(test[1][0]);
        orderMethod.addCardButton(addToCartButton, test[1][2]);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            String output = alert.getText();

            Assert.assertTrue(output.contains(test[1][8]), "Lỗi không hiện hộp thoại");
            alert.accept();
        } catch (NoAlertPresentException e) {
            System.out.println("Không có cảnh báo nào xuất hiện.");
        }

        WebElement productTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1.text-center.introduce")));
        org.testng.Assert.assertEquals(productTitle.getText(),"Sản phẩm", "Lỗi không quay lại được trang sản phẩm!");
    }

    // Thêm 1 sản phẩm vào giỏ
    @Test (priority = 2)
    public void Add1Product(){
        orderMethod.scrollByxpath(test[2][0], test[2][1]);

        orderMethod.addCardButton(addToCartButton, test[2][2]);
    }

    // Xóa giỏ hàng
    @Test (priority = 3)
    public void DeleteCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement deleteCartButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='XÓA GIỎ HÀNG']")));
        deleteCartButton.click();

        WebElement messEmptyCart = driver.findElement(By.cssSelector("p.text-center"));
        checkMess(messEmptyCart,test[3][8], "Giỏ hàng không rỗng hoặc Lỗi thông báo.");
    }

    // Thêm sản phẩm với số lượng nhiều (Tối đa là 10)
    @Test (priority = 4)
    public void AddBulkProduct() {
        setProductPage();
        orderMethod.clickNavBar(test[4][0]);
        waitTime(1000);
        orderMethod.addWithAmount(addToCartButton, test[4][2], test[4][3]);
        waitTime(1000);
    }

    // Ấn nút tiếp tục đặt hàng
    @Test (priority = 5)
    public void ContinueOrder() {
        WebElement continueButton = driver.findElement(By.xpath("//input[@value='TIẾP TỤC ĐẶT HÀNG']"));
        continueButton.click();
        
        orderMethod.scrollByxpath(test[5][0], test[5][1]);

        orderMethod.addCardButton(addToCartButton, test[5][2]);
    }

    private WebElement fullname;
    private WebElement phone;
    private WebElement address;
    private WebElement note;


    // Không điền thông tin bắt buộc
    @Test (priority = 6)
    public void Order1() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement orderButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@value='ĐẶT HÀNG']")));
        orderMethod.clickWithJS(orderButton);
        orderMethod.scrollByid("infoCustomer");
        waitTime(1000);

        fullname = driver.findElement(By.id("hoten"));
        phone = driver.findElement(By.id("dienthoai"));
        address = driver.findElement(By.id("diachi"));
        note = driver.findElement(By.id("floatingTextarea"));

        orderMethod.setForm(fullname, phone, address, note);

        orderMethod.sendForm(test[6][4], test[6][5], test[6][6], test[6][7]);

        assertMessage(fullname, true);
        assertMessage(phone, true);
        assertMessage(address, true);

        WebElement continueButton = driver.findElement(By.id("continueButton"));
        orderMethod.clickWithJS(continueButton);

        message = driver.findElement(By.id("error-message"));
        checkMess(message, test[6][8], "Lỗi hiện thông báo!");
    }

    // Điền sai định dạng SĐT
    @Test (priority = 7)
    public void Order2() {
        orderMethod.scrollByid("infoCustomer");
        waitTime(1000);
        orderMethod.sendForm(test[7][4], test[7][5], test[7][6], test[7][7]);

        assertMessage(fullname, false);
        assertMessage(phone, false);
        assertMessage(address ,false);

        WebElement continueButton = driver.findElement(By.id("continueButton"));
        orderMethod.clickWithJS(continueButton);

        message = driver.findElement(By.id("error-message"));
        checkMess(message, test[7][8], "Lỗi hiện thông báo!");
    }

    // Điền đúng hết thông tin
    @Test (priority = 8)
    public void Order3() {
        orderMethod.sendForm(test[8][4], test[8][5], test[8][6], test[8][7]);

        assertMessage(fullname, false);
        assertMessage(phone, false);
        assertMessage(address, false);

        WebElement continueButton = driver.findElement(By.id("continueButton"));
        orderMethod.clickWithJS(continueButton);
        orderMethod.scrollByid("infoCustomer");
        waitTime(1000);

        WebElement paymentMethod = driver.findElement(By.id("pttt2"));
        paymentMethod.click();
        waitTime(2000);

        WebElement orderButton = driver.findElement(By.name("dongydathang"));
        orderMethod.clickWithJS(orderButton);

        message = driver.findElement(By.xpath("//h4[contains(text(), 'CHÚC MỪNG BẠN ĐÃ ĐẶT HÀNG THÀNH CÔNG')]"));
        checkMess(message, test[8][8], "Lỗi đặt hàng chưa thành công!");
    }

    @AfterClass
    public void quit() {
        driver.quit();
    }
}
