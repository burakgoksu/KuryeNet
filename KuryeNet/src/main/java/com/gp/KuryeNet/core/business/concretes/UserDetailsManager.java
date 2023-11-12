package com.gp.KuryeNet.core.business.concretes;

import java.util.ArrayList;

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

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		//Write Logic to get the user from the DB
        User user = userDao.findFirstByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("User not found",null);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
	}

}
