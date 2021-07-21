package com.haulmont.views.menu;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.stereotype.Service;

@Service
@PageTitle("Главное меню")
@Route(value = "welcome", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class MainMenuView extends Div {

    public MainMenuView() {
        Image image = new Image("https://dummyimage.com/1500x900/091994/e4e5f0.jpg&text=Credit+Bank", "DummyImage");
        add(image);
        add(image);
    }

}
