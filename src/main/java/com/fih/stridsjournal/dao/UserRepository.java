package com.fih.stridsjournal.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fih.stridsjournal.model.User;

@Repository//@RepositoryRestResource( path = "User")
public interface UserRepository extends PagingAndSortingRepository<User, String> {
}
