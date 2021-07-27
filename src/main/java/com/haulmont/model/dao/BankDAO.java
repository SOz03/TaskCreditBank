package com.haulmont.model.dao;

import com.haulmont.model.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BankDAO extends  JpaRepository<Bank, String> {
    @Query("SELECT b FROM Bank b WHERE b.nameBank = ?1")
    Bank findByNameBank(String nameBank);
}
