package com.saudi.sheeps.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudi.sheeps.entity.Feed;

@Repository
public interface FeedDAO extends JpaRepository<Feed, Long>{

	Feed findFeedByName (String name);
}
