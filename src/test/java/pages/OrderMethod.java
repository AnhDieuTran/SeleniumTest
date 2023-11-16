package pages;

import base.Method;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderMethod {

    private WebDriver driver;
    private WebElement fullname;
    private WebElement phone;
    private WebElement address;
    private WebElement note;

    Method method = new Method();

    public OrderMethod(WebDriver _driver) {
        driver = _driver;
    }

    public void setForm(WebElement _fullname, WebElement _phone, WebElement _address, WebElement _note) {
        fullname = _fullname;
        phone = _phone;
        address = _address;
        note = _note;
    }

    //Hàm xử lý JavaScript khi click vào element
    public void clickWithJS(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
        method.waitTime(1000);
    }

    // Hàm khi click chọn dòng sản phẩm trên navbar để trang tự động kéo đến các sản phẩm tương ứng.
    public void clickNavBar(String hrefLink) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("li.nav-item a.nav-link[href='" + hrefLink + "']")));
        link.click();
    }

    // Hàm cuộn thủ công kéo đến các sản phẩm tương ứng. Cuộn theo text.
    public void scrollByxpath(String tag, String textProduct) {
        WebElement element = driver.findElement(By.xpath("//" + tag + "[text()='" + textProduct + "']"));
        int elementPosition = element.getLocation().getY();
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, arguments[0] - 200);", elementPosition);
        method.waitTime(1000);
    }

    // Hàm cuộn thủ công kéo đến phần id tương ứng.
    public void scrollByid(String id) {
        WebElement element = driver.findElement(By.id(id));
        int elementPosition = element.getLocation().getY();
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, arguments[0] - 100);", elementPosition);
    }

    // Hàm nhập dữ liệu vào form.
    public void sendForm(String inputFullname, String inputPhone, String inputAddress, String inputNote) {
        fullname.clear();
        phone.clear();
        address.clear();
        note.clear();

        fullname.sendKeys(inputFullname);
        phone.sendKeys(inputPhone);
        address.sendKeys(inputAddress);
        note.sendKeys(inputNote);
        //waitTime(2000);
    }

    // Hàm thêm sản phẩm.
    public void addCardButton (WebElement addToCart, String productName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement cartButtonContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[contains(text(),'" + productName + "')]/ancestor::div[@class='card']")));

        addToCart = wait.until(ExpectedConditions.elementToBeClickable(cartButtonContainer.findElement(By.cssSelector("input[name='addcart']"))));

        clickWithJS(addToCart);
    }

    // Hàm thêm sản phẩm với số lượng chỉ định.
    public void addWithAmount (WebElement addToCart, String productName, String amount) {
        WebElement CartButton = driver.findElement(By.xpath("//h5[contains(text(),'" + productName + "')]/ancestor::div[@class='card']"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        addToCart = wait.until(ExpectedConditions.elementToBeClickable(CartButton.findElement(By.cssSelector("input[name='addcart']"))));

        WebElement amountProduct = CartButton.findElement(By.name("soluong"));
        amountProduct.clear();
        amountProduct.sendKeys(amount);

        clickWithJS(addToCart);
    }
}
