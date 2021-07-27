package com.haulmont.model.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Setter
@Getter
@Table(/*name = "credits"*/)
public class Credit {
    @Id
//    @Column(name = "id_credit")
    private String idCredit;
//    @Column(name = "credit_limit")
    private BigDecimal creditLimit;
//    @Column(name = "interest_rate")
    private BigDecimal interestRate;

    public Credit(BigDecimal creditLimit, BigDecimal interestRate) {
        this.idCredit = UUID.randomUUID().toString();
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }

    public Credit(String idCredit, BigDecimal creditLimit, BigDecimal interestRate) {
        this.idCredit = idCredit;
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }

    public Credit() {

    }

    public String getIdCredit() {
        return idCredit;
    }

    public void setIdCredit(String idCredit) {
        this.idCredit = idCredit;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Credit)) return false;

        Credit credit = (Credit) o;

        if (!Objects.equals(idCredit, credit.idCredit)) return false;
        if (getCreditLimit() != null ? !getCreditLimit().equals(credit.getCreditLimit()) : credit.getCreditLimit() != null)
            return false;
        return getInterestRate() != null ? getInterestRate().equals(credit.getInterestRate()) : credit.getInterestRate() == null;
    }

    @Override
    public int hashCode() {
        int result = getCreditLimit() != null ? getCreditLimit().hashCode() : 0;
        result = 31 * result + (getInterestRate() != null ? getInterestRate().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "лимит " + creditLimit;
    }
}
