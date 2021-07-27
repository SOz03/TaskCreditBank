package com.haulmont.views.edit.paymentgraph;

import com.haulmont.model.entity.PaymentGraph;
import com.haulmont.model.service.daoService.PaymentGraphService;
import com.vaadin.flow.component.crud.CrudFilter;
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Service
public class PaymentGraphDataProvider extends AbstractBackEndDataProvider<PaymentGraph, CrudFilter> {

    private final PaymentGraphService paymentGraphService;
    private final List<PaymentGraph> paymentGraphs;

    private Consumer<Long> sizeChangeListener;

    public PaymentGraphDataProvider(@Autowired PaymentGraphService paymentGraphService) {
        this.paymentGraphService = paymentGraphService;
        this.paymentGraphs = paymentGraphService.findAll();
    }

    @Override
    protected Stream<PaymentGraph> fetchFromBackEnd(Query<PaymentGraph, CrudFilter> query) {
        int offset = query.getOffset();
        int limit = query.getLimit();

        Stream<PaymentGraph> stream = paymentGraphs.stream();

        if (query.getFilter().isPresent()) {
            stream = stream
                    .filter(predicate(query.getFilter().get()))
                    .sorted(comparator(query.getFilter().get()));
        }

        return stream.skip(offset).limit(limit);
    }

    @Override
    protected int sizeInBackEnd(Query<PaymentGraph, CrudFilter> query) {
        long count = fetchFromBackEnd(query).count();

        if (sizeChangeListener != null) {
            sizeChangeListener.accept(count);
        }

        return (int) count;
    }

    public void setSizeChangeListener(Consumer<Long> listener) {
        sizeChangeListener = listener;
    }

    private static Predicate<PaymentGraph> predicate(CrudFilter filter) {
        return filter.getConstraints().entrySet().stream()
                .map(constraint -> (Predicate<PaymentGraph>) paymentGraph -> {
                    try {
                        Object value = valueOf(constraint.getKey(), paymentGraph);
                        return value != null && value.toString().toLowerCase()
                                .contains(constraint.getValue().toLowerCase());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                })
                .reduce(Predicate::and)
                .orElse(e -> true);
    }

    private static Comparator<PaymentGraph> comparator(CrudFilter filter) {
        return filter.getSortOrders().entrySet().stream()
                .map(sortClause -> {
                    try {
                        Comparator<PaymentGraph> comparator
                                = Comparator.comparing(paymentGraph ->
                                (Comparable) valueOf(sortClause.getKey(), paymentGraph));

                        if (sortClause.getValue() == SortDirection.DESCENDING) {
                            comparator = comparator.reversed();
                        }

                        return comparator;
                    } catch (Exception ex) {
                        return (Comparator<PaymentGraph>) (o1, o2) -> 0;
                    }
                })
                .reduce(Comparator::thenComparing)
                .orElse((o1, o2) -> 0);
    }

    private static Object valueOf(String fieldName, PaymentGraph paymentGraph) {
        try {
            Field field = PaymentGraph.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(paymentGraph);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void persist(PaymentGraph paymentGraph) {
        if (paymentGraph.getIdPaymentGraph() == null) {
            paymentGraph.setIdPaymentGraph(UUID.randomUUID().toString());
        }

        final Optional<PaymentGraph> existingItem = find(paymentGraph.getIdPaymentGraph());
        if (existingItem.isPresent()) {
            int position = paymentGraphs.indexOf(existingItem.get());

            existingItem.get().setIdPaymentGraph(paymentGraph.getIdPaymentGraph());
            existingItem.get().setAmountInterest(paymentGraph.getAmountInterest());
            existingItem.get().setAmountPayment(paymentGraph.getAmountPayment());
            existingItem.get().setAmountRepayment(paymentGraph.getAmountRepayment());
            existingItem.get().setCreditOffer(paymentGraph.getCreditOffer());
            existingItem.get().setDatePayment(paymentGraph.getDatePayment());

            paymentGraphs.set(position, existingItem.get());
            paymentGraphService.update(existingItem.get());

        } else {
            paymentGraphs.add(paymentGraph);
            paymentGraphService.update(paymentGraph);
        }
    }

    Optional<PaymentGraph> find(String id) {
        return paymentGraphs
                .stream()
                .filter(entity -> entity.getIdPaymentGraph().equals(id))
                .findFirst();
    }

    public void delete(PaymentGraph item) {
        if ((paymentGraphs.removeIf(paymentGraph -> paymentGraph.getIdPaymentGraph().equals(item.getIdPaymentGraph())))) {
            paymentGraphs.removeIf(entity -> entity.getIdPaymentGraph().equals(item.getIdPaymentGraph()));
            paymentGraphService.delete(item.getIdPaymentGraph());
        }
    }

}