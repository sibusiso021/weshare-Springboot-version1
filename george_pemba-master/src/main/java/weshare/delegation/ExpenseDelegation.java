package weshare.delegation;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import weshare.services.ExpenseDaoService;
import weshare.services.impl.ExpenseDaoImpl;

public class ExpenseDelegation implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {


    }
}
