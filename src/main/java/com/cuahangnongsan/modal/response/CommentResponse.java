package com.cuahangnongsan.modal.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class CommentResponse {


    private String content;

    private CommentResponse parent;


}

