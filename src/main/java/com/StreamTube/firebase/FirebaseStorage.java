package com.StreamTube.firebase;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;

@Configuration
public class FirebaseStorage {

    @Value("classpath:streamtube-953c4-firebase-adminsdk-zk2o3-2067466deb.json")
    private Resource serviceAccount;

    private final static String bucketName = "streamtube-953c4.appspot.com";

    @Bean
    public Bucket storageClient() throws IOException {
        System.out.println("Initializing Firebase storage...");

        if (FirebaseApp.getApps().isEmpty()) { // Check if the FirebaseApp has already been initialized
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
                    .setStorageBucket(bucketName)
                    .build();
            FirebaseApp.initializeApp(options);
            System.out.println("Firebase initialized");
        } else {
            System.out.println("Firebase already initialized.");
        }

        FirebaseApp firebaseApp = FirebaseApp.getInstance(); // Get the default FirebaseApp instance
        Bucket bucket = StorageClient.getInstance(firebaseApp).bucket();

        System.out.println("Storage client initialized");

        return bucket;
    }
}
