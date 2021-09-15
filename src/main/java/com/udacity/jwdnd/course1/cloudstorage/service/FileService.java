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
public class FileService implements StorageService {
    private FileMapper fileMapper;
    private final Path rootLocation;

    public FileService(FileMapper fileMapper, StorageProperties properties){
        this.fileMapper = fileMapper;
        this.rootLocation = Paths.get(properties.getLocation());
    }

    public Stream<File> getAllFiles(Integer userId ){
        return fileMapper.getAll(userId).stream();
//        try {
//           return Files.walk(this.rootLocation, 1).filter(path -> !path.equals(this.rootLocation)).map(this.rootLocation::relativize);
//        }
//        catch (IOException e) {
//            throw new StorageException("Failed to read stored files", e);
//        }
    }

    public File getFile(String filename){
        return fileMapper.get(filename);
    }

    public int createFile(MultipartFile file, Integer userId){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Blob blob = null;
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }

            Path destinationFile = this.rootLocation.resolve(
                            Paths.get(fileName))
                    .normalize().toAbsolutePath();

            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }


//            try (InputStream inputStream = file.getInputStream()) {
//                blob = new SerialBlob(StreamUtils.copyToByteArray(inputStream));
//            }

            SecureRandom random = new SecureRandom();
            int int_random = random.nextInt(Integer.MAX_VALUE);
            return fileMapper.insert(new File(int_random, fileName, file.getContentType(), file.getSize(),file.getBytes(),userId));
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }

    }


    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    public int updateFile(MultipartFile file, Integer userId){
        try{
            return fileMapper.update(file.getOriginalFilename(),file.getContentType(),file.getSize(),file.getInputStream());
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }

    }

    public void delete(File file){
         fileMapper.delete(file.getFileName());
    }
}
