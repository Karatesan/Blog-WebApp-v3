package com.karatesan.WebAppApi.services.interfaces;


import com.karatesan.WebAppApi.dto.blogpost.ImageUploadDto;
import com.karatesan.WebAppApi.ulilityClassess.ImageLocationData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

public interface ImageService {

    public ImageLocationData saveImage(String uploadDirectory, Long blogId, ImageUploadDto image);

    public ImageLocationData saveImage(Long blogId, ImageUploadDto image) ;

    public byte[] getImage(String imageDirectory, String imageName) throws IOException;

    public String deleteImage(String imageDirectory, String imageName) throws IOException;

    public List<byte[]> getAllImages(String uploadDirectory) throws IOException;

}
