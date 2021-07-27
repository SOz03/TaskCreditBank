package com.haulmont.views.edit.creditoffer;

import com.haulmont.model.entity.CreditOffer;
import com.haulmont.model.service.daoService.CreditOfferService;
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
public class CreditOfferDataProvider extends AbstractBackEndDataProvider<CreditOffer, CrudFilter> {

    private final CreditOfferService creditOfferService;
    private final List<CreditOffer> creditOffers;

    private Consumer<Long> sizeChangeListener;

    public CreditOfferDataProvider(@Autowired CreditOfferService creditOfferService) {
        this.creditOfferService = creditOfferService;
        this.creditOffers = creditOfferService.findAll();
    }

    @Override
    protected Stream<CreditOffer> fetchFromBackEnd(Query<CreditOffer, CrudFilter> query) {
        int offset = query.getOffset();
        int limit = query.getLimit();

        Stream<CreditOffer> stream = creditOffers.stream();

        if (query.getFilter().isPresent()) {
            stream = stream
                    .filter(predicate(query.getFilter().get()))
                    .sorted(comparator(query.getFilter().get()));
        }

        return stream.skip(offset).limit(limit);
    }

    @Override
    protected int sizeInBackEnd(Query<CreditOffer, CrudFilter> query) {
        long count = fetchFromBackEnd(query).count();

        if (sizeChangeListener != null) {
            sizeChangeListener.accept(count);
        }

        return (int) count;
    }

    public void setSizeChangeListener(Consumer<Long> listener) {
        sizeChangeListener = listener;
    }

    private static Predicate<CreditOffer> predicate(CrudFilter filter) {
        return filter.getConstraints().entrySet().stream()
                .map(constraint -> (Predicate<CreditOffer>) creditOffer -> {
                    try {
                        Object value = valueOf(constraint.getKey(), creditOffer);
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

    private static Comparator<CreditOffer> comparator(CrudFilter filter) {
        return filter.getSortOrders().entrySet().stream()
                .map(sortClause -> {
                    try {
                        Comparator<CreditOffer> comparator
                                = Comparator.comparing(creditOffer ->
                                (Comparable) valueOf(sortClause.getKey(), creditOffer));

                        if (sortClause.getValue() == SortDirection.DESCENDING) {
                            comparator = comparator.reversed();
                        }

                        return comparator;
                    } catch (Exception ex) {
                        return (Comparator<CreditOffer>) (o1, o2) -> 0;
                    }
                })
                .reduce(Comparator::thenComparing)
                .orElse((o1, o2) -> 0);
    }

    private static Object valueOf(String fieldName, CreditOffer creditOffer) {
        try {
            Field field = CreditOffer.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(creditOffer);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void persist(CreditOffer creditOffer) {
        if (creditOffer.getIdCreditOffer() == null) {
            creditOffer.setIdCreditOffer(UUID.randomUUID().toString());
        }

        final Optional<CreditOffer> existingItem = find(creditOffer.getIdCreditOffer());
        if (existingItem.isPresent()) {
            int position = creditOffers.indexOf(existingItem.get());

            existingItem.get().setIdCreditOffer(creditOffer.getIdCreditOffer());
            existingItem.get().setAmountCredit(creditOffer.getAmountCredit());
            existingItem.get().setBank(creditOffer.getBank());
            existingItem.get().setClient(creditOffer.getClient());
            existingItem.get().setCredit(creditOffer.getCredit());
            existingItem.get().setPaymentGraphList(creditOffer.getPaymentGraphList());

            creditOffers.set(position, existingItem.get());
            creditOfferService.update(existingItem.get());

        } else {
            creditOffers.add(creditOffer);
            creditOfferService.update(creditOffer);
        }
    }

    Optional<CreditOffer> find(String id) {
        return creditOffers
                .stream()
                .filter(entity -> entity.getIdCreditOffer().equals(id))
                .findFirst();
    }

    public void delete(CreditOffer item) {
        if ((creditOffers.removeIf(creditOffer -> creditOffer.getIdCreditOffer().equals(item.getIdCreditOffer())))) {
            creditOffers.removeIf(entity -> entity.getIdCreditOffer().equals(item.getIdCreditOffer()));
            creditOfferService.delete(item.getIdCreditOffer());
        }
    }

}

