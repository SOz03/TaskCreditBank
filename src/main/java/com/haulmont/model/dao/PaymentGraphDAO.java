package com.haulmont.model.dao;

import com.haulmont.model.entity.PaymentGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentGraphDAO extends JpaRepository<PaymentGraph, String> {

}
