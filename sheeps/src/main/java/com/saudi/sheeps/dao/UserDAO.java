package com.saudi.sheeps.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.saudi.sheeps.entity.User;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {
	
	@Query("select m from User m where m.password= :password and lower(m.userName) like lower(concat(:userName))")
	User findByUserNameContainingIgnoreCaseAndPassword(@Param("userName") String userName,
			@Param("password") String password);

}
