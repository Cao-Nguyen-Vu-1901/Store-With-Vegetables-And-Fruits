package com.cuahangnongsan.service.imp;


import com.cuahangnongsan.constant.StringConstant;
import com.cuahangnongsan.dto.request.AdminUserCreationRequest;
import com.cuahangnongsan.dto.request.UserRequest;
import com.cuahangnongsan.dto.request.UserUpdateRequest;
import com.cuahangnongsan.entity.Permission;
import com.cuahangnongsan.entity.Role;
import com.cuahangnongsan.entity.User;
import com.cuahangnongsan.exception.AppException;
import com.cuahangnongsan.mapper.UserMapper;
import com.cuahangnongsan.repository.PermissionRepository;
import com.cuahangnongsan.repository.RoleRepository;
import com.cuahangnongsan.repository.UserRepository;
import com.cuahangnongsan.service.IUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.cuahangnongsan.util.ProcessImage.upload;
import com.cuahangnongsan.dto.response.*;
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponse findById(String id) {
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow());
    }

    @Override
    public UserResponse findByUsername(String username) {
        return userMapper
                .toUserResponse(userRepository.
                        findByUsernameAndStatus(username, StringConstant.USER_STATUS_ACTIVE));
    }

    @Override
    public UserResponse userSave(User user) {
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public void adminSave(ArrayList<String> roles,
                                  AdminUserCreationRequest request,
                                  ArrayList<String> permissions,
                                  RedirectAttributes redirectAttributes) throws IOException {

                String imageName = "";
        Set<Role> listRole = new HashSet<>();
        User user = userMapper.toUser(request);

        if(request.getPassword() != null && request.getPassword().equals(request.getRePassword())){
            if(findByUsername(request.getUsername()) == null){
                if(Objects.equals(request.getImage().getOriginalFilename(),"")){
                    redirectAttributes.addFlashAttribute("userNew", userMapper.toUserResponse(user));
                    redirectAttributes.addFlashAttribute("errorImage", "Vui lòng chọn ảnh!");
                    throw new AppException("Image is not found!");
                }else {
                    imageName = upload(request.getImage(),"users/");
                    String address = request.getSpecificAddress() + ", "
                            + request.getWard() + ", " + request.getDistrict()
                            + ", " + request.getCityProvince();
                    if(request.getOptionsChoice().equals("yes")){
                        roles.forEach( a-> {
                            Role role = roleRepository.findById(a).orElseThrow();
                            listRole.add(role);
                        });
                    }else if(request.getOptionsChoice().equals("no")) {
                        Set<Permission> listPermissions = new HashSet<>();
                        permissions.forEach(a-> {
                            Permission permission = permissionRepository.findById(a).orElseThrow();
                            listPermissions.add(permission);
                        });
                        if(roleRepository.findByName("ROLE_ADMIN_" + request.getRoleName()) == null){
                            Role role = Role.builder().name( "ROLE_ADMIN_" + request.getRoleName()).permissions(listPermissions).build();
                            Role roleNew = roleRepository.save(role);
                            listRole.add(roleNew);
                        }else {
                            redirectAttributes.addFlashAttribute("userNew", userMapper.toUserResponse(user));
                            redirectAttributes.addFlashAttribute("errorRole", "Tên vai trò đã tồn tại!");
                            throw new AppException("Role is not correct!");
                        }
                    }

                    user.setImage(imageName);
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                    user.setFailedLoginAttempts(0);
                    user.setStatus(StringConstant.USER_STATUS_ACTIVE);
                    user.setRoles(listRole);
                    user.setAddress(address);
                    user.setCreateDate(LocalDate.now());
                    userMapper.toUserResponse(userRepository.save(user));
                }
            }else{
                redirectAttributes.addFlashAttribute("userNew", userMapper.toUserResponse(user));
                redirectAttributes.addFlashAttribute("errorUserName", "Tên đăng nhập đã tồn tại!");
                throw new AppException("Username is existed!");
            }
        }else {
            redirectAttributes.addFlashAttribute("userNew", userMapper.toUserResponse(user));
            redirectAttributes.addFlashAttribute("errorPassword", "Mật khẩu và mật khẩu nhập lại không trùng khớp!");
            throw new AppException("Password is not correct!");
        }
    }

    @Override
    public UserResponse updateStatusUser(String id) {
        User user = userRepository.findById(id).orElseThrow();
        user.setStatus(StringConstant.USER_STATUS_DISABLE);
        return userSave(user);
    }

    @Override
    public Page<UserResponse> showAndSearchUser(int pageNo, int pageSize, String type, String value, String role){
        List<UserResponse> users = new ArrayList<>();
        Page<UserResponse> userPaging = null;

        Pageable pageable = PageRequest.of(pageNo -1, pageSize);
        Set<Role> roles = new HashSet<>();
        if(role != null && role.equals("admin")){
            roles.add(roleRepository.findByName(StringConstant.ROLE_ADMIN));
        }else {
            roles.add(roleRepository.findByName(StringConstant.ROLE_USER));
        }
        try{
            userPaging = switch (type){
                case "name" -> userRepository.findAllByNameLike("%" + value + "%", pageable)
                        .map(userMapper::toUserResponse);
                case "email" -> userRepository.findAllByEmailLike("%" + value + "%", pageable)
                        .map(userMapper::toUserResponse);
                case "address" -> userRepository.findAllByAddressLike("%" + value + "%", pageable)
                        .map(userMapper::toUserResponse);
                case "status" -> userRepository.findAllByStatus( Boolean.parseBoolean(value), pageable)
                        .map(userMapper::toUserResponse);
                default -> userRepository.findAllByRoles(roles, pageable)
                        .map(userMapper::toUserResponse);

            };
        }catch (Exception e){
            return userRepository.findAllByRoles(roles, pageable)
                    .map(userMapper::toUserResponse);
        }
       return userPaging;
    }

    @Override
    public UserResponse registerUser(UserRequest request,String rePassword, HttpSession session) {

        User user = userMapper.toUser(request);
        user.setCreateDate(LocalDate.now());
        if(!request.getPassword().equals(rePassword)){
            session.setAttribute("msg", "Mật khẩu và mật khẩu nhập lại không đúng");
            throw new AppException("Password is not correct!");
        }else {
            User isExistedUser = userRepository.findByUsername(request.getUsername());
            if(isExistedUser != null){
                session.setAttribute("msg", "Tên đăng nhập đã tồn tại");
                throw new AppException("Username is existed!");
            }else {
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                user.setImage("https://firebasestorage.googleapis.com/v0/b/website-ban-nong-san.appspot.com/o/users%2Fuser-avatar.jpg?alt=media&token=722a0001-b720-4398-9e32-56090424f53c");
                user.setStatus(StringConstant.USER_STATUS_ACTIVE);
                session.setAttribute("msg", "Đăng ký thành công! Vui lòng đăng nhập lại");
                return userMapper.toUserResponse(userRepository.save(user));
            }
        }
    }
    @Override
    public UserResponse updatePassword(String oldPassword, String newPassword,
                                       String reNewPassword, User user, ModelMap model) {

        if (!newPassword.equals(reNewPassword) || user == null) {
            model.addAttribute("error", "Mật khẩu nhập lại không đúng!");
        } else if (passwordEncoder.matches(oldPassword,user.getPassword())) {
            if(!passwordEncoder.matches(newPassword,user.getPassword())){
                user.setPassword(passwordEncoder.encode(newPassword));
                model.addAttribute("success", "Đổi mật khẩu thành công!");
                return userMapper.toUserResponse(userRepository.save(user));
            }else {
                model.addAttribute("error", "Mật khẩu cũ và mật khẩu mới không được trùng nhau!");
                return null;
            }
        } else {
            model.addAttribute("error", "Mật khẩu cũ không đúng!");

        }

        return null;
    }

    @Override
    public UserResponse updateInformation(UserUpdateRequest request, User user, ModelMap modelMap) throws IOException {

        if(user != null){
            String urlImage = upload(request.getImage(), "users/");
            if(urlImage != null){
                user.setImage(urlImage);
            }
            user.setName(request.getName());
            user.setPhoneNumber(request.getPhoneNumber());
            user.setEmail(request.getEmail());
            user.setRoles(new HashSet<>(roleRepository.findAllByName(StringConstant.ROLE_USER)));
            return userMapper.toUserResponse(userRepository.save(user));
        }

        return null;
    }


    @Override
    public void removeSessionMessage() {

        HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest()
                .getSession();

        session.removeAttribute("msg");
    }


    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream().map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> findAllByNameLike(String name) {
        return userRepository.findAllByNameLike(name)
                .stream().map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }




    @Override
    public List<UserResponse> findAllByRoleName(String name) {
        return userRepository.findAllByRoleName(name)
                .stream().map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> findAllByCreateDateAndRoleName(LocalDate date, String roleName) {
        return userRepository.findAllByCreateDateAndRoleName(date, roleName)
                .stream().map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> findAllByMonthYearAndRoleName(int month, int year, String roleName) {
        return userRepository.findAllByMothYearRoleName(month, year, roleName)
                .stream().map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }

}
