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

    @Value("${resources.post-image.path}")
    private String path;

    @Override
    public String saveFile(byte[] bytes, String fileName, String contentType) {
        String randomUUID = UUID.randomUUID().toString();
        String uploadTargetName = randomUUID + fileName;
        File file = new File(location + uploadTargetName);

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(bytes);
        } catch (Exception e) {
            throw new IllegalStateException(fileName + "이미지 파일 저장중 오류가 발생했습니다.");
        }
        return path + "/" +uploadTargetName;
    }

    @Override
    public void deleteFile(String filePath) {

    }
}
