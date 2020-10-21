package com.example.tripstory;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class WebController {
    //private final JdbcTemplate jdbcTemplate;

    @Autowired
    TestRepository testRepository;

    @PostMapping("/insert")
    String pushInsert(@RequestParam MultipartFile multipartFile){
        String result = "faild TT";

        System.out.println(multipartFile.getOriginalFilename());
        System.out.println(multipartFile.getSize());
        try {
            //Storage storage = StorageOptions.newBuilder().setProjectId("tripstory-283605").setCredentials(GoogleCredentials.fromStream(new FileInputStream("D:/Capstone/Server/Key/tripstory-283605-5d797a3e119d.json"))).build().getService();
            Storage storage = StorageOptions.getDefaultInstance().getService();
            BlobInfo blobInfo = storage.create(
                    BlobInfo.newBuilder("tripstory_image", multipartFile.getOriginalFilename()).build(), //get original file name
                    multipartFile.getBytes(), // the file
                    Storage.BlobTargetOption.predefinedAcl(Storage.PredefinedAcl.PUBLIC_READ) // Set file permission
            );
            return blobInfo.getSelfLink(); // Return file url
        }catch(IllegalStateException | IOException e){
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getTuples")
    public String getTuples() {
        List<TestEntitiy> testEntitiys = testRepository.findAll();
        String result ="";
        result = testEntitiys.get(0).getId().toString() + " " + testEntitiys.get(0).getPw().toString();
        return result;
        /*
        return this.jdbcTemplate.queryForList("SELECT * FROM test").stream()
                .map((m) -> m.values().toString())
                .collect(Collectors.toList()).toString();
         */
    }
}
