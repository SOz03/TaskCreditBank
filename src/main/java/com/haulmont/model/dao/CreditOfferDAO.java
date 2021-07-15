package com.haulmont.model.dao;

import com.haulmont.model.entity.CreditOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditOfferDAO extends JpaRepository<CreditOffer, String> {
}
