package com.haulmont.views.menu;

import com.haulmont.model.service.CreateDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class DBService implements Serializable {
    @Autowired
    private CreateDB createDB;

    public void setCreateDB() {
        createDB.createDB();
    }
}
