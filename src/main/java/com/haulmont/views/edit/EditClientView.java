package com.haulmont.views.edit;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.haulmont.views.MainLayout;

@PageTitle("EditClient")
@Route(value = "edit-client", layout = MainLayout.class)
public class EditClientView extends Div {

    public EditClientView() {
        addClassName("edit-client-view");
        add(new Text("Content placeholder"));
    }

}
