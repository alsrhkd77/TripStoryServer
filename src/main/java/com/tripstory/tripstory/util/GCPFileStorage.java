package com.tripstory.tripstory.util;

//import com.google.cloud.storage.BlobInfo;
//import com.google.cloud.storage.Storage;
//import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Profile("prod")
public class GCPFileStorage implements FileStorage{

    @Value("${resources.post-image.path}")
    private String storagePath;

    @Override
    public String saveFile(byte[] bytes, String fileName, String contentType) {
//        String uuid = UUID.randomUUID().toString();
//        String name = uuid + fileName;
//        try {
//            Storage storage = StorageOptions.getDefaultInstance().getService();
//            BlobInfo blobInfo = storage.create(
//                    BlobInfo.newBuilder("tripstory_image", "post_image/" + name).setContentType(contentType).build(),
//                    bytes,
//                    Storage.BlobTargetOption.predefinedAcl(Storage.PredefinedAcl.PUBLIC_READ) // Set file permission
//            );
//            return storagePath + blobInfo.getName(); // Return file url
//        }catch(IllegalStateException e){
//            throw new RuntimeException(e);
//        }
        return null;
    }

    @Override
    public void deleteFile(String filePath) {
//        String objectName = filePath.split("/")[5];
//        Storage storage = StorageOptions.getDefaultInstance().getService();
//        storage.delete("tripstory_image", "post_image/" + objectName);
    }
}
