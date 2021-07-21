package com.haulmont.model.dao;

import com.haulmont.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientDAO extends JpaRepository<Client, String> {
    @Modifying
    @Query("UPDATE Client client " +
            "set client.numberPassport =:numberPassport, " +
                "client.firstName =:firstName, " +
                "client.lastName =:lastName, " +
                "client.middleName =:middleName, " +
                "client.phoneNumber =:phoneNumber, " +
                "client.mail =:mail, " +
                "client.bank =:bank " +
            "where client.idClient =:idClient")
    void updateUserById(@Param("idClient") String idClient,
                        @Param("numberPassport") String numberPassport,
                        @Param("firstName") String firstName,
                        @Param("lastName") String lastName,
                        @Param("middleName") String middleName,
                        @Param("phoneNumber") String phoneNumber,
                        @Param("mail") String mail,
                        @Param("bank") String bank);
}
