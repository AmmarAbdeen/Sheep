package com.saudi.sheeps.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.saudi.sheeps.entity.Sheep;

@Repository
public interface SheepDAO extends JpaRepository<Sheep, Long> {
	
	Sheep  findByCodeAndColor(Long code, String color);
	
	List<Sheep> findAllByType(String type);
	
	Optional<Sheep> findById(Long id);
	
	@Modifying(clearAutomatically = true)
	@Query("select count(*) ,s.type from Sheep as s group by s.type")
	List<List> getAllSheepsMaleAndFemale();
	
	@Modifying(clearAutomatically = true)
	@Query(value = "select count(*),cast(EXTRACT(YEAR from AGE(s.birthdate)) +1 as bigint) from Sheep as s \r\n"
			+ "where s.type = 'ewe' GROUP BY EXTRACT(YEAR from AGE(s.birthdate)) order by EXTRACT(YEAR from AGE(s.birthdate))", nativeQuery = true)
	List<List> getAllSheepPerAge();
	

}

