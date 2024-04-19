package br.edu.utfpr.bankapi.validations;

import org.springframework.stereotype.Component;

import br.edu.utfpr.bankapi.exception.WithoutBalanceException;
import br.edu.utfpr.bankapi.model.Transaction;

/**
 * Validating the availability of balance in the account
 */
@Component
public class AvailableBalanceValidation {

    public void validate(Transaction transaction) {
        // Checking if the source account has sufficient balance
        if (transaction.getSourceAccount().getBalanceWithLimit() < transaction.getAmount()) {
            throw new WithoutBalanceException();
        }
    }
}