package com.cuahangnongsan.dto.request;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostRequest implements Serializable {

    String title;

    String content;

    String shortDescription;

}
