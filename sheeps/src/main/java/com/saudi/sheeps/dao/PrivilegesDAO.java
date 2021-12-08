package com.saudi.sheeps.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudi.sheeps.entity.Privileges;

@Repository
public interface PrivilegesDAO  extends JpaRepository<Privileges, Long>{
	
	List<Privileges> findAllByUserIdOrderByPrivilegeorder(Long userId);
	
	List<Privileges> findAllByUserIdAndAdminPrivilegeOrderByPrivilegeorder(Long userId,boolean adminPrivilege);

}
