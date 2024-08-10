package com.cuahangnongsan.service.imp;


import com.cuahangnongsan.constant.StringConstant;
import com.cuahangnongsan.entity.Role;
import com.cuahangnongsan.entity.User;
import com.cuahangnongsan.repository.RoleRepository;
import com.cuahangnongsan.repository.UserRepository;
import com.cuahangnongsan.service.IUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private RoleRepository roleRepository;


    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsernameAndStatus(username, StringConstant.USER_STATUS_ACTIVE);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }



    @Override
    public void removeSessionMessage() {

        HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest()
                .getSession();

        session.removeAttribute("msg");
    }


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findAllByNameLike(String name) {
        return userRepository.findAllByNameLike(name);
    }


    @Override
    public List<User> findAllByEmailLike(String email) {
        return userRepository.findAllByEmailLike(email);
    }

    @Override
    public List<User> findAllByPhoneNumberLike(String phoneNumber) {
        return userRepository.findAllByPhoneNumberLike(phoneNumber);
    }

    @Override
    public List<User> findAllByAddressLike(String address) {
        return userRepository.findAllByAddressLike(address);
    }

    @Override
    public List<User> findAllByStatus(boolean status) {
        return userRepository.findAllByStatus(status);
    }

    @Override
    public List<User> findAllByRoleName(String name) {
        return userRepository.findAllByRoleName(name);
    }

}
