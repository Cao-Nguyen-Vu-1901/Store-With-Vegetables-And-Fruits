package com.cuahangnongsan.dto.response;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

public class CommentResponse {


    private Long id;

    private String content;

    private String username;

    private String imageUser;

    private LocalDateTime createdDate;
    @JsonBackReference(value = "comment_json")

    private CommentResponse parent;

    @JsonManagedReference(value = "comment_json")
    private List<CommentResponse> replies = new ArrayList<>();

    @JsonBackReference(value = "product_json")
    private ProductResponse product;

}

