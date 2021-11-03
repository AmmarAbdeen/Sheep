package com.saudi.sheeps.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudi.sheeps.entity.Medicine;

@Repository
public interface MedicineDAO extends JpaRepository<Medicine, Long>{

	
	List<Medicine> findAllByQuantityGreaterThanAndExpiryDateGreaterThan(Double quantity,LocalDate date );
}
