package com.cuahangnongsan.config.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class FirebaseInitializer {

    private static FirebaseApp firebaseApp;

    private FirebaseInitializer() {
        // Private constructor to prevent instantiation
    }

    public static synchronized void getFirebaseApp() throws IOException {
        if (firebaseApp == null) {
            InputStream serviceAccount =
                    CartServiceImpl.class.getClassLoader().getResourceAsStream("serviceAccountKey.json");
            if (serviceAccount == null) {
                throw new FileNotFoundException("Resource not found: serviceAccountKey.json");
            }
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setStorageBucket("website-ban-nong-san.appspot.com")
                    .setDatabaseUrl("https://website-ban-nong-san-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .build();

            firebaseApp = FirebaseApp.initializeApp(options);
        }
    }


}
