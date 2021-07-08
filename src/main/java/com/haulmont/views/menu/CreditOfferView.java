package com.haulmont.views.menu;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.haulmont.views.MainLayout;

@PageTitle("CreditOffer")
@Route(value = "credit-offer", layout = MainLayout.class)
public class CreditOfferView extends Div {

    public CreditOfferView() {
        addClassName("credit-offer-view");
        add(new Text("CreditOfferView"));
    }

}
