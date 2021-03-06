package com.haulmont.views.info;

import com.haulmont.model.entity.Bank;
import com.haulmont.model.entity.Client;
import com.haulmont.model.entity.CreditOffer;
import com.haulmont.model.entity.PaymentGraph;
import com.haulmont.model.service.daoService.BankService;
import com.haulmont.model.service.daoService.ClientService;
import com.haulmont.model.service.daoService.CreditOfferService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientsViewService extends Div {
    private final BankService bankService;
    private final CreditOfferService creditOfferService;
    private final ClientService clientService;

    private CreditOffer creditOfferForGrid;
    private List<Client> clients;

    private Select<Bank> selectBank;
    private final Button buttonBank = new Button();
    private final Button buttonGraph = new Button();
    private final Label creditOfferText = new Label();
    Grid<Client> clientGrid = new Grid<>(Client.class);
    Grid<PaymentGraph> graphGrid = new Grid<>(PaymentGraph.class);

    public ClientsViewService(@Autowired BankService bankService,
                              @Autowired ClientService clientService,
                              @Autowired CreditOfferService creditOfferService) {
        this.bankService = bankService;
        this.creditOfferService = creditOfferService;
        this.clientService = clientService;

        add(form());
    }

    private FormLayout form() {
        FormLayout form = new FormLayout();
        form.setWidth("5000");

        buttonBank.setText("??????????????");
        buttonBank.setHeight("300");

        buttonGraph.setText("???????????? ????????????????");
        buttonGraph.setHeight("300");

        openInfoClientsByBank();
        openGraphList();

        settingsSelectBank();
        settingsClientGrid();
        settingsGraphGrid();

        clientGrid.setVisible(false);
        buttonGraph.setVisible(false);
        graphGrid.setVisible(false);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(selectBank, buttonBank, clientGrid, creditOfferText, buttonGraph, graphGrid);

        form.add(verticalLayout);

        return form;
    }

    private void settingsSelectBank() {
        selectBank = new Select<>();
        selectBank.setLabel("???????????????? ????????");
        List<Bank> bankList = bankService.findAll();

        selectBank.setItemLabelGenerator(Bank::getNameBank);
        selectBank.setItems(bankList);
    }

    private void settingsClientGrid() {
        clientGrid.removeColumnByKey("idClient");
        clientGrid.removeColumnByKey("firstName");
        clientGrid.removeColumnByKey("lastName");
        clientGrid.removeColumnByKey("middleName");
        clientGrid.removeColumnByKey("numberPassport");
        clientGrid.removeColumnByKey("phoneNumber");
        clientGrid.removeColumnByKey("mail");
        clientGrid.removeColumnByKey("bank");

        clientGrid.addColumn(Client::getIdClient).setHeader("ID ??????????????");
        clientGrid.addColumn(Client::getFirstName).setHeader("??????????????");
        clientGrid.addColumn(Client::getLastName).setHeader("??????");
        clientGrid.addColumn(Client::getMiddleName).setHeader("????????????????");
        clientGrid.addColumn(Client::getNumberPassport).setHeader("?????????? ????????????????");
        clientGrid.addColumn(Client::getPhoneNumber).setHeader("?????????? ????????????????");
        clientGrid.addColumn(Client::getMail).setHeader("??????????");
    }

    private void settingsGraphGrid() {
        graphGrid.removeColumnByKey("idPaymentGraph");
        graphGrid.removeColumnByKey("datePayment");
        graphGrid.removeColumnByKey("amountPayment");
        graphGrid.removeColumnByKey("amountRepayment");
        graphGrid.removeColumnByKey("amountInterest");
        graphGrid.removeColumnByKey("creditOffer");

        graphGrid.addColumn(PaymentGraph::getIdPaymentGraph).setHeader("ID ??????????????");
        graphGrid.addColumn(PaymentGraph::getDatePayment).setHeader("???????? ??????????????");
        graphGrid.addColumn(PaymentGraph::getAmountPayment).setHeader("?????????? ????????????");
        graphGrid.addColumn(PaymentGraph::getAmountRepayment).setHeader("?????????? ??????????????");
        graphGrid.addColumn(PaymentGraph::getAmountInterest).setHeader("?????????? ??????????????????");
    }

    private void openInfoClientsByBank() {
        buttonBank.addClickListener(event -> {
            clients = clientService.findAll();

            List<Client> trueList = new ArrayList<>();
            clientGrid.setVisible(false);
            buttonGraph.setVisible(false);
            creditOfferText.setVisible(false);
            graphGrid.setVisible(false);

            if (selectBank.getValue() != null) {
                selectBank.setReadOnly(true);
                try{
                    for (Client client : clients) {
                        if (client.getBank()!=null && client.getBank().getIdBank().equals(selectBank.getValue().getIdBank()))
                            trueList.add(client);
                    }
                }
                catch (Exception e){
                    System.err.println("???????????? ???????????????? ???????????? ????????????????");
                    e.getMessage();
                }
                selectBank.setReadOnly(false);
                if (trueList.size() != 0) {
                    clientGrid.setItems(trueList);
                    clientGrid.setVisible(true);
                    buttonGraph.setVisible(true);
                    Notification.show("???????????? ???????????????? ?????? ?????????? " + selectBank.getValue().toString());
                } else {
                    Notification.show("?? ?????????????? ?????????? ?????? ????????????????");
                }

            } else {
                Notification.show("?????????????? ?????????? ?????????????? ????????");
            }
        });

        clientGrid.addItemClickListener(clientItemClickEvent -> {
            if(clientItemClickEvent.getItem().getIdClient().equals("") ){
                creditOfferText.setVisible(false);
            }

            Client selectClient = clientItemClickEvent.getItem();
            if (selectClient != null) {
                creditOfferText.setVisible(true);
                CreditOffer creditOffer = creditOfferService.findByClient(selectClient.getIdClient());
                String message;
                if (creditOffer == null) {
                    message = "?? ?????????????? ?????? ????????????????";

                } else {
                    message = creditOffer.toString();
                    creditOfferForGrid = creditOffer;
                }
                creditOfferText.setText(message);
            }
        });
    }

    private void openGraphList() {
        buttonGraph.addClickListener(event -> {
            graphGrid.setVisible(false);
            if (clientGrid.getSelectedItems().isEmpty()) {
                Notification.show("?????????????? ?????????? ?????????????? ??????????????");
            } else if (creditOfferForGrid != null) {
                List<PaymentGraph> trueList = creditOfferForGrid.getPaymentGraphList();
                if (trueList.size() != 0){
                    graphGrid.setItems(trueList);
                    graphGrid.setVisible(true);
                    Notification.show("?????????????? ???????????? ?????? " + creditOfferForGrid.getClient().toString());
                } else Notification.show("?????? ????????????????");
            }
        });

    }
}
