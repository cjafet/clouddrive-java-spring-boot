package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationTest {

    @LocalServerPort
    private Integer port;

    private LoginPage loginPage;
    private SignupPage signupPage;

    private static WebDriver driver;

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
    void unregisteredUser() {

        // Check if the login page is accessible by an unregistered user
        driver.get("http://localhost:" + port + "/login");
        assertEquals("Login", driver.getTitle());

        // Check if the register page is accessible by an unregistered user
        driver.get("http://localhost:" + port + "/signup");
        assertEquals("Sign Up", driver.getTitle());

        // Check if the home page is not accessible by an unregistered user
        driver.get("http://localhost:" + port + "/home");
        assertEquals("Login", driver.getTitle());


    }

    @Test
    void signUser() {

        // Register user
        registerUser();

        // Log user in
        logUser();

        driver.get("http://localhost:" + port + "/home");

        // Check if user is redirect to the home page after login
        assertEquals("Home", driver.getTitle());

        // Logout user
        WebElement logout = driver.findElement(By.id("logout"));
        logout.click();

        // Check if user have been logged out and redirect to the login page
        assertEquals("Login", driver.getTitle());

        // Check if user can not access home page after sign out
        driver.get("http://localhost:" + port + "/home");
        assertEquals("Login", driver.getTitle());

    }


    // Helper methods

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


}
