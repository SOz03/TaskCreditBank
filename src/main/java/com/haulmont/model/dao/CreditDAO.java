package com.haulmont.model.dao;

import com.haulmont.model.entity.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditDAO extends JpaRepository<Credit,String> {
}
