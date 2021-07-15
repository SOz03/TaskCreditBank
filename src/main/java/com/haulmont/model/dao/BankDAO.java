package com.haulmont.model.dao;

import com.haulmont.model.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankDAO extends JpaRepository<Bank, String> {

}
