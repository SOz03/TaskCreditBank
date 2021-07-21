package com.haulmont.model.service.daoService;

import com.haulmont.model.dao.CreditOfferDAO;
import com.haulmont.model.entity.Client;
import com.haulmont.model.entity.CreditOffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import java.math.BigDecimal;

@Service
public class CreditOfferService extends CrudService<CreditOffer, String> {

    @Autowired
    private final CreditOfferDAO repository;

    public CreditOfferService(@Autowired CreditOfferDAO repository) {
        this.repository = repository;
    }

    @Override
    protected CreditOfferDAO getRepository() {
        return repository;
    }

    public CreditOffer findByClientAndAmountCredit(Client client, BigDecimal amountCredit) {
        return repository.findByClientAndAmountCredit(client, amountCredit);
    }

    public CreditOffer findByClient(String idClient) {
        return repository.findByClient(idClient);
    }
}
