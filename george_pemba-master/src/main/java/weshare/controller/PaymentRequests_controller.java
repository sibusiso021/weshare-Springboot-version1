package weshare.controller;

import io.javalin.http.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import weshare.model.Expense;
import weshare.model.PaymentRequest;
import weshare.model.Person;
import weshare.persistence.ExpenseDAO;
import weshare.server.Routes;
import weshare.server.ServiceRegistry;
import weshare.services.ExpenseDaoService;

import javax.money.MonetaryAmount;
import java.time.LocalDate;
import java.util.*;

import static weshare.model.DateHelper.DD_MM_YYYY;
import static weshare.model.MoneyHelper.amountOf;


@RestController
@RequestMapping("/paymentRequest")
public class PaymentRequests_controller {

    @Autowired
    private ExpenseDaoService expenseDAO;
        @GetMapping("/{id}")
        public Map<String, Object> viewPaymentRequest(@PathVariable UUID id){
            Expense expense = expenseDAO.get(id).orElseThrow();
            return Map.of("paymentRequest", expense.listOfPaymentRequests(), "total", expense.totalAmountOfPaymentsRequested()
        }

    @PutMapping("/{id}/addpayment")
    public void addPaymentRequest(@PathVariable
    UUID id, @RequestParam String email, @RequestParam String dueDate, @RequestParam int amount){
        //have to fix this
        Expense expense = expenseDAO.get(id).orElseThrow;

        Person payback = new Person(email);
        LocalDate date = LocalDate.parse(dueDate, DD_MM_YYYY);
        PaymentRequest request = expense.RequestPayment(payback, amountOf(amount), date);

    };




}
