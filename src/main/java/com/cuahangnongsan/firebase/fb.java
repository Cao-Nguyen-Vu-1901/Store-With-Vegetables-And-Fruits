//package com.cuahangnongsan.firebase;
//
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
//import com.google.firebase.database.*;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.concurrent.ExecutionException;
//
//public class fb {
//
//
//
//
//    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
////        FileInputStream serviceAccount =
////                new FileInputStream("src/main/resources/serviceAccountKey.json");
////        if(serviceAccount != null){
////            System.out.println("NOT NULL");
////        }
//
//        InputStream serviceAccount = fb.class.getClassLoader().getResourceAsStream("serviceAccountKey.json");
//        if (serviceAccount == null) {
//            throw new FileNotFoundException("Resource not found: serviceAccountKey.json");
//        }
//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                .setDatabaseUrl("https://website-ban-nong-san-default-rtdb.asia-southeast1.firebasedatabase.app/")
//                .build();
//
//        FirebaseApp.initializeApp(options);
//
//
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
//
//
////        DatabaseReference userRef = databaseReference.child("users").child("2");
//        User u = new User("f12g", "2");
//        databaseReference.child("d").child(u.getUsername()).setValueAsync(u);
//////         Attach a ValueEventListener to/ listen for changes in the data at this location
////        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
////            public void onDataChange(DataSnapshot dataSnapshot) {
////                if (dataSnapshot.exists()) {
////                    User user = dataSnapshot.getValue(User.class);
////                    if (user != null) {
////                        System.out.println("User: " + user.getUsername() + ", Email: " + user.getEmail());
////                    }
////                } else {
////                    System.out.println("User not found");
////                }
////            }
////
////            @Override
////            public void onCancelled(DatabaseError databaseError) {
////                System.out.println("Error fetching user: " + databaseError.getMessage());
////            }
////        });
//
////        FirebaseService service = new FirebaseService();
////        service.writeNewUser("ggg", u);
//        try {
//            Thread.sleep(5000); // Chờ 5 giây để đảm bảo rằng dữ liệu được đọc
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    System.out.println("av");
//    }
//}
