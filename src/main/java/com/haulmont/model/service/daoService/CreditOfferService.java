package com.haulmont.model.service.daoService;

import com.haulmont.model.dao.CreditOfferDAO;
import com.haulmont.model.entity.Client;
import com.haulmont.model.entity.CreditOffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CreditOfferService extends CrudService<CreditOffer, String> {

    @Autowired
    private CreditOfferDAO repository;

    @Override
    protected CreditOfferDAO getRepository() {
        return repository;
    }

    public CreditOffer findByClientAndAmountCredit(Client client, BigDecimal amountCredit) {
        return repository.findByClientAndAmountCredit(client, amountCredit);
    }

    public List<CreditOffer> findAll(){
        return repository.findAll();
    }

    public CreditOffer findByClient(String idClient) {
        return repository.findByClient(idClient);
    }
}
