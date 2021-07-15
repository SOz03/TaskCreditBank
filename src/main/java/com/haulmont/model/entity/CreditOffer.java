package com.haulmont.model.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "credit_offer")
public class CreditOffer{
    @Id
    @Column(name = "id_credit_offer")
    private String idCreditOffer;
    @ManyToOne
    @JoinColumn(name = "client")
    private Client client;
    @ManyToOne
    @JoinColumn(name = "credit")
    private Credit credit;
    @Column(name = "amount_credit")
    private BigDecimal amountCredit;
    @Column(name = "date_payment")
    private Date datePayment;
    @Column(name = "amount_payment")
    private BigDecimal amountPayment;
    @Column(name = "amount_repayment")
    private BigDecimal amountRepayment;
    @Column(name = "amount_interest")
    private BigDecimal amountInterest;

    public CreditOffer(Client client, Credit credit, BigDecimal amountCredit, Date datePayment,
                       BigDecimal amountPayment, BigDecimal amountRepayment, BigDecimal amountInterest) {
        this.idCreditOffer = UUID.randomUUID().toString();
        this.client = client;
        this.credit = credit;
        this.amountCredit = amountCredit;
        this.datePayment = datePayment;
        this.amountPayment = amountPayment;
        this.amountRepayment = amountRepayment;
        this.amountInterest = amountInterest;
    }

    public CreditOffer() {

    }

    public String getIdCreditOffer() {
        return idCreditOffer;
    }

    public Client getClient() {
        return client;
    }

    public Credit getCredit() {
        return credit;
    }

    public BigDecimal getAmountCredit() {
        return amountCredit;
    }

    public Date getDatePayment() {
        return datePayment;
    }

    public BigDecimal getAmountPayment() {
        return amountPayment;
    }

    public BigDecimal getAmountRepayment() {
        return amountRepayment;
    }

    public BigDecimal getAmountInterest() {
        return amountInterest;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    public void setAmountCredit(BigDecimal amountCredit) {
        this.amountCredit = amountCredit;
    }

    public void setDatePayment(Date datePayment) {
        this.datePayment = datePayment;
    }

    public void setAmountPayment(BigDecimal amountPayment) {
        this.amountPayment = amountPayment;
    }

    public void setAmountRepayment(BigDecimal amountRepayment) {
        this.amountRepayment = amountRepayment;
    }

    public void setAmountInterest(BigDecimal amountInterest) {
        this.amountInterest = amountInterest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreditOffer)) return false;

        CreditOffer that = (CreditOffer) o;

        if (getClient() != null ? !getClient().equals(that.getClient()) : that.getClient() != null) return false;
        if (getCredit() != null ? !getCredit().equals(that.getCredit()) : that.getCredit() != null) return false;
        if (getAmountCredit() != null ? !getAmountCredit().equals(that.getAmountCredit()) : that.getAmountCredit() != null)
            return false;
        if (getDatePayment() != null ? !getDatePayment().equals(that.getDatePayment()) : that.getDatePayment() != null)
            return false;
        if (getAmountPayment() != null ? !getAmountPayment().equals(that.getAmountPayment()) : that.getAmountPayment() != null)
            return false;
        if (getAmountRepayment() != null ? !getAmountRepayment().equals(that.getAmountRepayment()) : that.getAmountRepayment() != null)
            return false;
        return getAmountInterest() != null ? getAmountInterest().equals(that.getAmountInterest()) : that.getAmountInterest() == null;
    }

    @Override
    public int hashCode() {
        int result = getClient() != null ? getClient().hashCode() : 0;
        result = 31 * result + (getCredit() != null ? getCredit().hashCode() : 0);
        result = 31 * result + (getAmountCredit() != null ? getAmountCredit().hashCode() : 0);
        result = 31 * result + (getDatePayment() != null ? getDatePayment().hashCode() : 0);
        result = 31 * result + (getAmountPayment() != null ? getAmountPayment().hashCode() : 0);
        result = 31 * result + (getAmountRepayment() != null ? getAmountRepayment().hashCode() : 0);
        result = 31 * result + (getAmountInterest() != null ? getAmountInterest().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Кредитное предложение" +
                "клиент" + client +
                ", кредит " + credit +
                ", сумма кредита " + amountCredit +
                ", дата платежа " + datePayment +
                ", сумма платежа " + amountPayment +
                ", сумма гашения тела кредита " + amountRepayment +
                ", сумма гашения процентов " + amountInterest;
    }

}
