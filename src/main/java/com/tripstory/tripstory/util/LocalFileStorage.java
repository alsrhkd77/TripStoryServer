package com.tripstory.tripstory.util;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Component
@Profile("dev")
public class LocalFileStorage implements FileStorage {

    /**
     * 파일 정보를 받아 해당 location 에 저장
     * 저장된 파일을 바로 불러 올 수 있는 파일 경로를 반환함
     * @param bytes
     * @param fileName
     * @param contentType
     * @param location    
     * @return 저장된 파일 경로
     */
    @Override
    public String saveFile(byte[] bytes, String fileName, String contentType, String location) {

        String randomUUID = UUID.randomUUID().toString();
        String uploadFileName = randomUUID + fileName;
        File file = new File(location + uploadFileName);

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(bytes);
        } catch (Exception e) {
            throw new IllegalStateException(fileName + "이미지 파일 저장중 오류가 발생했습니다.");
        }
        return uploadFileName;
    }

    /**
     * 파일이름과 파일위 위치한 경로를 받아 삭제
     * @param fileName
     * @param location
     * @return 삭제할 대상이 있으면 삭제하고 1반환, 없으면 0반환
     */
    @Override
    public int deleteFile(String fileName, String location) {
        String deleteFileName = location + fileName;
        File deleteTargetFile = new File(deleteFileName);

        if (deleteTargetFile.exists()) {
            deleteTargetFile.delete();

        } else {
            return 0;
        }
        return 1;
    }
}
