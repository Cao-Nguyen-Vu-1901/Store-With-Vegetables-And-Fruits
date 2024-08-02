//package com.cuahangnongsan.config.firebase;
//
//import com.cuahangnongsan.service.imp.CartServiceImpl;
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//
//@Configuration
//@ComponentScan("com.cuahangnongsan.controller")
//public class FirebaseConfig {
//
//    @Bean
//    public FirebaseApp initialize() {
//        if (FirebaseApp.getApps().isEmpty()) {
//            try {
//                InputStream serviceAccount =
//                        CartServiceImpl.class.getClassLoader().getResourceAsStream("serviceAccountKey.json");
//                if (serviceAccount == null) {
//                    throw new FileNotFoundException("Resource not found: serviceAccountKey.json");
//                }
//                FirebaseOptions options = new FirebaseOptions.Builder()
//                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                        .setDatabaseUrl("https://website-ban-nong-san-default-rtdb.asia-southeast1.firebasedatabase.app/")
//                        .build();
//
//
//                return FirebaseApp.initializeApp(options);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//}
