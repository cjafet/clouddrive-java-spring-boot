package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.pages.CredentialPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialTest {

    @LocalServerPort
    private Integer port;

    private static WebDriver driver;

    private LoginPage loginPage;
    private SignupPage signupPage;
    private HomePage homePage;
    private CredentialPage credentialPage;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private CredentialService credentialService;


    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterAll
    public static void afterAll() {
        driver.quit();
    }

    @Test
    public void addCredential() {

        // Register user
        registerUser();

        // Log user in
        logUser();

        driver.get("http://localhost:" + port + "/home");
        WebDriverWait wait = new WebDriverWait(driver, 3000);
        wait.until(driver -> driver.findElement(By.id("logout")));

        goToCredentialTab();

        wait.until(driver -> driver.findElement(By.id("new-credential")));

        if (driver instanceof JavascriptExecutor) {
            ((JavascriptExecutor)driver).executeScript("showCredentialModal();");
        }

        wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url"))).click();
        setCredential("www.github.com", "cjafet", "123456");

        driver.get("http://localhost:" + port + "/home");
        wait.until(driver -> driver.findElement(By.id("logout")));

        goToCredentialTab();

        WebDriverWait wait2 = new WebDriverWait(driver, 3000);
        wait2.until(ExpectedConditions.elementToBeClickable(By.id("new-credential")));

        List<Credential> credential = credentialService.getCredentialById(1);
        String encrypt = encryptionService.encryptValue("123456", credential.get(0).getKey());
        assertEquals(credential.get(0).getUrl(), credentialPage.getUrl());
        assertEquals(credential.get(0).getUserName(), credentialPage.getUsername());
        assertEquals(encrypt, credentialPage.getPassword());

        credentialPage.editCredential();
        wait.until(driver -> driver.findElement(By.id("credential-password")));
        String userPassword = credentialPage.getTextPassword();
        assertEquals("123456", userPassword);

        wait2.until(ExpectedConditions.elementToBeClickable(By.id("credential-url")));
        setCredential("www.github.com", "carlos", "654321");
        driver.get("http://localhost:" + port + "/home");
        wait.until(driver -> driver.findElement(By.id("logout")));

        goToCredentialTab();
        wait2.until(ExpectedConditions.elementToBeClickable(By.id("new-credential")));
        credential = credentialService.getCredentialById(1);
        assertEquals(credential.get(0).getUrl(), credentialPage.getUrl());
        assertEquals(credential.get(0).getUserName(), credentialPage.getUsername());
        assertEquals(credential.get(0).getUserPassword(), credentialPage.getPassword());

        credentialPage.deleteCredential();

        driver.get("http://localhost:" + port + "/home");
        wait.until(driver -> driver.findElement(By.id("logout")));

        goToCredentialTab();
        wait2.until(ExpectedConditions.elementToBeClickable(By.id("new-credential")));

        assertEquals(0, credentialPage.getUrlLength());
        assertEquals(0, credentialPage.getUsernameLength());
        assertEquals(0, credentialPage.getPasswordLength());



    }


    // Helper Methods

    private void logUser() {
        driver.get("http://localhost:" + port + "/login");
        loginPage = new LoginPage(driver);
        loginPage.setUser("carlos");
        loginPage.setPassword("carlos");
        loginPage.login();
    }

    private void registerUser() {
        driver.get("http://localhost:" + port + "/signup");
        signupPage = new SignupPage(driver);
        signupPage.setFirstName("Carlos");
        signupPage.setLastName("Jafet Neto");
        signupPage.setUserName("carlos");
        signupPage.setUserPassword("carlos");
        signupPage.signUp();
    }

    private void goToCredentialTab() {
        homePage = new HomePage(driver);
        homePage.goToCredentialPage();
    }

    private void setCredential(String siteUrl, String user, String password) {
        credentialPage = new CredentialPage(driver);
        credentialPage.setCredential(siteUrl, user, password);
        credentialPage.saveCredential();
    }

}
