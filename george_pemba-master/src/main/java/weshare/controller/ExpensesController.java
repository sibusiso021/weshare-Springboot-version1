package weshare.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import weshare.model.Expense;
import weshare.model.Person;

//import weshare.server.WeShareServer;
import weshare.services.ExpenseDaoService;
import weshare.services.PersonService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

import static weshare.model.DateHelper.DD_MM_YYYY;
import static weshare.model.MoneyHelper.amountOf;
//uses the restcontroller annotation to mark or set a default setting for all the methods in the class a request handler methods
// meaning Every request handling method of the controller class automatically serializes return objects into HttpResponse.
// this annotation just like spring mvc controller allows me to handle incoming http requests

@RestController
@RequestMapping("expenses")
public class ExpensesController {

    private final ExpenseDaoService expenseDAO;
    private final PersonService personService;

    @Autowired
    public ExpensesController(ExpenseDaoService expenseDAO, PersonService personService) {
        this.expenseDAO = expenseDAO;
        this.personService = personService;
    }
    //you were about to do a view method that will handle the getmapping request
    //In the paramerters of the view method the Model object is part of the mvc architecuture will use it to pass data to be used
    // from the controller to the view

    @GetMapping("/expenses")
    public String view(Model model){
        String personId = "123"; // Get the person ID from Camunda
        ;
        Person personLoggedIn = personService.findPersonById(personId);
        Collection<Expense> expenses = expenseDAO.findExpensesForPerson(personLoggedIn);
        int total = expenses.stream().mapToInt(expense -> expense.totalAmountAvailableForPaymentRequests().getNumber().intValue()).sum();
        model.addAttribute("expenses", expenses);
        model.addAttribute("nettTotal", amountOf(total));
        return "expenses";
    };

    @PostMapping("/expenses/add")
    public String addNewExpense(@RequestParam("date") String date, @RequestParam("amount") int amount, @RequestParam("description") String description) {
        String personId = "123";
        Person personLoggedIn = personService.findPersonById(personId);
        Expense expense = new Expense(personLoggedIn, description, amountOf(amount), LocalDate.parse(date, DD_MM_YYYY));
        expenseDAO.save(expense);
        return "expenses";
    };




    }








