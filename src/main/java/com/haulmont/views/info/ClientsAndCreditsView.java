package com.haulmont.views.info;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.haulmont.views.menu.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@PageTitle("Клиенты и кредиты")
@Route(value = "clients-and-credits", layout = MainLayout.class)
public class ClientsAndCreditsView extends Div {

    public ClientsAndCreditsView(@Autowired ClientsViewService  clientsViewService) {
        add(clientsViewService);
    }

}
