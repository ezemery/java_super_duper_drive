package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.storage.StorageException;
import com.udacity.jwdnd.course1.cloudstorage.storage.StorageFileNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.storage.StorageProperties;
import com.udacity.jwdnd.course1.cloudstorage.storage.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.SecureRandom;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Stream;

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

    public int createFile(MultipartFile file, Integer userId){
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            return fileMapper.insert(new File(null, file.getOriginalFilename(), file.getContentType(), file.getSize(),userId,file.getBytes()));
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }

    }

    public boolean isFileAvailable(String filename) {
        return fileMapper.get(filename) == null;
    }

    public void delete(Integer fileId){
         fileMapper.delete(fileId);
    }
}
