package com.tripstory.tripstory.util;

public interface FileStorageV2 {

    String saveFile(byte[] bytes, String fileName, String contentType, String location);

    int deleteFile(String fileName, String location);
}
