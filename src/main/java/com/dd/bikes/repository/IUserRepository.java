package com.dd.bikes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dd.bikes.model.User;

public interface IUserRepository extends JpaRepository<User, Integer> {
	public User findByUsername(String username);

	@Query("SELECT u FROM User as u WHERE u.username = :uname AND u.password = :upass")
	User findByUsernameAndPassword(@Param("uname") String uname, @Param("upass") String upass);
}
