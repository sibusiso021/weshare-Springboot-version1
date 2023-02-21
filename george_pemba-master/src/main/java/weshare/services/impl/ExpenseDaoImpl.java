package weshare.services.impl;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import weshare.model.Expense;
import weshare.model.PaymentRequest;
import weshare.model.Person;
import weshare.services.ExpenseDaoService;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


public class ExpenseDaoImpl implements ExpenseDaoService {
    private final Map<UUID, Expense> expenses;

    public ExpenseDaoImpl() {
        expenses = new ConcurrentHashMap<>();
    }

    public ExpenseDaoImpl(Map<UUID, Expense> expenses) {
        this.expenses = expenses;
    }

    @Override
    public Collection<Expense> findExpensesForPerson(Person person) {
        return expenses.values().stream()
                .filter(e -> e.getPerson().equals(person))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Expense save(Expense expense) {
        expenses.put(expense.getId(), expense);
        return expense;
    }

    @Override
    public Optional<Expense> get(UUID id) {
        return expenses.containsKey(id) ? Optional.of(expenses.get(id)) : Optional.empty();
    }

    @Override
    public Collection<PaymentRequest> findPaymentRequestsSent(Person person) {
        return findExpensesForPerson(person).stream()
                .map(Expense::listOfPaymentRequests)
                .flatMap(Collection::stream)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Collection<PaymentRequest> findPaymentRequestsReceived(Person person) {
        return expenses.values().stream()
                .map(Expense::listOfPaymentRequests)
                .flatMap(Collection::stream)
                .filter(pr -> pr.getPersonWhoShouldPayBack().equals(person))
                .collect(Collectors.toUnmodifiableList());
    }
}
