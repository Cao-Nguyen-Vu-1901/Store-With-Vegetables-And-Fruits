package com.cuahangnongsan.dto.response;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostResponse {

    String id;

    String title;

    String content;

    LocalDateTime createdTime;

    LocalDateTime modifiedTime;

    String image;

    String shortDescription;


    UserResponse user;



}
