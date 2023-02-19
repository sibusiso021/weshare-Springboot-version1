package weshare.server;

import weshare.controller.*;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;

public class Routes {
    public static final String LOGIN_PAGE = "/";
    public static final String LOGIN_ACTION = "/login.action";
    public static final String LOGOUT = "/logout";
    public static final String EXPENSES = "/expenses";

    public static final String NEW_EXPENSE = "/newexpense";
    public static final String ADD_NEW_EXPENSE = "/expense.action";

    public static final String PAYMENT_REQUEST = "/paymentrequest";

    public static final String ADD_PAYMENT_REQUEST = "/paymentrequest.action";

    public static final String make_payment_REQUEST = "/payment.action";

    public static final String PAYMENTREQUEST_SENT = "/paymentrequests_sent";

    public static final String PAYMENTREQUEST_RECEIVED = "/paymentrequests_received";


    public static void configure(WeShareServer server) {
        server.routes(() -> {
            post(LOGIN_ACTION,  PersonController.login);
            get(LOGOUT,         PersonController.logout);
            get(EXPENSES,           ExpensesController.view);

            get(NEW_EXPENSE,    NewExpansesController.view);
            post(ADD_NEW_EXPENSE,   ExpensesController.addNewExpense);
            get(PAYMENT_REQUEST, PaymentRequests_controller.view);
            post(ADD_PAYMENT_REQUEST,PaymentRequests_controller.addPayment);

            get(PAYMENTREQUEST_SENT,    RequestSentController.view);
            get(PAYMENTREQUEST_RECEIVED,    RequestReceivedController.view);

            get(make_payment_REQUEST, RequestReceivedController.view);
            post(make_payment_REQUEST,RequestReceivedController.makePayment);


        });

    }
}
