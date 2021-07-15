package com.haulmont.model;

import com.haulmont.model.service.daoService.ClientService;
import com.haulmont.model.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.ParseException;

@Service
public class CreateBD {
    @Autowired
    private ClientService clientService;

    private boolean createdBD;

    public void createBD() throws ParseException {
        if (!(createdBD)) {
            createEntitlement();
        }
    }

    private void createEntitlement() throws ParseException{
        createdBD = true;
        Client client = new Client("3610 735600","Иванов","Иван",
                "Иванович", "89515600111","ivanov@mail.ru");
        Client client1 = new Client("3511 123111","Сидоров","Пётр",
                "Антонович", "89515234511","sidorov@mail.ru");
        clientService.update(client);
        clientService.update(client1);
    }
}
