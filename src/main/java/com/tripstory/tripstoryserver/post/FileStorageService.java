package com.tripstory.tripstoryserver.post;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileStorageService {

    private final String uploadPath;

    public FileStorageService(@Value("${custom.path.upload-images}") String uploadPath) {
        this.uploadPath = uploadPath + "/post-images/";
    }

    public String saveImage(MultipartFile image) throws IOException {
        UUID uuid = UUID.randomUUID();
        String imageName = uuid + image.getOriginalFilename();
        System.out.println("-------- image file store begin --------");
        System.out.println("대상 이미지 : "+image.getOriginalFilename());
        BufferedOutputStream bs = null;
        try {
            bs = new BufferedOutputStream(new FileOutputStream(uploadPath + imageName));
            bs.write(image.getBytes());

        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            bs.close();
            System.out.println("-------- image file store success --------");
            System.out.println("저장된 경로 : " + uploadPath + "/" +imageName);
        }
        return "/post-images/" + imageName;
    }
}
