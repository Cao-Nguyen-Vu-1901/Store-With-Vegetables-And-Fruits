package com.cuahangnongsan.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminUserCreationRequest {
    String name;
    String username;
    String email;
    String phoneNumber;
    MultipartFile image;
    String optionsChoice;
    String password;
    String rePassword;
    String roleName;
    String cityProvince;
    String district;
    String ward;
    String specificAddress;
}
