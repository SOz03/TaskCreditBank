package com.haulmont.views.edit.client;

import com.haulmont.model.entity.Client;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.crud.*;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.haulmont.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@PageTitle("Изменение клиентов")
@Route(value = "edit-client", layout = MainLayout.class)
public class EditClientView extends Div {
    private final ClientDataProvider clientDataProvider;

    public EditClientView(@Autowired ClientDataProvider clientDataProvider) {
        this.clientDataProvider = clientDataProvider;
        add(addCrudClient());
    }

    private CrudEditor<Client> createPersonEditor() {
        TextField firstName = new TextField("Имя");
        TextField lastName = new TextField("Фамилия");
        TextField middleName = new TextField("Отчество");
        TextField numberPassport = new TextField("Номер паспорта");
        TextField mail = new TextField("Почта");
        TextField phoneNumber = new TextField("Номер");
        FormLayout form = new FormLayout(firstName, lastName, middleName, numberPassport,
                mail, phoneNumber);

        Binder<Client> binder = new Binder<>(Client.class);
        binder.bind(firstName, Client::getFirstName, Client::setFirstName);
        binder.bind(lastName, Client::getLastName, Client::setLastName);
        binder.bind(middleName, Client::getMiddleName, Client::setMiddleName);
        binder.bind(numberPassport, Client::getNumberPassport, Client::setNumberPassport);
        binder.bind(mail, Client::getMail, Client::setMail);
        binder.bind(phoneNumber, Client::getPhoneNumber, Client::setPhoneNumber);

        return new BinderCrudEditor<>(binder, form);
    }

    private Crud<Client> addCrudClient() {
        Crud<Client> crud = new Crud<>(Client.class, createPersonEditor());
        Span footer = new Span();
        footer.getElement().getStyle().set("flex", "1");

        Button newItemButton = new Button("Добавить клиента ..");
        newItemButton.addClickListener(e -> crud.edit(new Client(), Crud.EditMode.NEW_ITEM));

        crud.setToolbar(footer, newItemButton);

        clientDataProvider.setSizeChangeListener(count -> footer.setText("Всего клиентов: " + count));

        crud.getGrid().removeColumnByKey("idClient");
        crud.getGrid().removeColumnByKey("numberPassport");
        crud.getGrid().removeColumnByKey("firstName");
        crud.getGrid().removeColumnByKey("lastName");
        crud.getGrid().removeColumnByKey("middleName");
        crud.getGrid().removeColumnByKey("phoneNumber");
        crud.getGrid().removeColumnByKey("mail");
        crud.getGrid().removeColumnByKey("mainBank");

        //crud.getGrid().addColumn(Client::getIdClient).setHeader("ID клиента");
        crud.getGrid().addColumn(Client::getFirstName).setHeader("Имя");
        crud.getGrid().addColumn(Client::getLastName).setHeader("Фамилия");
        crud.getGrid().addColumn(Client::getMiddleName).setHeader("Отчество");
        crud.getGrid().addColumn(Client::getNumberPassport).setHeader("Номер паспорта");
        crud.getGrid().addColumn(Client::getMail).setHeader("Почта");
        crud.getGrid().addColumn(Client::getPhoneNumber).setHeader("Номер телефона");
        crud.getGrid().addColumn(Client::getMainBank).setHeader("Оформлен в банке");

        crud.setDataProvider(clientDataProvider);

        crud.addSaveListener(e -> clientDataProvider.persist(e.getItem()));
        crud.addDeleteListener(e -> clientDataProvider.delete(e.getItem()));
        return crud;
    }

}
