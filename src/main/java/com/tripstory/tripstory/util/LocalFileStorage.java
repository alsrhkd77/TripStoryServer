package com.tripstory.tripstory.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Component
@Profile("dev")
public class LocalFileStorage implements FileStorage{

    @Value("${resources.post-image.location}")
    private String location;

    @Override
    public String saveFile(byte[] bytes, String fileName, String contentType) {
        String randomUUID = UUID.randomUUID().toString();
        String uploadTargetName = randomUUID + fileName;
        String path = location + uploadTargetName;
        File file = new File(path);

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(bytes);
        } catch (Exception e) {
            throw new IllegalStateException(fileName + "이미지 파일 저장중 오류가 발생했습니다.");
        }
        return path;
    }

    @Override
    public void deleteFile(String filePath) {

    }
}
