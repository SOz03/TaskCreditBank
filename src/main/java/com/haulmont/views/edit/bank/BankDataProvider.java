package com.haulmont.views.edit.bank;

import com.haulmont.model.entity.Bank;
import com.haulmont.model.service.daoService.BankService;
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
public class BankDataProvider extends AbstractBackEndDataProvider<Bank, CrudFilter> {

    private final BankService bankService;
    private final List<Bank> banks;
    private Consumer<Long> sizeChangeListener;

    public BankDataProvider(@Autowired BankService bankService) {
        this.bankService = bankService;
        this.banks = bankService.findAll();
    }

    @Override
    protected Stream<Bank> fetchFromBackEnd(Query<Bank, CrudFilter> query) {
        int offset = query.getOffset();
        int limit = query.getLimit();

        Stream<Bank> stream = banks.stream();

        if (query.getFilter().isPresent()) {
            stream = stream
                    .filter(predicate(query.getFilter().get()))
                    .sorted(comparator(query.getFilter().get()));
        }

        return stream.skip(offset).limit(limit);
    }

    @Override
    protected int sizeInBackEnd(Query<Bank, CrudFilter> query) {
        long count = fetchFromBackEnd(query).count();

        if (sizeChangeListener != null) {
            sizeChangeListener.accept(count);
        }

        return (int) count;
    }

    public void setSizeChangeListener(Consumer<Long> listener) {
        sizeChangeListener = listener;
    }

    private static Predicate<Bank> predicate(CrudFilter filter) {
        return filter.getConstraints().entrySet().stream()
                .map(constraint -> (Predicate<Bank>) bank -> {
                    try {
                        Object value = valueOf(constraint.getKey(), bank);
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

    private static Comparator<Bank> comparator(CrudFilter filter) {
        return filter.getSortOrders().entrySet().stream()
                .map(sortClause -> {
                    try {
                        Comparator<Bank> comparator
                                = Comparator.comparing(bank ->
                                (Comparable) valueOf(sortClause.getKey(), bank));

                        if (sortClause.getValue() == SortDirection.DESCENDING) {
                            comparator = comparator.reversed();
                        }

                        return comparator;
                    } catch (Exception ex) {
                        return (Comparator<Bank>) (o1, o2) -> 0;
                    }
                })
                .reduce(Comparator::thenComparing)
                .orElse((o1, o2) -> 0);
    }

    private static Object valueOf(String fieldName, Bank bank) {
        try {
            Field field = Bank.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(bank);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void persist(Bank item) {
        if (item.getIdBank() == null) {
            item.setIdBank(UUID.randomUUID().toString());
        }

        final Optional<Bank> existingItem = find(item.getIdBank());
        if (existingItem.isPresent()) {
            int position = banks.indexOf(existingItem.get());

            banks.remove(existingItem.get());
            banks.add(position, item);
            bankService.update(item);
        } else {
            banks.add(item);
            bankService.update(item);
        }
    }

    Optional<Bank> find(String id) {
        return banks
                .stream()
                .filter(entity -> entity.getIdBank().equals(id))
                .findFirst();
    }

    public void delete(Bank item) {
        if ((banks.removeIf(client -> client.getIdBank().equals(item.getIdBank())))) {
            banks.removeIf(entity -> entity.getIdBank().equals(item.getIdBank()));
            bankService.delete(item.getIdBank());
        }
    }
}
