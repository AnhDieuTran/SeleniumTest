package pages;

import org.openqa.selenium.WebElement;

public class ChangePWMethod {

    private WebElement changeButton;
    private WebElement matkhaucu;
    private WebElement matkhaumoi_1;
    private WebElement matkhaumoi_2;

    public void setForm(WebElement _matkhaucu, WebElement _matkhaumoi_1, WebElement _matkhaumoi_2, WebElement _changeButton) {
        matkhaucu = _matkhaucu;
        matkhaumoi_1 = _matkhaumoi_1;
        matkhaumoi_2 = _matkhaumoi_2;
        changeButton = _changeButton;
    }

    // Hàm nhập dữ liệu vào form.
    public void sendForm(String inputmatkhaucu, String inputmatkhaumoi_1, String inputmatkhaumoi_2) {
        matkhaucu.clear();
        matkhaumoi_1.clear();
        matkhaumoi_2.clear();

        matkhaucu.sendKeys(inputmatkhaucu);
        matkhaumoi_1.sendKeys(inputmatkhaumoi_1);
        matkhaumoi_2.sendKeys(inputmatkhaumoi_2);
        //waitTime(1000);

        changeButton.click();
        //waitTime(2000);
    }

}
