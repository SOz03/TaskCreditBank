package com.haulmont.views.newoffer;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.haulmont.views.menu.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@PageTitle("Кредитное предложение")
@Route(value = "credit-offer", layout = MainLayout.class)
public class CreditOfferView extends Div {

    public CreditOfferView(@Autowired CreditOfferViewService creditOfferViewService) {
        creditOfferViewService.updateComboItem();
        add(creditOfferViewService);
    }
}
