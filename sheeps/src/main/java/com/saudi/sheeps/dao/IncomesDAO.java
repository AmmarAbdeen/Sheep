package com.saudi.sheeps.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudi.sheeps.entity.Income;

@Repository
public interface IncomesDAO  extends JpaRepository<Income, Long>{

}
