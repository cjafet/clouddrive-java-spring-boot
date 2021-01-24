package com.udacity.jwdnd.course1.cloudstorage.pages;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class NotePage {

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "new-note")
    private WebElement newNote;

    @FindBy(id = "edit-note")
    private WebElement editNote;

    @FindBy(id = "delete-note")
    private WebElement deleteNote;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(className = "note-title")
    private List<WebElement> noteTitles;

    @FindBy(className = "note-description")
    private List<WebElement> noteDescriptions;

    @FindBy(id = "save-note")
    private WebElement noteSubmit;

    public NotePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    public void setNote(String title, String description) {
        noteTitle.clear();
        noteTitle.sendKeys(title);
        noteDescription.clear();
        noteDescription.sendKeys(description);

    }

    public String getNoteTitle() {
        return noteTitles.get(0).getText();
    }

    public String getNoteDescription() {
        return noteDescriptions.get(0).getText();
    }

    public Note getNote() {
        return new Note(0,noteTitles.get(0).getText(), noteDescriptions.get(0).getText(), 0);
    }

    public Integer getNoteTitleLength() {
        return noteTitles.size();
    }

    public Integer getNoteDescriptionLength() {
        return noteDescriptions.size();
    }

    public void saveNote() {
        noteSubmit.click();
    }

    public void addNewNote() {
        newNote.click();
    }

    public void editNote() {
        editNote.click();
    }

    public void deleteNote() {
        deleteNote.click();
    }

    public void goToNotesPage() {
        notesTab.click();
    }

}
