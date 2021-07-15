package com.haulmont.views.edit.credit;

import com.haulmont.model.entity.Credit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.crud.*;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.haulmont.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@PageTitle("Изменение тарифов на кредит")
@Route(value = "edit-credit", layout = MainLayout.class)
public class EditCreditView extends Div {

    private final CreditDataProvider creditDataProvider;

    public EditCreditView(@Autowired CreditDataProvider creditDataProvider) {
        this.creditDataProvider = creditDataProvider;
        add(addCrudCredit());
    }

    private CrudEditor<Credit> createCreditEditor() {
        BigDecimalField creditLimit = new BigDecimalField("Лимит кредита");
        BigDecimalField interestRate = new BigDecimalField("Процент");

        FormLayout form = new FormLayout(creditLimit, interestRate);

        Binder<Credit> binder = new Binder<>(Credit.class);
        binder.bind(creditLimit, Credit::getCreditLimit, Credit::setCreditLimit);
        binder.bind(interestRate, Credit::getInterestRate, Credit::setInterestRate);

        return new BinderCrudEditor<>(binder, form);
    }

    private Crud<Credit> addCrudCredit() {
        Crud<Credit> crud = new Crud<>(Credit.class, createCreditEditor());
        Span footer = new Span();
        footer.getElement().getStyle().set("flex", "1");

        Button newItemButton = new Button("Добавить кредит ..");
        newItemButton.addClickListener(event -> crud.edit(new Credit(), Crud.EditMode.NEW_ITEM));

        crud.setToolbar(footer, newItemButton);

        creditDataProvider.setSizeChangeListener(count -> footer.setText("Имеющихся кредитов: " + count));

        crud.getGrid().removeColumnByKey("idCredit");
        crud.getGrid().removeColumnByKey("creditLimit");
        crud.getGrid().removeColumnByKey("interestRate");

        //crud.getGrid().addColumn(Credit::getIdCredit).setHeader("ID кредита");
        crud.getGrid().addColumn(Credit::getCreditLimit).setHeader("Лимит кредита");
        crud.getGrid().addColumn(Credit::getInterestRate).setHeader("Процент");

        crud.setDataProvider(creditDataProvider);

        crud.addSaveListener(e -> creditDataProvider.persist(e.getItem()));
        crud.addDeleteListener(e -> creditDataProvider.delete(e.getItem()));
        return crud;
    }
}
