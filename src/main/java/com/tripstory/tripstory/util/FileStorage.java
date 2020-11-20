package com.tripstory.tripstory.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

public interface FileStorage {

    String saveFile(byte[] bytes, String fileName, String contentType, String location);

    int deleteFile(String fileName, String location);
}
