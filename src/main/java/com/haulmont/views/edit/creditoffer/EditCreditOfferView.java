package com.haulmont.views.edit.creditoffer;

import com.haulmont.model.entity.Bank;
import com.haulmont.model.entity.Client;
import com.haulmont.model.entity.Credit;
import com.haulmont.model.entity.CreditOffer;
import com.haulmont.views.menu.MainLayout;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@PageTitle("Изменение кредитных предложений")
@Route(value = "edit-credit-offer", layout = MainLayout.class)
public class EditCreditOfferView extends Div {
    private final CreditOfferDataProvider creditOfferDataProvider;

    public EditCreditOfferView(@Autowired CreditOfferDataProvider creditOfferDataProvider) {
        this.creditOfferDataProvider = creditOfferDataProvider;
        add(addCrudCreditOffer());
    }

    private CrudEditor<CreditOffer> createCreditEditor() {
        ComboBox<Client> clientBox = new ComboBox<>("Клиент");
        ComboBox<Credit> creditBox = new ComboBox<>("Кредит");
        ComboBox<Bank> bankBox = new ComboBox<>("Банк");
        BigDecimalField amount = new BigDecimalField("Сумма кредита");

        FormLayout form = new FormLayout(clientBox, creditBox, bankBox, amount);

        Binder<CreditOffer> binder = new Binder<>(CreditOffer.class);
        binder.bind(clientBox, CreditOffer::getClient, CreditOffer::setClient);
        binder.bind(creditBox, CreditOffer::getCredit, CreditOffer::setCredit);
        binder.bind(bankBox, CreditOffer::getBank, CreditOffer::setBank);
        binder.bind(amount,CreditOffer::getAmountCredit, CreditOffer::setAmountCredit);

        return new BinderCrudEditor<>(binder, form);
    }

    private Crud<CreditOffer> addCrudCreditOffer() {
        Crud<CreditOffer> crud = new Crud<>(CreditOffer.class, createCreditEditor());
        Span footer = new Span();
        footer.getElement().getStyle().set("flex", "1");

        crud.setToolbar(footer);

        creditOfferDataProvider.setSizeChangeListener(count -> footer.setText("Всего кредитных предложений: " + count));

        crud.getGrid().removeColumnByKey("idCreditOffer");
        crud.getGrid().removeColumnByKey("amountCredit");
        crud.getGrid().removeColumnByKey("bank");
        crud.getGrid().removeColumnByKey("client");
        crud.getGrid().removeColumnByKey("credit");
        crud.getGrid().removeColumnByKey("paymentGraphList");

        //crud.getGrid().addColumn(CreditOffer::getIdCreditOffer).setHeader("ID кредитного предложения");
        crud.getGrid().addColumn(CreditOffer::getAmountCredit).setHeader("Сумма кредита");
        crud.getGrid().addColumn(CreditOffer::getBank).setHeader("Банк");
        crud.getGrid().addColumn(CreditOffer::getClient).setHeader("Клиент");
        crud.getGrid().addColumn(CreditOffer::getCredit).setHeader("Кредит");

        crud.setDataProvider(creditOfferDataProvider);

        crud.addSaveListener(e -> creditOfferDataProvider.persist(e.getItem()));
        crud.addDeleteListener(e -> creditOfferDataProvider.delete(e.getItem()));
        return crud;
    }
}

