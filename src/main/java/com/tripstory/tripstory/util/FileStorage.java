package com.tripstory.tripstory.util;

public interface FileStorage {

    String saveFile(byte[] bytes, String fileName, String contentType);

    void deleteFile(String filePath);
}
