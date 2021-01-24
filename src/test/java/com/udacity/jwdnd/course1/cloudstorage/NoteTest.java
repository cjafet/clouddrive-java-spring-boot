package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.NotePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteTest {

    @LocalServerPort
    private Integer port;

    private static WebDriver driver;
    private NotePage notePage;
    private LoginPage loginPage;
    private SignupPage signupPage;
    private HomePage homePage;


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
    public void addNote() {

        // Register user
        registerUser();

        // Log user in
        logUser();

        driver.get("http://localhost:" + port + "/home");
        WebDriverWait wait = new WebDriverWait(driver, 3000);
        wait.until(driver -> driver.findElement(By.id("logout")));

        goToNoteTab();

        wait.until(driver -> driver.findElement(By.id("new-note")));

        if (driver instanceof JavascriptExecutor) {
            ((JavascriptExecutor)driver).executeScript("showNoteModal();");
        }

        wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).click();
        setNote("Test Note Title", "Test note description");

        driver.get("http://localhost:" + port + "/home");
        wait.until(driver -> driver.findElement(By.id("logout")));

        goToNoteTab();

        WebDriverWait wait2 = new WebDriverWait(driver, 3000);
        wait2.until(ExpectedConditions.elementToBeClickable(By.id("new-note")));

        Note note = notePage.getNote();
        assertEquals("Test Note Title", note.getNoteTitle());
        assertEquals("Test note description", note.getNoteDescription());

        notePage.editNote();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).click();
        setNote("Test Edit Note Title", "Test edit note description");

        driver.get("http://localhost:" + port + "/home");
        wait.until(driver -> driver.findElement(By.id("logout")));

        goToNoteTab();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("new-note")));

        Note editedNote = notePage.getNote();
        assertEquals("Test Edit Note Title", editedNote.getNoteTitle());
        assertEquals("Test edit note description", editedNote.getNoteDescription());
        assertEquals(1, notePage.getNoteTitleLength());
        assertEquals(1, notePage.getNoteDescriptionLength());

        notePage.deleteNote();

        driver.get("http://localhost:" + port + "/home");
        wait.until(driver -> driver.findElement(By.id("logout")));

        goToNoteTab();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("new-note")));

        assertEquals(0, notePage.getNoteTitleLength());
        assertEquals(0, notePage.getNoteDescriptionLength());

    }

    // Helper Methods

    private void goToNoteTab() {
        homePage = new HomePage(driver);
        homePage.goToNotesPage();
    }

    private void setNote(String title, String description) {
        notePage = new NotePage(driver);
        notePage.setNote(title, description);
        notePage.saveNote();
    }

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
