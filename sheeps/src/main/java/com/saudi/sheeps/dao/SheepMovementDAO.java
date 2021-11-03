package com.saudi.sheeps.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudi.sheeps.entity.SheepMovement;

@Repository
public interface SheepMovementDAO extends JpaRepository<SheepMovement, Long> {

}
