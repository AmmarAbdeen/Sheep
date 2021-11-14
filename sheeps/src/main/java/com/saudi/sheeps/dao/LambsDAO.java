package com.saudi.sheeps.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.saudi.sheeps.dto.Dashboard;
import com.saudi.sheeps.entity.Lambs;

@Repository
public interface LambsDAO extends JpaRepository<Lambs, Long> {
	Lambs findByCodeAndColor(Long code,String color);
	
	@Modifying(clearAutomatically = true)
	@Query("select count(*) as value,s.type as type from Lambs as s group by s.type")
	List<Dashboard> getAllLambsMaleAndFemale();
}
