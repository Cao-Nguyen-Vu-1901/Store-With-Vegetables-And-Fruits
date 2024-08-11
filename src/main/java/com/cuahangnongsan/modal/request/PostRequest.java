package com.cuahangnongsan.modal.request;


import com.cuahangnongsan.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;


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
