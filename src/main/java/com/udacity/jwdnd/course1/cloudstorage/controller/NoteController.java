package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/note")
public class NoteController {

    private NoteService noteService;
    private CredentialService credentialService;
    private UserService userService;

    public NoteController(NoteService noteService, CredentialService credentialService, UserService userService) {
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @GetMapping("/delete/{id}")
    public String deleteNote(@PathVariable("id") String noteId, @ModelAttribute("newNote") Note newNote, @ModelAttribute("newCredential") Credential credential, Model model) {

        try {
            noteService.deleteNote(Integer.parseInt(noteId));
        } catch (Exception e) {
            return "redirect:/home?error";
        }

        return "redirect:/home?success";
    }

    @PostMapping
    public String addNote(@ModelAttribute("newNote") Note newNote, @ModelAttribute("newCredential") Credential credential, Model model, Principal principal) {

        User user = this.userService.getUser(principal.getName());
        newNote.setUserId(user.getUserid());

        if(newNote.getNoteId() == null) {
            try {
                noteService.createNote(newNote);
            } catch (Exception e) {
                return "redirect:/home?error";
            }
        } else {
            try {
                noteService.updateNote(newNote);
            } catch (Exception e) {
                return "redirect:/home?error";
            }
        }

        return "redirect:/home?success";
    }

}
