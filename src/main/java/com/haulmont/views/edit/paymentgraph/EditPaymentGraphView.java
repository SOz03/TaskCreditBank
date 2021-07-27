package com.haulmont.views.edit.paymentgraph;

import com.haulmont.model.entity.*;
import com.haulmont.views.menu.MainLayout;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@PageTitle("Изменение платежей")
@Route(value = "edit-payment-graph", layout = MainLayout.class)
public class EditPaymentGraphView extends Div {
    private final PaymentGraphDataProvider paymentGraphDataProvider;

    public EditPaymentGraphView(@Autowired PaymentGraphDataProvider paymentGraphDataProvider) {
        this.paymentGraphDataProvider = paymentGraphDataProvider;
        add(addCrudPG());
    }

    private CrudEditor<PaymentGraph> createPGEditor() {
        ComboBox<CreditOffer> creditBox = new ComboBox<>("Кредитное предложение");
        BigDecimalField payment = new BigDecimalField("Общая сумма");
        BigDecimalField repayment = new BigDecimalField("Сумма");
        BigDecimalField interest = new BigDecimalField("Сумма процентов");

        FormLayout form = new FormLayout(creditBox, payment, repayment, interest);

        Binder<PaymentGraph> binder = new Binder<>(PaymentGraph.class);
        binder.bind(creditBox, PaymentGraph::getCreditOffer, PaymentGraph::setCreditOffer);
        binder.bind(payment, PaymentGraph::getAmountPayment, PaymentGraph::setAmountPayment);
        binder.bind(repayment, PaymentGraph::getAmountRepayment, PaymentGraph::setAmountRepayment);
        binder.bind(interest, PaymentGraph::getAmountInterest, PaymentGraph::setAmountInterest);

        return new BinderCrudEditor<>(binder, form);
    }

    private Crud<PaymentGraph> addCrudPG() {
        Crud<PaymentGraph> crud = new Crud<>(PaymentGraph.class, createPGEditor());
        Span footer = new Span();
        footer.getElement().getStyle().set("flex", "1");

        crud.setToolbar(footer);

        paymentGraphDataProvider.setSizeChangeListener(count -> footer.setText("Всего платежей: " + count));

        crud.getGrid().removeColumnByKey("idPaymentGraph");
        crud.getGrid().removeColumnByKey("datePayment");
        crud.getGrid().removeColumnByKey("amountPayment");
        crud.getGrid().removeColumnByKey("amountRepayment");
        crud.getGrid().removeColumnByKey("amountInterest");
        crud.getGrid().removeColumnByKey("creditOffer");

        crud.getGrid().addColumn(PaymentGraph::getDatePayment).setHeader("Дата платежа");
        crud.getGrid().addColumn(PaymentGraph::getCreditOffer).setHeader("ID предложения");
        crud.getGrid().addColumn(PaymentGraph::getAmountPayment).setHeader("Общая сумма");
        crud.getGrid().addColumn(PaymentGraph::getAmountInterest).setHeader("Сумма процентов");
        crud.getGrid().addColumn(PaymentGraph::getAmountRepayment).setHeader("Сумма");

        crud.setDataProvider(paymentGraphDataProvider);

        crud.addSaveListener(e -> paymentGraphDataProvider.persist(e.getItem()));
        crud.addDeleteListener(e -> paymentGraphDataProvider.delete(e.getItem()));
        return crud;
    }
}
