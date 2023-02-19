package weshare.controller;

import io.javalin.http.Handler;
import org.javamoney.moneta.function.MonetaryFunctions;
import org.jetbrains.annotations.NotNull;
import weshare.model.*;
import weshare.persistence.ExpenseDAO;
import weshare.server.Routes;
import weshare.server.ServiceRegistry;
import weshare.server.WeShareServer;

import javax.money.MonetaryAmount;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import static weshare.model.MoneyHelper.amountOf;

public class RequestReceivedController {

    public static final Handler view = context -> {

        ExpenseDAO expensesDAO = ServiceRegistry.lookup(ExpenseDAO.class);
        Person personLoggedIn = WeShareServer.getPersonLoggedIn(context);

        Collection<PaymentRequest> receivedPaymentRequests = expensesDAO.findPaymentRequestsReceived(personLoggedIn);

        MonetaryAmount total = amountOf(0);
        for (PaymentRequest paymentRequest: receivedPaymentRequests){
            if(!paymentRequest.isPaid()){
                total = total.add(paymentRequest.getAmountToPay());

            }

        }


        Map<String, Object> viewModel = Map.of("receivedRequests", receivedPaymentRequests, "total", total);
        context.render("paymentrequests_received.html", viewModel);
    };
    public static final Handler makePayment = context ->{

        ExpenseDAO expensesDAO = ServiceRegistry.lookup(ExpenseDAO.class);

        Person personLoggedIn = WeShareServer.getPersonLoggedIn(context);
      Collection<PaymentRequest> receivedPaymentRequests = expensesDAO.findPaymentRequestsReceived(personLoggedIn);

        UUID expenseId = UUID.fromString(context.formParam("expenseId"));
        PaymentRequest paymentexpense = null;
        for(PaymentRequest paymentRequest: receivedPaymentRequests){
            if(paymentRequest.getId().equals(expenseId)){
                paymentexpense = paymentRequest;

            }

        }
        paymentexpense.pay(personLoggedIn , DateHelper.TODAY);

        Expense expensave= new Expense(personLoggedIn, paymentexpense.getDescription(), paymentexpense.getAmountToPay(), DateHelper.TODAY);
        expensesDAO.save(expensave);

        context.redirect("/paymentrequests_received");


    };

}
