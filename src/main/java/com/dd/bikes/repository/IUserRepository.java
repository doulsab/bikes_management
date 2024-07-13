package com.dd.bikes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dd.bikes.model.User;
@Repository

public interface IUserRepository extends JpaRepository<User, Integer> {
	public User findByUsername(String username);
}
