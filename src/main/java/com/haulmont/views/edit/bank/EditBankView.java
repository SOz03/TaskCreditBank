package com.haulmont.views.edit.bank;

import com.haulmont.model.entity.Bank;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.*;
import com.haulmont.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@PageTitle("Изменение банков")
@Route(value = "edit-bank", layout = MainLayout.class)
public class EditBankView extends Div {
    private final BankDataProvider bankDataProvider;

    public EditBankView(@Autowired BankDataProvider bankDataProvider) {
        this.bankDataProvider = bankDataProvider;
        add(addCrudClient());
    }

    private CrudEditor<Bank> createBankEditor() {
        TextField bankName = new TextField("Название банка");
        FormLayout form = new FormLayout(bankName);

        Binder<Bank> binder = new Binder<>(Bank.class);
        binder.bind(bankName, Bank::getNameBank, Bank::setNameBank);

        return new BinderCrudEditor<>(binder, form);
    }

    private Crud<Bank> addCrudClient() {
        Crud<Bank> crud = new Crud<>(Bank.class, createBankEditor());
        Span footer = new Span();
        footer.getElement().getStyle().set("flex", "1");

        com.vaadin.flow.component.button.Button newItemButton = new Button("Добавить банк ..");
        newItemButton.addClickListener(e -> crud.edit(new Bank(), Crud.EditMode.NEW_ITEM));

        crud.setToolbar(footer, newItemButton);

        bankDataProvider.setSizeChangeListener(count -> footer.setText("Всего банков: " + count));

        crud.getGrid().removeColumnByKey("idBank");
        crud.getGrid().removeColumnByKey("nameBank");
        crud.getGrid().removeColumnByKey("clients");

        crud.getGrid().addColumn(Bank::getIdBank).setHeader("ID банка");
        crud.getGrid().addColumn(Bank::getNameBank).setHeader("Название банка");

        crud.setDataProvider(bankDataProvider);

        crud.addSaveListener(e -> bankDataProvider.persist(e.getItem()));
        crud.addDeleteListener(e -> bankDataProvider.delete(e.getItem()));
        return crud;
    }
}
