package com.haulmont.model.service;

import com.haulmont.model.entity.*;
import com.haulmont.model.service.daoService.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class CreateDB {
    @Autowired
    private ClientService clientService;
    @Autowired
    private CreditService creditService;
    @Autowired
    private BankService bankService;
    @Autowired
    private CreditOfferService creditOfferService;
    @Autowired
    private PaymentGraphService paymentGraphService;

    private boolean createDB = false;

    public void createDB() {
        if (!createDB) {
            saveEntity();
        }
    }

    private void saveEntity() {
        createDB = true;
        Bank bank = new Bank("13e4df11-a84b-4ace-a04e-1ce384f64320", "ООО 'ВТБ'");
        Bank bank1 = new Bank("2670d375-8b75-4760-9b1a-0c167a0ac5a9", "АльфаБанк");
        Bank bank2 = new Bank("f4b9536f-d9ef-49b9-ab24-5c0fe61d2a39", "OOO 'Сбер'");
        bankService.update(bank);
        bankService.update(bank1);
        bankService.update(bank2);

        Client client = new Client("5555dce1-0880-4044-ac4d-669bf15dc392", "1111 202333", "Петр", "Петров", "Петрович", "89303555111", "petrov@mail.ru", null);
        Client client1 = new Client("9d63c020-7343-4fe3-b5e2-48e09f6f00d8", "1802 705901", "Антон", "Антонов", "Антонович", "89707511333", "antonov@mail.ru", bank);
        Client client2 = new Client("baa916a3-bce8-4560-9e9c-c2984e120201", "1212 101103", "Иван", "Иванов", "Иванович", "89897750555", "ivanov@mail.ru", null);
        Client client3 = new Client("e743ee18-0880-4044-ac4d-669bf15dc392", "3618 909777", "Сергей", "Сергеев", "Сергеевич", "89275670701", "sergeev@mail.ru", null);
        clientService.update(client);
        clientService.update(client1);
        clientService.update(client2);
        clientService.update(client3);

        Credit credit = new Credit("19d25086-2a6f-4e9d-a096-7fb1193ee5f8", new BigDecimal(500_000), new BigDecimal(7));
        Credit credit1 = new Credit("2a1e6cf2-5fdc-41bd-9bc4-17f1b434dd1a", new BigDecimal(1_000_000), new BigDecimal(10));
        Credit credit2 = new Credit("408b0ade-2345-4b1f-8d98-e1a4b5ab2ee0", new BigDecimal(100_000), new BigDecimal(5));
        creditService.update(credit);
        creditService.update(credit1);
        creditService.update(credit2);

        CreditOffer creditOffer = new CreditOffer("03bc016b-cdc7-4f3a-b14a-437f38f06ac4", client1, credit, new BigDecimal(535_000), bank);
        creditOfferService.update(creditOffer);

        PaymentGraph paymentGraph = new PaymentGraph("0996900c-edb6-46d7-ab74-4f49772c0ec7", new Date(2021 - 10 - 21), new BigDecimal("44583.34"), new BigDecimal("41666.67"), new BigDecimal("2916.67"), creditOffer);
        PaymentGraph paymentGraph1 = new PaymentGraph("13e7c07d-f2f0-4f09-91b3-a08ca5fc2d98", new Date(2021 - 10 - 21), new BigDecimal("44583.34"), new BigDecimal("41666.67"), new BigDecimal("2916.67"), creditOffer);
        PaymentGraph paymentGraph2 = new PaymentGraph("4bb41fd8-40ab-404d-ba93-f4bb1240c4b6", new Date(2021 - 10 - 21), new BigDecimal("44583.34"), new BigDecimal("41666.67"), new BigDecimal("2916.67"), creditOffer);
        PaymentGraph paymentGraph3 = new PaymentGraph("4c439c95-e4c6-44fc-9ee7-8db65710de55", new Date(2021 - 10 - 21), new BigDecimal("44583.34"), new BigDecimal("41666.67"), new BigDecimal("2916.67"), creditOffer);
        PaymentGraph paymentGraph4 = new PaymentGraph("6893ae21-9343-4161-a191-cefc62e0db8e", new Date(2021 - 10 - 21), new BigDecimal("44583.34"), new BigDecimal("41666.67"), new BigDecimal("2916.67"), creditOffer);
        PaymentGraph paymentGraph5 = new PaymentGraph("79237c0b-989e-46c4-8a9b-e96469d7b9a4", new Date(2021 - 10 - 21), new BigDecimal("44583.34"), new BigDecimal("41666.67"), new BigDecimal("2916.67"), creditOffer);
        PaymentGraph paymentGraph6 = new PaymentGraph("82519366-9462-40fc-934d-6b69bca61828", new Date(2021 - 10 - 21), new BigDecimal("44583.34"), new BigDecimal("41666.67"), new BigDecimal("2916.67"), creditOffer);
        PaymentGraph paymentGraph7 = new PaymentGraph("9da74e9b-26fd-414b-978c-ff4298cc2774", new Date(2021 - 10 - 21), new BigDecimal("44583.34"), new BigDecimal("41666.67"), new BigDecimal("2916.67"), creditOffer);
        PaymentGraph paymentGraph8 = new PaymentGraph("aaf78c39-02a4-4045-9ab0-a779298a752a", new Date(2021 - 10 - 21), new BigDecimal("44583.34"), new BigDecimal("41666.67"), new BigDecimal("2916.67"), creditOffer);
        PaymentGraph paymentGraph9 = new PaymentGraph("b2fcfb62-7940-4151-9c48-548916560d6f", new Date(2021 - 10 - 21), new BigDecimal("44583.34"), new BigDecimal("41666.67"), new BigDecimal("2916.67"), creditOffer);
        PaymentGraph paymentGraph10 = new PaymentGraph("c67d6147-7553-4df7-a82f-0bc017d828cf", new Date(2021 - 10 - 21), new BigDecimal("44583.34"), new BigDecimal("41666.67"), new BigDecimal("2916.67"), creditOffer);
        PaymentGraph paymentGraph11 = new PaymentGraph("e2843424-c839-4790-b724-e4f953c1c332", new Date(2021 - 10 - 21), new BigDecimal("44583.34"), new BigDecimal("41666.67"), new BigDecimal("2916.67"), creditOffer);
        paymentGraphService.update(paymentGraph);
        paymentGraphService.update(paymentGraph1);
        paymentGraphService.update(paymentGraph2);
        paymentGraphService.update(paymentGraph3);
        paymentGraphService.update(paymentGraph4);
        paymentGraphService.update(paymentGraph5);
        paymentGraphService.update(paymentGraph6);
        paymentGraphService.update(paymentGraph7);
        paymentGraphService.update(paymentGraph8);
        paymentGraphService.update(paymentGraph9);
        paymentGraphService.update(paymentGraph10);
        paymentGraphService.update(paymentGraph11);
    }
}
