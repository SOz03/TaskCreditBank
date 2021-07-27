package com.haulmont.model.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(/*name = "credit_offer"*/)
public class CreditOffer{
    @Id
//    @Column(name = "id_credit_offer")
    private String idCreditOffer;
//   @Column(name = "amount_credit")
    private BigDecimal amountCredit;
    @ManyToOne
//    @JoinColumn(name = "bank")
    private Bank bank;
    @ManyToOne
//    @JoinColumn(name = "client")
    private Client client;
    @ManyToOne
//    @JoinColumn(name = "credit")
    private Credit credit;

    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER, mappedBy = "creditOffer")
    List<PaymentGraph> paymentGraphList;

    public CreditOffer(Client client, Credit credit, BigDecimal amountCredit, Bank bank) {
        this.idCreditOffer = UUID.randomUUID().toString();
        this.client = client;
        this.credit = credit;
        this.amountCredit = amountCredit;
        this.bank = bank;
    }

    public CreditOffer(String idCreditOffer, Client client, Credit credit, BigDecimal amountCredit, Bank bank) {
        this.idCreditOffer = idCreditOffer;
        this.client = client;
        this.credit = credit;
        this.amountCredit = amountCredit;
        this.bank = bank;
    }

    public CreditOffer() {

    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public String getIdCreditOffer() {
        return idCreditOffer;
    }

    public void setIdCreditOffer(String idCreditOffer) {
        this.idCreditOffer = idCreditOffer;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    public BigDecimal getAmountCredit() {
        return amountCredit;
    }

    public void setAmountCredit(BigDecimal amountCredit) {
        this.amountCredit = amountCredit;
    }

    public List<PaymentGraph> getPaymentGraphList() {
        return paymentGraphList;
    }

    public void setPaymentGraphList(List<PaymentGraph> paymentGraphList) {
        this.paymentGraphList = paymentGraphList;
    }

    @Override
    public String toString() {
        return "КРЕДИТНОЕ ПРЕДЛОЖЕНИЕ:\n где кредитный лимит(" +
                credit.getCreditLimit() + "), процент(" +
                credit.getInterestRate() + "%) на 12 месяцев";
    }
}
