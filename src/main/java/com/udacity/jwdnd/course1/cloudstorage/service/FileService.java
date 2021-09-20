package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;


@Service
public class FileService {
    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper){
        this.fileMapper = fileMapper;
    }

    public List<File> getAllUserFiles(Integer userId ){
        return fileMapper.getFilesByUserId(userId);
    }

    public File getFile(String filename){
        return fileMapper.get(filename);
    }

    public int createFile(MultipartFile file, Integer userId) throws Exception {
        try {
            if (file.isEmpty()) {
                throw new Exception("Failed to store empty file.");
            }
            return fileMapper.insert(new File(null, file.getOriginalFilename(), file.getContentType(), file.getSize(),userId,file.getBytes()));
        }
        catch (Exception e) {
            throw new Exception("Failed to store file.", e);
        }

    }

    public boolean isFileAvailable(String filename) {
        return fileMapper.get(filename) == null;
    }

    public void delete(Integer fileId){
         fileMapper.delete(fileId);
    }
}
