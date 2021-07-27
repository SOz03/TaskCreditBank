package com.haulmont.model.dao;

import com.haulmont.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientDAO extends JpaRepository<Client, String> {

}
