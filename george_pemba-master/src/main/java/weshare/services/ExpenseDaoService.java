package weshare.services;

import weshare.model.Expense;
import weshare.model.PaymentRequest;
import weshare.model.Person;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface ExpenseDaoService {
    Collection<Expense> findExpensesForPerson(Person person);

    Expense save(Expense expense);

    Optional<Expense> get(UUID id);

    Collection<PaymentRequest> findPaymentRequestsSent(Person person);

    Collection<PaymentRequest> findPaymentRequestsReceived(Person person);
}
