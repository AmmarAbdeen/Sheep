package com.saudi.sheeps.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudi.sheeps.entity.Lambs;

@Repository
public interface LambsDAO extends JpaRepository<Lambs, Long> {
	Lambs findByCodeAndColor(Long code,String color);
}
