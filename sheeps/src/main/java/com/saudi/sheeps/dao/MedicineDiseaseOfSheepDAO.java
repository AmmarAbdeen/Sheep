package com.saudi.sheeps.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudi.sheeps.entity.MedicineDiseaseOfSheep;



@Repository
public interface MedicineDiseaseOfSheepDAO extends JpaRepository<MedicineDiseaseOfSheep, Long>{

}
