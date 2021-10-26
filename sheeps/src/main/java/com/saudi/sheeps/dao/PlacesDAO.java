package com.saudi.sheeps.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudi.sheeps.entity.Places;


@Repository
public interface PlacesDAO extends JpaRepository<Places, Long> {

}
