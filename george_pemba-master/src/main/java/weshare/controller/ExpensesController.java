package weshare.controller;

import io.javalin.http.Handler;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.function.MonetaryFunctions;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import weshare.model.Expense;
import weshare.model.Person;
import weshare.persistence.ExpenseDAO;
import weshare.server.Routes;
import weshare.server.ServiceRegistry;
import weshare.server.WeShareServer;

import javax.money.MonetaryAmount;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static weshare.model.DateHelper.DD_MM_YYYY;
import static weshare.model.DateHelper.TODAY;
import static weshare.model.MoneyHelper.ZERO_RANDS;
import static weshare.model.MoneyHelper.amountOf;
import org.springframework.stereotype.Controller;
//uses the restcontroller annotation to mark or set a default setting for all the methods in the class a request handler methods
// meaning Every request handling method of the controller class automatically serializes return objects into HttpResponse.
@RestController
@RequestMapping("expenses")
public class ExpensesController {



    @GetMapping("/expenses")
    public static final Handler view = context -> {
        ExpenseDAO expensesDAO = ServiceRegistry.lookup(ExpenseDAO.class);
        Person personLoggedIn = WeShareServer.getPersonLoggedIn(context);

        Collection<Expense> expenses = expensesDAO.findExpensesForPerson(personLoggedIn);
        int total = 0;
        for (Expense expense: expenses
        ) {
            total += expense.totalAmountAvailableForPaymentRequests().getNumber().intValue();

        }
        Map<String, Object> viewModel = Map.of("expenses", expenses, "nettTotal", amountOf(total));
        context.render("expenses.html", viewModel);

    };

    @PostMapping("/expenses/add")

    public static final Handler addNewExpense = context -> {
        ExpenseDAO expensesDAO = ServiceRegistry.lookup(ExpenseDAO.class);
        Person personLoggedIn = WeShareServer.getPersonLoggedIn(context);

        String description = context.formParamAsClass("description", String.class).get();
        LocalDate date = LocalDate.parse(context.formParamAsClass("date", String.class).get(), DD_MM_YYYY);
        int amount = context.formParamAsClass("amount", int.class).get();
        Expense expense = new Expense(personLoggedIn, description, amountOf(amount), date);
        expensesDAO.save(expense);
        context.redirect(Routes.EXPENSES);
    };
}
