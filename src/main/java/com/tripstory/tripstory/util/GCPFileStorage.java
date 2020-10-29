package com.tripstory.tripstory.util;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Profile("prod")
public class GCPFileStorage implements FileStorage{
    @Override
    public String saveFile(byte[] bytes, String fileName, String contentType) {

        try {
            Storage storage = StorageOptions.getDefaultInstance().getService();
            BlobInfo blobInfo = storage.create(
                    BlobInfo.newBuilder("tripstory_image", "post_image/" + fileName).setContentType(contentType).build(),
                    Storage.BlobTargetOption.predefinedAcl(Storage.PredefinedAcl.PUBLIC_READ) // Set file permission
            );
            System.out.println("----------------------------");
            System.out.println(blobInfo.getMediaLink());
            System.out.println(blobInfo.getSelfLink());
            System.out.println(blobInfo.getBucket());
            System.out.println(blobInfo.getGeneratedId());
            System.out.println(blobInfo.getName());
            System.out.println("----------------------------");
            return blobInfo.getSelfLink(); // Return file url
        }catch(IllegalStateException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteFile(String filePath) {

    }
}
