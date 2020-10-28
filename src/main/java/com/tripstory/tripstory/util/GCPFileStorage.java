package com.tripstory.tripstory.util;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class GCPFileStorage implements FileStorage{
    @Override
    public String saveFile(byte[] bytes, String fileName, String contentType) {
        return null;
    }

    @Override
    public void deleteFile(String filePath) {

    }
}
