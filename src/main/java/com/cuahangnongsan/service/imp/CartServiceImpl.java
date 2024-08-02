package com.cuahangnongsan.service.imp;
import com.cuahangnongsan.config.firebase.FirebaseInitializer;
import com.cuahangnongsan.entity.User;
import com.cuahangnongsan.modal.response.Cart;
import com.cuahangnongsan.service.ICartService;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.*;
import com.google.firebase.database.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CartServiceImpl implements ICartService {
    User user = User.builder().id("abc").build();
    private List<Cart> carts = null;
    @Override
    public void save(Cart cart) throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseInitializer.getFirebaseApp();
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        if(carts != null)
            carts.clear();
        carts = getById("abc");
        if(carts!=null){
            for (Cart a : carts) {
                if (a.getProductId().equals(cart.getProductId())) {
                    a.setQuantity(a.getQuantity() + cart.getQuantity());
                    cart = a;
                }
            }
        }

        if(cart.getId() == null){
            cart.setId("1");
            databaseReference.child("carts").child(user.getId()).child(UUID.randomUUID().toString()).setValueAsync(cart);
        }else
            databaseReference.child("carts").child(user.getId()).child(cart.getId()).setValueAsync(cart);
        databaseReference.child("carts").child(user.getId()).
                child(cart.getId()).child(cart.getProductId()).removeValueAsync();
    }



    @Override
    public List<Cart> getById(String id) throws IOException {

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseInitializer.getFirebaseApp();
        }
        carts = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference cartRef = databaseReference.child("carts").child(user.getId());
        cartRef.limitToFirst(50).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                carts.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String id = snapshot.getKey();
                    Cart item = snapshot.getValue(Cart.class);
                    item.setId(id);
                    carts.add(item);

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
//                System.out.println("Error fetching user: " + databaseError.getMessage());
            }
        });
        try {

            Thread.sleep(600); // Chờ 5 giây để đảm bảo rằng dữ liệu được đọc

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return carts;
    }


}
