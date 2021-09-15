package com.udacity.jwdnd.course1.cloudstorage.storage;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface StorageService {
    void init();

    int createFile(MultipartFile file,Integer userId);

    Stream<File> getAllFiles(Integer userId);

    File getFile(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();
}
