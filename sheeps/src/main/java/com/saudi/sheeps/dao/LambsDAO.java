package com.saudi.sheeps.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.saudi.sheeps.entity.Lambs;

@Repository
public interface LambsDAO extends JpaRepository<Lambs, Long> {
	Lambs findByCodeAndColor(Long code, String color);

	@Modifying(clearAutomatically = true)
	@Query("select count(*),s.type from Lambs as s group by s.type")
	List<List> getAllLambsMaleAndFemale();

	@Modifying(clearAutomatically = true)
	@Query(value = "select count(*),to_char(date_trunc('month', s.birthdate), 'Mon') from Lambs as s \r\n"
			+ "where s.birthdate between :firstDate and :secondDate\r\n"
			+ "GROUP BY date_trunc('month', s.birthdate) order by date_trunc('month', s.birthdate)", nativeQuery = true)
	List<List> getAllLambsPerMonth(@Param("firstDate") LocalDate firstDate,@Param("secondDate") LocalDate secondDate);
}
