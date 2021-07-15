package com.haulmont.model.dao;

import com.haulmont.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientDAO extends JpaRepository<Client, String> {
    @Query("SELECT c FROM Client c WHERE c.numberPassport = :numberPassport")
    Client findByPassport(@Param("numberPassport") String number);

//    @Query("SELECT c FROM Client c")
//    List<Client> findAll();
}
