package com.haulmont.views.menu;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.haulmont.views.MainLayout;

@PageTitle("ClientsAndCredits")
@Route(value = "clients-and-credits", layout = MainLayout.class)
public class ClientsAndCreditsView extends Div {

    public ClientsAndCreditsView() {
        addClassName("clients-and-credits-view");
        add(new Text("ClientsAndCreditsView"));
    }

}
