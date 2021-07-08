package com.haulmont.views.menu;

import com.haulmont.views.MainLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("MainMenu")
@Route(value = "welcome", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class MainMenuView extends Div {

    public MainMenuView() {
        addClassName("main-menu-view");
        add(new Text("Content placeholder"));
    }

}
