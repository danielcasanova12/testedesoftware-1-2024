package br.edu.utfpr.bankapi.validations;

import br.edu.utfpr.bankapi.exception.WithoutBalanceException;
import br.edu.utfpr.bankapi.model.Account;
import br.edu.utfpr.bankapi.model.Transaction;
import br.edu.utfpr.bankapi.model.TransactionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AvailableBalanceValidationTest {

    @Autowired
    AvailableBalanceValidation availableBalanceValidation;

    @Test
    void validTransactionWithSufficientBalance() {
        var sourceAccount = new Account("Uncle Scrooge", 12345, 1000, 0);
        var transaction = new Transaction(sourceAccount, null, 500, TransactionType.TRANSFER);

        Assertions.assertDoesNotThrow(() -> availableBalanceValidation.validate(transaction));
    }

    @Test
    void validTransactionWithLimitAndBalance() {
        var sourceAccount = new Account("Uncle Scrooge", 12345, 1000, 500);
        var transaction = new Transaction(sourceAccount, null, 1200, TransactionType.TRANSFER);

        Assertions.assertDoesNotThrow(() -> availableBalanceValidation.validate(transaction));
    }

    @Test
    void validTransactionWithExactBalance() {
        var sourceAccount = new Account("Uncle Scrooge", 12345, 1000, 0);
        var transaction = new Transaction(sourceAccount, null, 1000, TransactionType.TRANSFER);

        Assertions.assertDoesNotThrow(() -> availableBalanceValidation.validate(transaction));
    }

    @Test
    void validTransactionWithExactBalanceAndLimit() {
        var sourceAccount = new Account("Uncle Scrooge", 12345, 1000, 500);
        var transaction = new Transaction(sourceAccount, null, 1500, TransactionType.TRANSFER);

        Assertions.assertDoesNotThrow(() -> availableBalanceValidation.validate(transaction));
    }

    @Test
    void throwExceptionWhenInsufficientBalance() {
        var sourceAccount = new Account("College Student", 12345, 1000, 0);
        var transaction = new Transaction(sourceAccount, null, 1500, TransactionType.TRANSFER);

        Assertions.assertThrows(WithoutBalanceException.class,
                () -> availableBalanceValidation.validate(transaction));
    }

    @Test
    void throwExceptionWhenInsufficientBalanceWithLimit() {
        var sourceAccount = new Account("College Student", 12345, 1000, 500);
        var transaction = new Transaction(sourceAccount, null, 1600, TransactionType.TRANSFER);

        Assertions.assertThrows(WithoutBalanceException.class,
                () -> availableBalanceValidation.validate(transaction));
    }

    @Test
    void throwExceptionWhenAmountExceedsBalanceByOne() {
        var sourceAccount = new Account("College Student", 12345, 1000, 0);
        var transaction = new Transaction(sourceAccount, null, 1001, TransactionType.TRANSFER);

        Assertions.assertThrows(WithoutBalanceException.class,
                () -> availableBalanceValidation.validate(transaction));
    }

    @Test
    void throwExceptionWhenAmountExceedsBalanceByOneWithLimit() {
        var sourceAccount = new Account("College Student", 12345, 1000, 500);
        var transaction = new Transaction(sourceAccount, null, 1501, TransactionType.TRANSFER);

        Assertions.assertThrows(WithoutBalanceException.class,
                () -> availableBalanceValidation.validate(transaction));
    }

    @Test
    void throwExceptionWhenSourceAccountIsNull() {
        var transaction = new Transaction(null, null, 10, TransactionType.TRANSFER);

        Assertions.assertThrows(NullPointerException.class,
                () -> availableBalanceValidation.validate(transaction));
    }
}
