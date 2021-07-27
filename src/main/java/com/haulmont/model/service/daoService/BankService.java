package com.haulmont.model.service.daoService;

import com.haulmont.model.dao.BankDAO;

import com.haulmont.model.entity.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import java.util.List;

@Service
public class BankService extends CrudService<Bank, String> {
    @Autowired
    private BankDAO repository;

    @Override
    protected BankDAO getRepository() {
        return repository;
    }

    public List<Bank> findAll() {
        return (List<Bank>) repository.findAll();
    }

    public Bank findByNameBank(String nameBank) {
        return repository.findByNameBank(nameBank);
    }

}
