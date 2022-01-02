package com.saudi.sheeps.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudi.sheeps.entity.BugsAndError;

@Repository
public interface ErrorsDAO extends JpaRepository<BugsAndError,Long>{

}
