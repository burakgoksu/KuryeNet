package com.gp.KuryeNet.core.business.concretes;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gp.KuryeNet.core.dataAccess.UserDao;
import com.gp.KuryeNet.core.entities.User;

@Service
public class UserDetailsManager implements UserDetailsService{
	
	private UserDao userDao;

	public UserDetailsManager(UserDao userDao) {
		super();
		this.userDao = userDao;
	}

	@Async
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findFirstByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("User not found",null);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getUserRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().getRoleName()))
                .collect(Collectors.toList()));
	}
	

}
