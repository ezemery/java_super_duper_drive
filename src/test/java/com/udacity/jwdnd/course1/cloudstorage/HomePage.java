package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePage {

    @FindBy(id = "logout-button")
    WebElement LogoutButton;

    @FindBy(id = "nav-files-tab")
    WebElement NavFileTab;

    @FindBy(id = "nav-notes-tab")
    WebElement NavNoteTab;

    @FindBy(id = "nav-credentials-tab")
    WebElement NavCredentialsTab;

    @FindBy(id = "new-note-button")
    WebElement NewNoteButton;

    @FindBy(id = "noteModal")
    WebElement NewNoteModal;

    @FindBy(id = "edit-note-button")
    WebElement EditNoteButton;

    @FindBy(id = "noteEditModal")
    WebElement EditNoteModal;

    @FindBy(id = "delete-note-button")
    WebElement DeleteNoteButton;

    @FindBy(className = "display-note-title")
    List<WebElement> DisplayNoteTitle;

    @FindBy(className = "display-note-description")
    List<WebElement> DisplayNoteDescription;

    @FindBy(id = "note-title")
    WebElement NoteTitle;

    @FindBy(id = "note-description")
    WebElement NoteDescription;

    @FindBy(id = "note-edit-title")
    WebElement EditNoteTitle;

    @FindBy(id = "note-edit-description")
    WebElement EditNoteDescription;

    @FindBy(id = "noteSubmit")
    WebElement NoteSubmit;

    @FindBy(id = "noteEditSubmit")
    WebElement EditNoteSubmit;

    @FindBy(id = "new-credetial-button")
    WebElement NewCredentialButton;

    @FindBy(id = "credentialModal")
    WebElement CredentialModal;

    @FindBy(id = "edit-credential-button")
    WebElement EditCredentialButton;

    @FindBy(id = "credentialEditModal")
    WebElement EditCredentialModal;

    @FindBy(id = "delete-credential-button")
    WebElement DeleteCredentialButton;

    @FindBy(className = "display-credential-url")
    List<WebElement> DisplayCredentialUrl;

    @FindBy(className = "display-credential-username")
    List<WebElement> DisplayCredentialUsername;

    @FindBy(className = "display-credential-password")
    List<WebElement> DisplayCredentialPassword;

    @FindBy(id = "credential-edit-url")
    WebElement CredentialEditUrl;

    @FindBy(id = "credential-edit-url")
    WebElement CredentialEditUsername;

    @FindBy(id = "credential-edit-password")
    WebElement CredentialEditPassword;

    @FindBy(id = "credentialEditSubmit")
    WebElement CredentialEditSubmit;

    @FindBy(id = "credential-url")
    WebElement CredentialUrl;

    @FindBy(id = "credential-username")
    WebElement CredentialUsername;

    @FindBy(id = "credential-password")
    WebElement CredentialPassword;

    @FindBy(id = "credentialSubmit")
    WebElement CredentialSubmit;


    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }
}
