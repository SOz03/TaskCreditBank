package com.haulmont.views.edit;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.haulmont.views.MainLayout;

@PageTitle("EditBank")
@Route(value = "edit-bank", layout = MainLayout.class)
public class EditBankView extends Div {

    public EditBankView() {
        addClassName("edit-bank-view");
        add(new Text("EditBankView"));
    }

}
