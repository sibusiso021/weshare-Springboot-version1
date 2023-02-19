package weshare.controller;

import io.javalin.http.Handler;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import weshare.model.Expense;
import weshare.model.PaymentRequest;
import weshare.model.Person;
import weshare.persistence.ExpenseDAO;
import weshare.server.Routes;
import weshare.server.ServiceRegistry;

import javax.money.MonetaryAmount;
import java.time.LocalDate;
import java.util.*;

import static weshare.model.DateHelper.DD_MM_YYYY;
import static weshare.model.MoneyHelper.amountOf;

@RestController
@RequestMapping("payments")
public class PaymentRequests_controller {

        @GetMapping("/paymentrequests")

        public static final Handler view = context -> {
            ExpenseDAO expensesDAO = ServiceRegistry.lookup(ExpenseDAO.class);
            String[] id = context.queryString().split("=");

            Optional<Expense> paymentrequest_list = expensesDAO.get(UUID.fromString(id[1]));
            MonetaryAmount total = paymentrequest_list.get().totalAmountOfPaymentsRequested();
            Map<String, Object> viewModel = Map.of("paymentrequests", paymentrequest_list, "total", total);


            context.render("paymentrequests.html", viewModel);
        };

    public static final Handler addPayment = context -> {
        ExpenseDAO expensesDAO = ServiceRegistry.lookup(ExpenseDAO.class);
        String email = context.formParamAsClass("email", String.class).get();
        Person payback = new Person(email);
        LocalDate date = LocalDate.parse(context.formParamAsClass("due_date", String.class).get(), DD_MM_YYYY);
        int amount = context.formParamAsClass("amount", int.class).get();
        String id =  context.formParamAsClass("id", String.class).get();
        Optional<Expense> expense = expensesDAO.get(UUID.fromString(id));
        PaymentRequest request = expense.get().requestPayment(payback,amountOf(amount),date);

        context.redirect(Routes.PAYMENT_REQUEST + "?expenseId=" + id);


    };




}
