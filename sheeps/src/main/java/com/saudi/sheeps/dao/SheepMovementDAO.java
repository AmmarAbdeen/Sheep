package com.saudi.sheeps.dao;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudi.sheeps.entity.SheepMovement;

@Repository
public interface SheepMovementDAO extends JpaRepository<SheepMovement, Long> {
	
	SheepMovement findBySheepIdAndPlacesIdAndDateBetween (Long sheepId ,Long placesId,LocalDate dateBefore3Days , LocalDate dateNow);

	SheepMovement findFirstBySheepIdOrderByDateDesc (Long sheepId);
	
	SheepMovement findByLambsIdAndPlacesIdAndDateBetween (Long lambId ,Long placesId,LocalDate dateBefore3Days , LocalDate dateNow);
	
	SheepMovement findFirstByLambsIdOrderByDateDesc(Long lambId);
}
