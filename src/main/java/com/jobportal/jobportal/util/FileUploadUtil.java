package com.jobportal.jobportal.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {

    public static void uploadFile(String fileName, String dirName, MultipartFile multipartFile) throws IOException {

        if (multipartFile == null) return;

        Path path = Paths.get(dirName);

        if (!Files.exists(path)) {
            System.out.println("-- create path");
            Files.createDirectories(path);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path finalPath = path.resolve(fileName);
            Files.copy(inputStream, finalPath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println(fileName + " saved to " + finalPath);
        } catch (IOException ioe) {
            throw new IOException("File cannot be saved");
        }

    }
}
