package com.haulmont.model.dao;

import com.haulmont.model.entity.Client;
import com.haulmont.model.entity.CreditOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface CreditOfferDAO extends JpaRepository<CreditOffer, String> {
    @Query("SELECT c FROM CreditOffer c WHERE c.client = ?1 AND c.amountCredit = ?2")
    CreditOffer findByClientAndAmountCredit(Client client, BigDecimal amountCredit);

    @Query("SELECT c FROM CreditOffer c WHERE c.client.idClient = ?1")
    CreditOffer findByClient(String idClient);
}
