package com.haulmont.model.service.daoService;

import com.haulmont.model.dao.BankDAO;

import com.haulmont.model.entity.Bank;
import com.haulmont.model.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import java.util.List;

@Service
public class BankService extends CrudService<Bank, String> {

    private final BankDAO repository;

    public BankService(@Autowired BankDAO repository) {
        this.repository = repository;
    }

    @Override
    protected BankDAO getRepository() {
        return repository;
    }

    public List<Bank> findAll() {
        return (List<Bank>) repository.findAll();
    }
}