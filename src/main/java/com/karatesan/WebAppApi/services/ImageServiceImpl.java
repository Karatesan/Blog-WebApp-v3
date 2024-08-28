package com.karatesan.WebAppApi.services;

import com.karatesan.WebAppApi.services.interfaces.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ImageServiceImpl implements ImageService {

    public String saveImage(String uploadDirectory, MultipartFile image) throws IOException {
        String uniqueFileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();

        Path uploadPath = Path.of(uploadDirectory);
        Path filePath = uploadPath.resolve(uniqueFileName);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }

    public byte[] getImage(String imageDirectory, String imageName) throws IOException {

        Path imagePath = Path.of(imageDirectory, imageName);

        if (Files.exists(imagePath)) {
            return Files.readAllBytes(imagePath);
        } else {
            return null;
        }
    }

    public List<byte[]> getAllImages(String uploadDirectory) throws IOException {

        Path directoryPath = Path.of(uploadDirectory);

        try (Stream<Path> paths = Files.list(directoryPath)) {
            return paths
                    .filter(p->Files.isRegularFile(p))
                    .map(p -> {
                        try {
                            return Files.readAllBytes(p);
                        } catch (IOException e) {
                            throw new RuntimeException("Error reading file: " + p.toString(), e);
                        }
                    })
                    .toList();
        }
    }

    public String deleteImage(String imageDirectory, String imageName) throws IOException {

        Path imagePath = Path.of(imageDirectory,imageName);

        if(Files.exists(imagePath)){
            Files.delete(imagePath);
            return "Success";
        } else{
            return "Failed";
        }
    }


}
