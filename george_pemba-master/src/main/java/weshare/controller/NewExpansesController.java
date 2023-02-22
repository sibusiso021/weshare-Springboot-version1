//package weshare.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import weshare.model.Expense;
//import weshare.model.Person;
//import weshare.server.ServiceRegistry;
//import weshare.server.WeShareServer;
//import weshare.services.ExpenseDaoService;
//
//import java.util.Collection;
//
//@RestController
//@RequestMapping("/expenses")
//public class NewExpansesController {
//    @Autowired
//    private final ExpenseDaoService expenseDAO;
//
//    public NewExpansesController(ExpenseDaoService expenseDAO) {
//        this.expenseDAO = expenseDAO;
//    }
//
//    @GetMapping("/expenses")
//    public String view(Model model){
//        ExpenseDaoService expensesDAO = ServiceRegistry.lookup(ExpenseDaoService.class);
//        Person personLoggedIn = WeShareServer.getPersonLoggedIn();
//
//        Collection<Expense> expenses = expenseDAO.findExpensesForPerson(personLoggedIn);
//
//        model.addAttribute("newexpenses", expenses);
//
//        return "new_expenses";
//    };
//}
