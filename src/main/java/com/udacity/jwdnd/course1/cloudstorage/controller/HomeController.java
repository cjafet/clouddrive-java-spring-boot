package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private NoteService noteService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;
    private FileService fileService;
    private UserService userService;

    public HomeController(NoteService noteService, CredentialService credentialService, EncryptionService encryptionService, FileService fileService, UserService userService) {
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping
    public String home(@ModelAttribute("newNote") Note newNote, @ModelAttribute("newCredential") Credential credential, @ModelAttribute("encryptionService") EncryptionService encryptionService, Model model, Principal principal) {
        User user = this.userService.getUser(principal.getName());
        List<Credential> credentials = this.credentialService.getCredentialById(user.getUserid());

        model.addAttribute("notes", noteService.getNotesById(user.getUserid()));
        model.addAttribute("credentials", credentials);
        model.addAttribute("files", this.fileService.getFiles(user.getUserid()));

        return "home";
    }

}
