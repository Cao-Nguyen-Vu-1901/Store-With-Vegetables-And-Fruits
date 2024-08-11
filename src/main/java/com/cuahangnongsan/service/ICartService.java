package com.cuahangnongsan.service;

import com.cuahangnongsan.dto.response.Cart;

import java.io.IOException;
import java.util.List;

public interface ICartService {
    void save(Cart cart) throws IOException;
    List<Cart> getById(String id) throws IOException;
}
