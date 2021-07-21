package com.haulmont.model.service.daoService;

import com.haulmont.model.dao.PaymentGraphDAO;
import com.haulmont.model.entity.PaymentGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import java.util.List;

@Service
public class PaymentGraphService extends CrudService<PaymentGraph, String> {

    @Autowired
    private final PaymentGraphDAO repository;

    public PaymentGraphService(@Autowired PaymentGraphDAO repository) {
        this.repository = repository;
    }

    @Override
    protected PaymentGraphDAO getRepository() {
        return repository;
    }

    public List<PaymentGraph> findAll(){
        return repository.findAll();
    }
}
