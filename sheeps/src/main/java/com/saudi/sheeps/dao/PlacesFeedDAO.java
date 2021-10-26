package com.saudi.sheeps.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudi.sheeps.entity.PlacesFeed;


@Repository
public interface PlacesFeedDAO extends JpaRepository<PlacesFeed, Long> {

}
