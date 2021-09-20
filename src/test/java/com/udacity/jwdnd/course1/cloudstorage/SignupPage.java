package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    private WebElement inputLastName;

    @FindBy(id = "inputUserName")
    private WebElement inputUsername;

    @FindBy(id = "inputPassWord")
    private WebElement inputPassword;

    @FindBy(id = "login-link")
    private WebElement loginLink;

    @FindBy(id = "error-msg")
    private WebElement errorMsg;

    @FindBy(id = "submit-button")
    private WebElement submitButton;


    public SignupPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    public void setInputFirstName(String value){
        inputFirstName.sendKeys(value);
    }

    public void setInputLastName(String value){
        inputLastName.sendKeys(value);
    }
    public void setInputUsername(String value){
        inputUsername.sendKeys(value);
    }

    public void setInputPassword(String value){
        inputPassword.sendKeys(value);
    }

    public WebElement getLoginMessage(){
        return loginLink;
    }

    public WebElement getErrorMessage(){
        return errorMsg;
    }

    public void submitForm() {
        submitButton.submit();
    }

    public void registerUser(String firstName, String lastName, String username, String password){
        setInputFirstName(String.valueOf(firstName));
        setInputLastName(String.valueOf(lastName));
        setInputUsername(String.valueOf(username));
        setInputPassword(String.valueOf(password));
        submitForm();
    }
}
