package weshare.controller;

import io.javalin.http.Handler;
import weshare.model.MoneyHelper;
import weshare.model.PaymentRequest;
import weshare.model.Person;
import weshare.persistence.ExpenseDAO;
import weshare.server.ServiceRegistry;
import weshare.server.WeShareServer;
import javax.money.MonetaryAmount;
import java.util.Collection;
import java.util.Map;

import static weshare.model.MoneyHelper.amountOf;

public class RequestSentController {

    public static final Handler view = context -> {
        ExpenseDAO expensesDAO = ServiceRegistry.lookup(ExpenseDAO.class);
        Person personLoggedIn = WeShareServer.getPersonLoggedIn(context);

        Collection<PaymentRequest> sentPaymentRequests = expensesDAO.findPaymentRequestsSent(personLoggedIn);

        MonetaryAmount total = amountOf(0);
        for (PaymentRequest paymentRequest: sentPaymentRequests) {
            total = total.add(paymentRequest.getAmountToPay());
        }

        Map<String, Object> viewModel = Map.of("sentRequests", sentPaymentRequests, "total", total);
        context.render("paymentrequests_sent.html", viewModel);
    };
}
