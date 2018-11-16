package com.fih.stridsjournal.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fih.stridsjournal.dao.UserRepository;
import com.fih.stridsjournal.model.UserRole;



@Service("authenticationService")
public class MyAuthenticationService implements UserDetailsService  {

	
	private UserRepository userDao;
	
	@Autowired
	public MyAuthenticationService(UserRepository userRepo){
		this.userDao = userRepo;
	}
	
	public MyAuthenticationService(){
		
	}

	@Transactional(readOnly=true)
	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
	
		
		Optional<com.fih.stridsjournal.model.User> userOptional = userDao.findById(username);
		com.fih.stridsjournal.model.User user = userOptional.isPresent() ? userOptional.get() : null;
		if(user == null){
			return null;
		}
		List<GrantedAuthority> authorities = 
                                      buildUserAuthority(user.getUserRole());
		return buildUserForAuthentication(user, authorities);
		
	}
	
	private User buildUserForAuthentication(com.fih.stridsjournal.model.User user, 
		List<GrantedAuthority> authorities) {
		return new User(user.getUsername(), user.getPassword(), 
			user.isEnabled(), true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		// Build user's authorities
		for (UserRole userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
		}

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}
	

}