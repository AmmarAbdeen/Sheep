package com.saudi.sheeps.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudi.sheeps.entity.Feed;
import com.saudi.sheeps.entity.StoredFeed;

@Repository
public interface StoredFeedDAO  extends JpaRepository<StoredFeed, Long>{

	StoredFeed findByName (String name);
	
	List<StoredFeed> findAllByQuantityGreaterThan(Double quantity);
}
