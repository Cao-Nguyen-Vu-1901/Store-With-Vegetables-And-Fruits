package com.cuahangnongsan.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostResponse {

    String id;

    String title;

    String content;

    String shortDescription;

}
