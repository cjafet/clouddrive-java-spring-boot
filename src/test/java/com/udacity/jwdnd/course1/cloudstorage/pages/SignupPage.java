package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    @FindBy(id = "inputFirstName")
    private WebElement firstName;

    @FindBy(id = "inputLastName")
    private WebElement lastName;

    @FindBy(id="inputUsername")
    private WebElement userName;

    @FindBy(id = "inputPassword")
    private WebElement userPassword;

    @FindBy(id = "signup-button")
    private WebElement submit;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void setFirstName(String firstName) {
        this.firstName.sendKeys(firstName);
    }

    public void setLastName(String lastName) {
        this.lastName.sendKeys(lastName);
    }

    public void setUserName(String userName) {
        this.userName.sendKeys(userName);
    }

    public void setUserPassword(String userPassword) {
        this.userPassword.sendKeys(userPassword);
    }

    public void signUp() {
        this.submit.click();
    }

}
