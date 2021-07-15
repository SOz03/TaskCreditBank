package com.haulmont.views.menu;

import com.haulmont.model.dao.ClientDAO;
import com.haulmont.model.entity.Client;
import com.haulmont.model.service.daoService.ClientService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.haulmont.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@PageTitle("ClientsAndCredits")
@Route(value = "clients-and-credits", layout = MainLayout.class)
public class ClientsAndCreditsView extends Div {

    public ClientsAndCreditsView( @Autowired ClientService clientService) {

    }
}
