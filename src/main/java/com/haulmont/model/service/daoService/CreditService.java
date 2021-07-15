package com.haulmont.model.service.daoService;

import com.haulmont.model.dao.CreditDAO;
import com.haulmont.model.entity.Credit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import java.util.List;

@Service
public class CreditService extends CrudService<Credit, String > {

    private final CreditDAO creditDAO;

    public CreditService(@Autowired CreditDAO creditDAO) {
        this.creditDAO = creditDAO;
    }

    @Override
    protected CreditDAO getRepository() {
        return creditDAO;
    }

    public List<Credit> findAll(){
        return (List<Credit>) creditDAO.findAll();
    }

}
