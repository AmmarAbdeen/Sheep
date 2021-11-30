package com.saudi.sheeps.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudi.sheeps.entity.GeneralPayment;

@Repository
public interface GeneralPaymentsDAO extends JpaRepository<GeneralPayment, Long>{

}
