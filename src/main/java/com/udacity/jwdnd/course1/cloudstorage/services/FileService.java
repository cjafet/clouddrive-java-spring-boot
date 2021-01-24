package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public List<File> getFiles(Integer userId) {
        return this.fileMapper.getFiles(userId);
    }

    public File getFileId(Integer fileId) {
        return this.fileMapper.getFileId(fileId);
    }

    public int addFile(File file) {
        return this.fileMapper.insert(file);
    }

    public void removeFile(Integer fileId) {
        this.fileMapper.deleteFile(fileId);
    }

}
