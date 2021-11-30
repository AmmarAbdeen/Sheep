package com.saudi.sheeps.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudi.sheeps.entity.Lookups;


@Repository
public interface LookupsDAO extends JpaRepository<Lookups, Long>  {
	
	List<Lookups> findAllByType(String type);
	
	Lookups findByCode(String code);
	
	Lookups findByNameAR(String nameAR);
	

}
