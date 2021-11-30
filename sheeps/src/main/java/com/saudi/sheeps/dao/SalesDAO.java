package com.saudi.sheeps.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudi.sheeps.entity.Sales;

@Repository
public interface SalesDAO extends JpaRepository<Sales, Long>{

}
