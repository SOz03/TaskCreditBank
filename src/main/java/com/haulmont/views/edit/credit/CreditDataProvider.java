package com.haulmont.views.edit.credit;

import com.haulmont.model.entity.Client;
import com.haulmont.model.entity.Credit;
import com.haulmont.model.service.daoService.CreditService;
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
public class CreditDataProvider extends AbstractBackEndDataProvider<Credit, CrudFilter> {
    private final CreditService creditService;
    private final List<Credit> credits;

    private Consumer<Long> sizeChangeListener;

    public CreditDataProvider(@Autowired CreditService creditService) {
        this.creditService = creditService;
        this.credits = creditService.findAll();
    }

    @Override
    protected Stream<Credit> fetchFromBackEnd(Query<Credit, CrudFilter> query) {
        int offset = query.getOffset();
        int limit = query.getLimit();

        Stream<Credit> stream = credits.stream();

        if (query.getFilter().isPresent()) {
            stream = stream
                    .filter(predicate(query.getFilter().get()))
                    .sorted(comparator(query.getFilter().get()));
        }

        return stream.skip(offset).limit(limit);
    }

    @Override
    protected int sizeInBackEnd(Query<Credit, CrudFilter> query) {
        long count = fetchFromBackEnd(query).count();

        if (sizeChangeListener != null) {
            sizeChangeListener.accept(count);
        }

        return (int) count;
    }

    public void setSizeChangeListener(Consumer<Long> listener) {
        sizeChangeListener = listener;
    }

    private static Predicate<Credit> predicate(CrudFilter filter) {
        return filter.getConstraints().entrySet().stream()
                .map(constraint -> (Predicate<Credit>) credit -> {
                    try {
                        Object value = valueOf(constraint.getKey(), credit);
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

    private static Comparator<Credit> comparator(CrudFilter filter) {
        return filter.getSortOrders().entrySet().stream()
                .map(sortClause -> {
                    try {
                        Comparator<Credit> comparator
                                = Comparator.comparing(credit ->
                                (Comparable) valueOf(sortClause.getKey(), credit));

                        if (sortClause.getValue() == SortDirection.DESCENDING) {
                            comparator = comparator.reversed();
                        }

                        return comparator;
                    } catch (Exception ex) {
                        return (Comparator<Credit>) (o1, o2) -> 0;
                    }
                })
                .reduce(Comparator::thenComparing)
                .orElse((o1, o2) -> 0);
    }

    private static Object valueOf(String fieldName, Credit credit) {
        try {
            Field field = Credit.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(credit);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void persist(Credit credit) {
        if (credit.getIdCredit() == null) {
            credit.setIdCredit(UUID.randomUUID().toString());
        }

        final Optional<Credit> existingItem = find(credit.getIdCredit());
        if (existingItem.isPresent()) {
            int position = credits.indexOf(existingItem.get());

            existingItem.get().setIdCredit(credit.getIdCredit());
            existingItem.get().setCreditLimit(credit.getCreditLimit());
            existingItem.get().setInterestRate(credit.getInterestRate());

            credits.set(position, existingItem.get());
            creditService.update(existingItem.get());

        } else {
            credits.add(credit);
            creditService.update(credit);
        }
    }

    Optional<Credit> find(String id) {
        return credits
                .stream()
                .filter(entity -> entity.getIdCredit().equals(id))
                .findFirst();
    }

    public void delete(Credit item) {
        if ((credits.removeIf(client -> client.getIdCredit().equals(item.getIdCredit())))){
            credits.removeIf(entity -> entity.getIdCredit().equals(item.getIdCredit()));
            creditService.delete(item.getIdCredit());
        }
    }
}
