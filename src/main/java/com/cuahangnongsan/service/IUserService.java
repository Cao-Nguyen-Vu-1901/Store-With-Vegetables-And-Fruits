package com.cuahangnongsan.service;

import com.cuahangnongsan.dto.request.AdminUserCreationRequest;
import com.cuahangnongsan.dto.request.UserRequest;
import com.cuahangnongsan.dto.request.UserUpdateRequest;
import com.cuahangnongsan.dto.response.UserResponse;
import com.cuahangnongsan.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface IUserService {


    UserResponse findById(String id);

    UserResponse findByUsername(String username);

    UserResponse userSave(User user);
    void adminSave(ArrayList<String> roles,
                          AdminUserCreationRequest request,
                          ArrayList<String> permissions,
                          RedirectAttributes redirectAttributes) throws IOException;

    UserResponse updateStatusUser(String id);

    UserResponse registerUser(UserRequest request, String rePassword, HttpSession session);

    UserResponse updatePassword(String oldPassword, String newPassword,
                                String reNewPassword, User user, ModelMap modelMap);

    UserResponse updateInformation(UserUpdateRequest request, User user, ModelMap modelMap) throws IOException;

    void showAndSearchUser(ModelMap modelMap, String type, String value, String role);

    void removeSessionMessage();

    List<UserResponse> findAll();

    List<UserResponse> findAllByNameLike(String name);

    List<UserResponse> findAllByEmailLike(String email);

    List<UserResponse> findAllByPhoneNumberLike(String phoneNumber);

    List<UserResponse> findAllByAddressLike(String address);

    List<UserResponse> findAllByStatus(boolean status);

    List<UserResponse> findAllByRoleName(String name);


}
