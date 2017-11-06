package com.fih.stridsjournal.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users",uniqueConstraints=
@UniqueConstraint(columnNames = {"username"}))
public class User {

	private String username;
	
	
	@JsonIgnore
	private boolean enabled;
	
	private String password;
	
	@JsonIgnore
	private Set<UserRole> userRole = new HashSet<UserRole>(0);
	
	@Id
	@Column(name = "username", unique = true, 
		nullable = false, length = 45)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	public Set<UserRole> getUserRole() {
		return userRole;
	}

	public void setUserRole(Set<UserRole> userRole) {
		this.userRole = userRole;
	}

	
	public boolean hasRole(String role){
		for(UserRole r : this.userRole){
			if(role.equals(r.getRole())){
				return true;
			}
		}
		return false;
		
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	@Column(name = "password", 
			nullable = false, length = 60)
	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
