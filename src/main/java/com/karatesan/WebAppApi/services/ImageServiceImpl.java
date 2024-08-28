package com.karatesan.WebAppApi.services;

import com.karatesan.WebAppApi.dto.blogpost.ImageUploadDto;
import com.karatesan.WebAppApi.exception.ImageSaveException;
import com.karatesan.WebAppApi.model.Image;
import com.karatesan.WebAppApi.services.interfaces.ImageService;
import com.karatesan.WebAppApi.ulilityClassess.ImageLocationData;
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

    //creates local image file name by adding to the begin randomUUID and extending it wihth order number
    public ImageLocationData saveImage(String uploadDirectory, Long blogId, ImageUploadDto image)  {
        String uniqueFileName = UUID.randomUUID().toString() + "_" + image.image().getOriginalFilename()+"_"+image.imageOrder();

        Path uploadPath = Path.of(uploadDirectory + "/" + blogId.toString());
        Path filePath = uploadPath.resolve(uniqueFileName);
        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Files.copy(image.image().getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            throw new ImageSaveException(e.getMessage(),e.getCause());
        }//TODO nie ma tego wyjatku w globalnym handlerze
        return new ImageLocationData(uploadPath.toString(),uniqueFileName);
    }

    public ImageLocationData saveImage(Long blogId, ImageUploadDto image){
        String DIRECTORY_PATH = "/src/main/resources/static/blogposts_images";
        return saveImage(DIRECTORY_PATH,blogId, image);
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
