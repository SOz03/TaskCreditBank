package com.haulmont.views.newoffer;

import com.haulmont.model.entity.*;
import com.haulmont.model.service.daoService.*;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.button.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CreditOfferViewService extends Div {

    private final CreditOfferService creditOfferService;
    private final CreditService creditService;
    private final ClientService clientService;
    private final BankService bankService;
    private final PaymentGraphService paymentGraphService;

    private final BigDecimalField amountCreditField = new BigDecimalField();
    private final ComboBox<Bank> bankBox = new ComboBox<>();
    private final ComboBox<Client> clientBox = new ComboBox<>();
    private final ComboBox<Credit> creditBox = new ComboBox<>();
    private final TextField bankField = new TextField();
    private final TextField clientField = new TextField();
    private final BigDecimalField creditField = new BigDecimalField();
    private final Button addCreditOffer = new Button();

    private Client client;
    private Bank bank;
    private CreditOffer creditOffer = null;

    public CreditOfferViewService(@Autowired CreditOfferService creditOfferService,
                                  @Autowired CreditService creditService,
                                  @Autowired ClientService clientService,
                                  @Autowired BankService bankService,
                                  @Autowired PaymentGraphService paymentGraphService) {
        this.creditOfferService = creditOfferService;
        this.creditService = creditService;
        this.clientService = clientService;
        this.bankService = bankService;
        this.paymentGraphService = paymentGraphService;
        setSizeFull();
        newCreditOffer();
        add(formLayout());
    }

    private FormLayout formLayout() {
        FormLayout form = new FormLayout();

        bankBox.setLabel("Банк");
        clientBox.setLabel("Клиент");
        creditBox.setLabel("Кредит");

        bankBox.setWidth("300px");
        clientBox.setWidth("300px");
        creditBox.setWidth("300px");

        bankField.setLabel("Выбранный банк");
        clientField.setLabel("Выбранный клиент");
        creditField.setLabel("Выбранный кредит");

        bankField.setWidth("300px");
        clientField.setWidth("300px");
        creditField.setWidth("300px");

        addCreditOffer.setText("Создать кредит");
        amountCreditField.setLabel("Общая сумма по кредиту с учетом %");

        amountCreditField.setEnabled(false);
        bankField.setEnabled(false);
        clientField.setEnabled(false);
        creditField.setEnabled(false);

        updateComboItem();

        bankBox.addValueChangeListener(event -> {
            bankField.setValue(event.getValue().getNameBank());
        });
        clientBox.addValueChangeListener(event -> {
            clientField.setValue(event.getValue().getFirstName() + " " + event.getValue().getLastName() +
                    " " + event.getValue().getMiddleName());

            client = new Client(event.getValue().getIdClient(), event.getValue().getNumberPassport(),
                    event.getValue().getFirstName(), event.getValue().getLastName(),
                    event.getValue().getMiddleName(), event.getValue().getPhoneNumber(),
                    event.getValue().getMail(), event.getValue().getBank());
        });
        creditBox.addValueChangeListener(event -> {
            creditField.setValue(event.getValue().getCreditLimit());
            calculationAmount();
        });

        VerticalLayout menuBox = new VerticalLayout();
        menuBox.add(clientBox, clientField, bankBox, bankField, creditBox, creditField,
                amountCreditField, addCreditOffer);

        form.add(menuBox);
        return form;
    }


    public void updateComboItem() {
        List<Bank> bankList = bankService.findAll();
        List<Credit> creditList = creditService.findAll();
        List<Client> clientList = clientService.findAll();

        bankBox.setItems(bankList);
        creditBox.setItems(creditList);
        clientBox.setItems(clientList);
    }

    public void calculationAmount() {
        Credit credit = creditService.findByCreditLimit(creditField.getValue());
        BigDecimal percent = credit.getInterestRate().multiply(new BigDecimal("0.01"));
        amountCreditField.setValue(credit.getCreditLimit().multiply(percent).add(credit.getCreditLimit()));
    }

    private void newCreditOffer() {
        addCreditOffer.addClickListener(event -> {
            if (bankField.getValue().isEmpty() || clientField.getValue().isEmpty() ||
                    creditField.getValue()==null) {
                Notification.show("Заполните все поля");
            } else {
                bank = bankService.findByNameBank(bankField.getValue());
                Credit credit = creditService.findByCreditLimit(creditField.getValue());

                if (creditOfferService.findByClient(client.getIdClient()) == null) {
                    creditOffer = new CreditOffer(client, credit, amountCreditField.getValue(),
                            bank);

                    try {
                        creditOfferService.update(creditOffer);
                        client.setBank(bank);
                        clientService.update(client);
                        newGraph(creditOffer);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Notification.show("Кредит оформлен на клиента - " + client.toString());

                    creditOffer = null;
                    client = null;
                } else {
                    Notification.show("У клиента " + client.toString() + " уже оформлен кредит!");
                }
                clear();
            }
        });
    }

    private void newGraph(CreditOffer creditOffer) {
        Date date = new Date();
        new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(date);
        BigDecimal creditLimit = creditOffer.getCredit().getCreditLimit();
        BigDecimal amount = creditLimit.divide(new BigDecimal(12), 2 , RoundingMode.CEILING);
        BigDecimal amountInterest = creditOffer.getCredit().getInterestRate().multiply(new BigDecimal("0.01")).multiply(amount);
        BigDecimal amountMonth = amount.add(amountInterest);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        for (int i = 1; i <= 12; i++) {
            calendar.add(Calendar.MONTH, +1);
            PaymentGraph paymentGraph = new PaymentGraph(calendar.getTime(), amountMonth,
                    amount, amountInterest, creditOffer);
            paymentGraphService.update(paymentGraph);
            calendar.setTime(calendar.getTime());
        }
        Notification.show("График для кредита создан");
    }

    private void clear() {
        clientField.clear();
        creditField.clear();
        bankField.clear();
        amountCreditField.clear();
    }
}
