package Method;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class OrderMethod extends Method {

    private WebElement fullname;
    private WebElement phone;
    private WebElement address;
    private WebElement note;

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
        waitTime(2000);
    }

    // Hàm khi click chọn dòng sản phẩm trên navbar để trang tự động kéo đến các sản phẩm tương ứng.
    public void clickNavBar(String hrefLink) {
        WebElement link = driver.findElement(By.cssSelector("li.nav-item a.nav-link[href='" + hrefLink + "']"));
        link.click();
        waitTime(2000);
    }

    // Hàm cuộn thủ công kéo đến các sản phẩm tương ứng. Cuộn theo text.
    public void scrollByxpath(String tag, String textProduct) {
        WebElement element = driver.findElement(By.xpath("//" + tag + "[text()='" + textProduct + "']"));
        int elementPosition = element.getLocation().getY();
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, arguments[0] - 200);", elementPosition);
        waitTime(2000);
    }

    // Hàm cuộn thủ công kéo đến phần id tương ứng.
    public void scrollByid(String id) {
        WebElement element = driver.findElement(By.id(id));
        int elementPosition = element.getLocation().getY();
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, arguments[0]-100);", elementPosition);
        waitTime(2000);
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
        waitTime(2000);
    }

    // Hàm thêm sản phẩm.
    public void addCardButton (WebElement addToCart, String productName) {
        WebElement CartButton = driver.findElement(By.xpath("//h5[contains(text(),'" + productName + "')]/ancestor::div[@class='card']"));
        addToCart = CartButton.findElement(By.cssSelector("input[name='addcart']"));
        clickWithJS(addToCart);
    }

    // Hàm thêm sản phẩm với số lượng chỉ định.
    public void addWithAmount (WebElement addToCart, String productName, String amount) {
        WebElement CartButton = driver.findElement(By.xpath("//h5[contains(text(),'" + productName + "')]/ancestor::div[@class='card']"));
        addToCart = CartButton.findElement(By.cssSelector("input[name='addcart']"));
        WebElement amountProduct = CartButton.findElement(By.name("soluong"));
        amountProduct.clear();
        amountProduct.sendKeys(amount);
        waitTime(2000);
        clickWithJS(addToCart);
    }
}
