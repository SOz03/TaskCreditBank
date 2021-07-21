package com.haulmont.model.dao;

import com.haulmont.model.entity.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface CreditDAO extends JpaRepository<Credit,String> {
    @Query("SELECT c FROM Credit c WHERE c.creditLimit = ?1")
    Credit findByCreditLimit(BigDecimal creditLimit);
}
