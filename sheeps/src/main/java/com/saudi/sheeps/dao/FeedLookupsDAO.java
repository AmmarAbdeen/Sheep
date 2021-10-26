package com.saudi.sheeps.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudi.sheeps.entity.FeedLookups;

@Repository
public interface FeedLookupsDAO extends JpaRepository<FeedLookups, Long> {

	FeedLookups findByName (String name);
}
