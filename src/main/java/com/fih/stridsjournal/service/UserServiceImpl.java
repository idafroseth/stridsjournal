package com.fih.stridsjournal.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fih.stridsjournal.dao.UserRepository;
import com.fih.stridsjournal.dao.UserRoleRepository;
import com.fih.stridsjournal.model.User;
import com.fih.stridsjournal.model.UserRole;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userDao;
	
	@Autowired
	UserRoleRepository userRoleDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public boolean userExists(String username) {
		if(username == null){
			return false;
		}
		return userDao.exists(username.toLowerCase());
	}

	
	@Override
	@Transactional
	public User saveUser(User user) {
		if(user == null){
			System.out.println("***User was null??");
			return null;
		}
		if(userDao.exists(user.getUsername())){
			System.out.println("***User already exists??");
			return null;
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		UserRole userRole = new UserRole("ROLE_USER");
		userRole.setUser(user);
		user.getUserRole().add(userRole);
		user.setEnabled(true);
		User savedUser = userDao.save(user);
		userRoleDao.save(userRole);
		System.out.println("***User is saved..");
		return savedUser;
	}

	@Override
	public User updateUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}
}
