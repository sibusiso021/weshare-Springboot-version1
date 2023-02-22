package weshare.controller;

import io.javalin.http.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Autowired
    private ExpenseDAO expenseDAO;

    @GetMapping("/sentRequest")
    public String viewSentRequest(Model model){
        Person personLoggedIn = WeShareServer.getPersonLoggedIn(context);

        Collection<PaymentRequest> sentPaymentRequests = expensesDAO.findPaymentRequestsSent(personLoggedIn);
        MonetaryAmount total = amountOf(0);
        for (PaymentRequest paymentRequest: sentPaymentRequests) {
            total = total.add(paymentRequest.getAmountToPay());
        }
        model.addAttribute("sentRequests", sentPaymentRequests);
        model.addAttribute("total", total);

        return  "paymentrequest_sent";
    }

}
