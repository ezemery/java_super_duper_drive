package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id = "inputUserName")
    WebElement inputUsername;

    @FindBy(id = "inputPassWord")
    WebElement inputPassword;

    @FindBy(id = "submit-button")
    WebElement submitButton;

    @FindBy(id = "error-msg")
    WebElement errorMsg;

    public LoginPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public void setInputUsername(String value){
        inputUsername.sendKeys(value);
    }

    public void setInputPassword(String value){
        inputPassword.sendKeys(value);
    }

    public String getErrorMessage(){
        return errorMsg.getText();
    }
    public void submitForm() {
        submitButton.submit();
    }

    public void loginUser(String username, String password){
        setInputUsername(String.valueOf(username));
        setInputPassword(String.valueOf(password));
        submitForm();
    }
}
