package com.cuahangnongsan.modal.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionRequest implements Serializable {

    String name;
    String description;
}