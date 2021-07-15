package com.haulmont.model.service.daoService;

import com.haulmont.model.dao.CreditOfferDAO;
import com.haulmont.model.entity.CreditOffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class CreditOfferService extends CrudService<CreditOffer, String> {

    private final CreditOfferDAO repository;

    public CreditOfferService(@Autowired CreditOfferDAO repository) {
        this.repository = repository;
    }

    @Override
    protected CreditOfferDAO getRepository() {
        return repository;
    }

}
