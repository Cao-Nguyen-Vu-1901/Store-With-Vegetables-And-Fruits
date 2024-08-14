package com.cuahangnongsan.dto.request;

import com.cuahangnongsan.entity.Comment;
import com.cuahangnongsan.entity.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentRequest {

    private Long id;

    private String content;

    private String username;

    private String imageUser;

    private LocalDateTime createdDate;

    private Comment parent;

    private List<Comment> replies = new ArrayList<>();

    private Product product;

}

