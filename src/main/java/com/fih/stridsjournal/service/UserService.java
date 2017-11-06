package com.fih.stridsjournal.service;

import com.fih.stridsjournal.model.User;

public interface UserService {
	
	/**
	 * Check if user exists
	 * @param username
	 * @return
	 */
	public boolean userExists(String username);
	
	/**
	 * Add a new user
	 * @param user
	 * @return
	 */
	public User saveUser(User user);
	
	/**
	 * Update a user 
	 * @param user
	 * @return
	 */
	public User updateUser(User user);

}
