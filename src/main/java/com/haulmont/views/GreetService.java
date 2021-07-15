package com.haulmont.views;

import com.haulmont.model.CreateBD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.text.ParseException;

@Service
public class GreetService implements Serializable {

    @Autowired
    private CreateBD createBD;

    public void createBD() throws ParseException {
        createBD.createBD();
    }

}