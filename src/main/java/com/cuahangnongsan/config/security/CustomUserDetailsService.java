package com.cuahangnongsan.config.security;

import com.cuahangnongsan.constant.StringConstant;
import com.cuahangnongsan.entity.User;
import com.cuahangnongsan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepo.findByUsernameAndStatus(username, StringConstant.USER_STATUS_ACTIVE);

		if (user == null) {
			throw new UsernameNotFoundException("user not found");
		} else {
			return new CustomUser(user);
		}

	}

}
