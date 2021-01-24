package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/credential")
public class CredentialController {

private CredentialService credentialService;
private NoteService noteService;
private UserService userService;

    public CredentialController(CredentialService credentialService, NoteService noteService, UserService userService) {
        this.credentialService = credentialService;
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping
    public String addCredential(@ModelAttribute("newCredential") Credential credential, @ModelAttribute("newNote") Note note, @ModelAttribute("encryptionService") EncryptionService encryptionService, Model model, Principal principal) {

        User user = this.userService.getUser(principal.getName());
        credential.setUserId(user.getUserid());

        if(credential.getCredentialId() == null) {

            try {
                this.credentialService.addCredential(credential);
            } catch (Exception e) {
                return "redirect:/home?error";
            }

        } else {
            try {
                this.credentialService.updateCredential(credential);
            } catch (Exception e) {
                return "redirect:/home?error";
            }

        }

        return "redirect:/home?success";

    }

    @GetMapping("/delete/{id}")
    public String deleteCredential(@PathVariable("id") String credentialId, @ModelAttribute("newNote") Note newNote, @ModelAttribute("newCredential") Credential credential, @ModelAttribute("encryptionService") EncryptionService encryptionService, Model model) {

        try {
            this.credentialService.deleteCredential(Integer.parseInt(credentialId));
        } catch (Exception e) {
            return "redirect:/home?error";
        }

        return "redirect:/home?success";
    }
}
