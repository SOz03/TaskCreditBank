package com.haulmont.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "payment_graph")
public class PaymentGraph {
    @Id
    @Column(name = "id_payment_graph")
    private String idPaymentGraph;
    @Column(name = "date_payment")
    private Date datePayment;
    @Column(name = "amount_payment")
    private BigDecimal amountPayment;
    @Column(name = "amount_repayment")
    private BigDecimal amountRepayment;
    @Column(name = "amount_interest")
    private BigDecimal amountInterest;
    @ManyToOne
    @JoinColumn(name = "id_credit_offer")
    private CreditOffer creditOffer;

    public PaymentGraph(){}

    public PaymentGraph(Date datePayment, BigDecimal amountPayment,
                        BigDecimal amountRepayment, BigDecimal amountInterest,
                        CreditOffer creditOffer) {
        this.idPaymentGraph = UUID.randomUUID().toString();
        this.datePayment = datePayment;
        this.amountPayment = amountPayment;
        this.amountRepayment = amountRepayment;
        this.amountInterest = amountInterest;
        this.creditOffer = creditOffer;
    }

    public String getIdPaymentGraph() {
        return idPaymentGraph;
    }

    public void setIdPaymentGraph(String idPaymentGraph) {
        this.idPaymentGraph = idPaymentGraph;
    }

    public Date getDatePayment() {
        return datePayment;
    }

    public void setDatePayment(Date datePayment) {
        this.datePayment = datePayment;
    }

    public BigDecimal getAmountPayment() {
        return amountPayment;
    }

    public void setAmountPayment(BigDecimal amountPayment) {
        this.amountPayment = amountPayment;
    }

    public BigDecimal getAmountRepayment() {
        return amountRepayment;
    }

    public void setAmountRepayment(BigDecimal amountRepayment) {
        this.amountRepayment = amountRepayment;
    }

    public BigDecimal getAmountInterest() {
        return amountInterest;
    }

    public void setAmountInterest(BigDecimal amountInterest) {
        this.amountInterest = amountInterest;
    }

    public CreditOffer getCreditOffer() {
        return creditOffer;
    }

    public void setCreditOffer(CreditOffer creditOffer) {
        this.creditOffer = creditOffer;
    }

    @Override
    public String toString() {
        return "PaymentGraph{" +
                "idPaymentGraph='" + idPaymentGraph + '\'' +
                ", datePayment=" + datePayment +
                ", amountPayment=" + amountPayment +
                ", amountRepayment=" + amountRepayment +
                ", amountInterest=" + amountInterest +
                '}';
    }
}
