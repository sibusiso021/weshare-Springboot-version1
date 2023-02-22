package weshare.delegation;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import weshare.model.Expense;
import weshare.model.Person;
//import weshare.server.WeShareServer;
import weshare.services.ExpenseDaoService;
import weshare.services.PersonService;

import java.time.LocalDate;

import static weshare.model.DateHelper.DD_MM_YYYY;
import static weshare.model.MoneyHelper.amountOf;

@Component
public class SaveExpenseDelegate implements JavaDelegate {
    @Autowired
    private ExpenseDaoService expenseDAO;

    @Autowired
    private PersonService personService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        // Get variables from the execution
        String date = (String) execution.getVariable("date");
        int amount = (int) execution.getVariable("amount");
        String description = (String) execution.getVariable("description");
        String personId = (String) execution.getVariable("personId");
        String userEmail = (String) execution.getVariable("userEmail");
        Person personLoggedIn = personService.findPersonById(personId);

        // Create and save the new expense
        Expense expense = new Expense(personLoggedIn, description, amountOf(amount), LocalDate.parse(date, DD_MM_YYYY));
        expenseDAO.save(expense);
    }
}

