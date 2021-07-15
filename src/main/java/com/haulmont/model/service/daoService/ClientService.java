package com.haulmont.model.service.daoService;

import com.haulmont.model.dao.ClientDAO;
import com.haulmont.model.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import java.util.List;

@Service
public class ClientService extends CrudService<Client, String> {

    @Autowired
    private final ClientDAO clientRepository;

    public ClientService(@Autowired ClientDAO clientRepository) {
        this.clientRepository = clientRepository;
    }
    @Override
    protected ClientDAO getRepository() {
        return clientRepository;
    }

    public List<Client> findAll() {
        return (List<Client>) clientRepository.findAll();
    }



}
