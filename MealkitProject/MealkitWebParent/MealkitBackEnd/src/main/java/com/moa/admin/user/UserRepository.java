package com.moa.admin.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.moa.common.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	@Query("SELECT a FROM User a WHERE a.email = :email")
	public User getUserByEmail(@Param("email") String email);
}
