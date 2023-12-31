package pages;

import org.openqa.selenium.WebElement;

public class LogInMethod {
    private WebElement loginButton;
    private WebElement InputUsername;
    private WebElement InputPassWord;

    public void setForm(WebElement _InputUsername, WebElement _InputPassWord, WebElement _loginButton) {
        InputUsername = _InputUsername;
        InputPassWord = _InputPassWord;
        loginButton = _loginButton;
    }

    public void sendForm(String inputInputUsername, String inputInputPassWord) {
        InputUsername.clear();
        InputPassWord.clear();

        InputUsername.sendKeys(inputInputUsername);
        InputPassWord.sendKeys(inputInputPassWord);

        loginButton.click();
    }
}
