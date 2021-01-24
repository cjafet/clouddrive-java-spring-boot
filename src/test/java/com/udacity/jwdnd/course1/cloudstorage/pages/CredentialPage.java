package com.udacity.jwdnd.course1.cloudstorage.pages;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CredentialPage {

    @FindBy(id = "credential-url")
    private WebElement url;

    @FindBy(id = "credential-username")
    private WebElement userName;

    @FindBy(id = "credential-password")
    private WebElement userPassword;

    @FindBy(id = "save-credential")
    private WebElement saveCredential;

    @FindBy(id = "edit-credential")
    private WebElement editCredential;

    @FindBy(id = "delete-credential")
    private WebElement delete;

    @FindBy(className = "credential-url")
    private List<WebElement> credentialUrl;

    @FindBy(className = "credential-username")
    private List<WebElement> credentialUsername;

    @FindBy(className = "credential-password")
    private List<WebElement> credentialPassword;

    public CredentialPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void setCredential(String siteUrl, String user, String password) {
        url.clear();
        url.sendKeys(siteUrl);

        userName.clear();
        userName.sendKeys(user);

        userPassword.clear();
        userPassword.sendKeys(password);

    }

    public void saveCredential() {
        saveCredential.click();
    }

    public void editCredential() {
        editCredential.click();
    }

    public void deleteCredential() {
        delete.click();
    }

    public Integer getUrlLength() {
        return credentialUrl.size();
    }

    public Integer getUsernameLength() {
        return credentialUsername.size();
    }

    public Integer getPasswordLength() {
        return credentialPassword.size();
    }

    public String getUrl() {
        return credentialUrl.get(0).getText();
    }

    public String getUsername() {
        return credentialUsername.get(0).getText();
    }

    public String getPassword() {
        return credentialPassword.get(0).getText();
    }

    public String getTextPassword() {
        return userPassword.getAttribute("value");
    }
}
