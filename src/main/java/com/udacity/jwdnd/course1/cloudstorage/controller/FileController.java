package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;


@Controller
@RequestMapping("/file")
public class FileController {

    private UserService userService;
    private FileService fileService;

    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }


    @GetMapping("/view/{id}")
    public ResponseEntity<ByteArrayResource> getFileById(@PathVariable("id") String fileId) {

        try {

            File file = this.fileService.getFileId(Integer.parseInt(fileId));

            ByteArrayResource resource = new ByteArrayResource(file.getFileData());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                    .contentLength(file.getFileData().length)
                    .contentType(MediaType.valueOf(file.getContentType()))
                    .body(resource);
        } catch (Exception e) {
            return (ResponseEntity<ByteArrayResource>) ResponseEntity.notFound();
        }


    }

    @GetMapping("/delete/{id}")
    public String removeFile(@PathVariable("id") String fileId) {
        try {
            this.fileService.removeFile(Integer.parseInt(fileId));
        } catch (Exception e) {
            return "redirect:/home?error";
        }
        return "redirect:/home?success";
    }

    @PostMapping
    public String addFile(@RequestParam("fileUpload") MultipartFile fileUpload, Principal principal, Model model) throws IOException {


        File newFile = this.fileService.getFileName(fileUpload.getOriginalFilename());

        if (!fileUpload.isEmpty() && newFile == null) {

            try {
                Long size = fileUpload.getSize();
                User user = this.userService.getUser(principal.getName());

                File file = new File();
                file.setUserId(user.getUserid());
                file.setContentType(fileUpload.getContentType());
                file.setFileName(fileUpload.getOriginalFilename());
                file.setFileSize(size.toString());
                file.setFileData(fileUpload.getBytes());

                this.fileService.addFile(file);

                model.addAttribute("files", this.fileService.getFiles(user.getUserid()));

                return "redirect:/home?success";

            } catch (Exception e) {
                return "redirect:/home?error";
            }

        } else {
            return "redirect:/home?error";
        }
    }
}
