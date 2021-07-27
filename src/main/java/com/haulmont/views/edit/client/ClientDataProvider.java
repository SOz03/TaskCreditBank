package com.haulmont.views.edit.client;

import com.haulmont.model.entity.Client;
import com.haulmont.model.service.daoService.ClientService;
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
public class ClientDataProvider extends AbstractBackEndDataProvider<Client, CrudFilter> {

    private final ClientService clientService;
    private final List<Client> clients;

    private Consumer<Long> sizeChangeListener;

    public ClientDataProvider(@Autowired ClientService clientService) {
        this.clientService = clientService;
        this.clients = clientService.findAll();
    }

    @Override
    protected Stream<Client> fetchFromBackEnd(Query<Client, CrudFilter> query) {
        int offset = query.getOffset();
        int limit = query.getLimit();

        Stream<Client> stream = clients.stream();

        if (query.getFilter().isPresent()) {
            stream = stream
                    .filter(predicate(query.getFilter().get()))
                    .sorted(comparator(query.getFilter().get()));
        }

        return stream.skip(offset).limit(limit);
    }

    @Override
    protected int sizeInBackEnd(Query<Client, CrudFilter> query) {
        long count = fetchFromBackEnd(query).count();

        if (sizeChangeListener != null) {
            sizeChangeListener.accept(count);
        }

        return (int) count;
    }

    public void setSizeChangeListener(Consumer<Long> listener) {
        sizeChangeListener = listener;
    }

    private static Predicate<Client> predicate(CrudFilter filter) {
        return filter.getConstraints().entrySet().stream()
                .map(constraint -> (Predicate<Client>) client -> {
                    try {
                        Object value = valueOf(constraint.getKey(), client);
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

    private static Comparator<Client> comparator(CrudFilter filter) {
        return filter.getSortOrders().entrySet().stream()
                .map(sortClause -> {
                    try {
                        Comparator<Client> comparator
                                = Comparator.comparing(client ->
                                (Comparable) valueOf(sortClause.getKey(), client));

                        if (sortClause.getValue() == SortDirection.DESCENDING) {
                            comparator = comparator.reversed();
                        }

                        return comparator;
                    } catch (Exception ex) {
                        return (Comparator<Client>) (o1, o2) -> 0;
                    }
                })
                .reduce(Comparator::thenComparing)
                .orElse((o1, o2) -> 0);
    }

    private static Object valueOf(String fieldName, Client client) {
        try {
            Field field = Client.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(client);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void persist(Client client) {
        if (client.getIdClient() == null) {
            client.setIdClient(UUID.randomUUID().toString());
        }

        final Optional<Client> existingItem = find(client.getIdClient());
        if (existingItem.isPresent()) {
            int position = clients.indexOf(existingItem.get());

            existingItem.get().setIdClient(client.getIdClient());
            existingItem.get().setFirstName(client.getFirstName());
            existingItem.get().setLastName(client.getLastName());
            existingItem.get().setMiddleName(client.getMiddleName());
            existingItem.get().setNumberPassport(client.getNumberPassport());
            existingItem.get().setPhoneNumber(client.getPhoneNumber());
            existingItem.get().setMail(client.getMail());
            existingItem.get().setBank(client.getBank());

            clients.set(position, existingItem.get());
            clientService.update(existingItem.get());

        } else {
            clients.add(client);
            clientService.update(client);
        }
    }

    Optional<Client> find(String id) {
        return clients
                .stream()
                .filter(entity -> entity.getIdClient().equals(id))
                .findFirst();
    }

    public void delete(Client item) {
        if ((clients.removeIf(client -> client.getIdClient().equals(item.getIdClient())))){
            clients.removeIf(entity -> entity.getIdClient().equals(item.getIdClient()));
            clientService.delete(item.getIdClient());
        }
    }

}

