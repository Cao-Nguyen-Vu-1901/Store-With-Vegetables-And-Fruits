package com.cuahangnongsan.firebase;//package com.cuahangnongsan.firebase;
//
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
////import com.cuahangnongsan.configuration.FirebaseConfig;
//import com.cuahangnongsan.config.FirebaseConfig;
//
//import java.io.IOException;
//
//
//public class FirebaseService {
//
//    private final DatabaseReference databaseReference;
//
//    public FirebaseService() throws IOException {
//        FirebaseConfig firebaseConfig = new FirebaseConfig();
//        firebaseConfig.initializeFirebase();
//        this.databaseReference = FirebaseDatabase.getInstance().getReference();
//
//    }
//
//    public void writeNewUser(String userId, User user) {
//        databaseReference.child("users").child(userId).setValueAsync(user);
//    }
//}
