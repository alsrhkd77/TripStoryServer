package com.tripstory.tripstory.util;

//import com.google.cloud.storage.BlobInfo;
//import com.google.cloud.storage.Storage;
//import com.google.cloud.storage.StorageOptions;

import java.util.UUID;

public class GCPFileStorageV2 implements FileStorageV2 {

    private final static String storagePath = "https://storage.googleapis.com/tripstory_image/";

    /**
     * 파일 정보를 받아 GCP Storage 에 저장함
     * 로컬 환경과는 다르게 별도의 리소스 핸들러를 추가하지 않기 때문에
     * 파일을 HTTP 로 바로 요청할 수 있도록 파일 저장이후 공개 URL 반환
     * @param bytes
     * @param fileName
     * @param contentType
     * @param location
     * @return 파일에 접근 가능한 공개 URL
     */
    @Override
    public String saveFile(byte[] bytes, String fileName, String contentType, String location) {
//        String bucketName = location.split("/")[0];
//        String folderName = location.split("/")[1];
//        String randomUUID = UUID.randomUUID().toString();
//        String uploadFileName = randomUUID + fileName;
//        try {
//            Storage storage = StorageOptions.getDefaultInstance().getService();
//            BlobInfo blobInfo = storage.create(
//                    BlobInfo.newBuilder(bucketName, folderName + "/" + uploadFileName)
//                            .setContentType(contentType).build(),
//                    bytes,
//                    Storage.BlobTargetOption.predefinedAcl(Storage.PredefinedAcl.PUBLIC_READ) // Set file permission
//            );
//            return storagePath + folderName + "/" + blobInfo.getName(); // Return file url
//        } catch (IllegalStateException e) {
//            throw new RuntimeException(e);
//        }
        return null;
    }

    /**
     * 파일 이름과 위치를 받아 GCP Storage 에서 삭제함
     * location 은 '/' 으로 구분되어 앞은 버킷이름, 뒤는 버킷내의 폴더 이름이 됨
     *
     * @param fileName
     * @param location
     * @return 정상적으로 삭제시 1반환, 오류 발생(파일이 없는 등) 0반환
     */
    @Override
    public int deleteFile(String fileName, String location) {
//        String bucketName = location.split("/")[0];
//        String folderName = location.split("/")[1];
//        try {
//            Storage storage = StorageOptions.getDefaultInstance().getService();
//            storage.delete(bucketName, folderName + "/" + fileName);
//            return 1;
//        } catch (Exception e) {
//            return 0;
//        }
        return 0;
    }
}
