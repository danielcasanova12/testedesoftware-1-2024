package br.edu.utfpr.bankapi.validations;

import org.springframework.stereotype.Component;

import br.edu.utfpr.bankapi.exception.WithoutBalanceException;
import br.edu.utfpr.bankapi.model.Transaction;

/**
 * Validar se existe saldo em conta disponível
 */
@Component
public class AvailableBalanceValidation {

    public void validate(Transaction transaction) {
        // Verifica se a conta de origem possui saldo
        if (transaction.getSourceAccount().getBalanceWithLimit() < transaction.getAmount()) {
            throw new WithoutBalanceException();
        }
    }

}
