package com.haulmont.views.edit;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.haulmont.views.MainLayout;

@PageTitle("EditCredit")
@Route(value = "edit-credit", layout = MainLayout.class)
public class EditCreditView extends Div {

    public EditCreditView() {
        addClassName("edit-credit-view");
        add(new Text("Content placeholder"));
    }

}
