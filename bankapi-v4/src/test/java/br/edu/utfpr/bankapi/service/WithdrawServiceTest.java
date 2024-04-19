package br.edu.utfpr.bankapi.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.edu.utfpr.bankapi.dto.WithdrawDTO;
import br.edu.utfpr.bankapi.exception.NotFoundException;
import br.edu.utfpr.bankapi.model.Account;
import br.edu.utfpr.bankapi.model.Transaction;
import br.edu.utfpr.bankapi.model.TransactionType;
import br.edu.utfpr.bankapi.repository.TransactionRepository;
import br.edu.utfpr.bankapi.validations.AvailableAccountValidation;
import br.edu.utfpr.bankapi.validations.AvailableBalanceValidation;

@SpringBootTest
public class WithdrawServiceTest {

    @Autowired
    private WithdrawServiceTest service;

    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private AvailableAccountValidation availableAccountValidation;

    @MockBean
    private AvailableBalanceValidation availableBalanceValidation;

    @Captor
    private ArgumentCaptor<Transaction> transactionCaptor;

    /**
     * Teste para verificar a operação de saque
     * 
     * @throws NotFoundException
     */
    @Test
    void shouldWithdraw() throws NotFoundException {
        // ### ARRANGE ###
        double initialBalance = 1200.50;

        long sourceAccountNumber = 67890;

        long amount = 200;

        WithdrawDTO withdrawDTO = new WithdrawDTO(sourceAccountNumber, 200);

        Account sourceAccount = new Account("Jane Doe", 67890, initialBalance, 0);

        Transaction transaction = new Transaction(sourceAccount, sourceAccount, amount,
                TransactionType.WITHDRAW);

        // Mocking the behavior of available account validation
        BDDMockito.given(availableAccountValidation.validate(withdrawDTO.sourceAccountNumber()))
                .willReturn(sourceAccount);

        // Mocking the behavior of available balance validation (Should not throw an exception)
        BDDMockito.willDoNothing().given(availableBalanceValidation).validate(transaction);

        // ### ACT ###
        service.withdraw(withdrawDTO);

        // ### ASSERT ###
        // Verify if the transaction was saved
        BDDMockito.then(transactionRepository).should().save(transactionCaptor.capture());
        Transaction savedTransaction = transactionCaptor.getValue();

        // The source account should be the same in the transaction
        Assertions.assertEquals(sourceAccount, savedTransaction.getSourceAccount());
        // The withdrawal amount should be the same in the transaction
        Assertions.assertEquals(withdrawDTO.amount(), savedTransaction.getAmount());
        // The transaction type should be WITHDRAW
        Assertions.assertEquals(TransactionType.WITHDRAW,
                savedTransaction.getType());
        // The balance in the source account should be reduced by the transaction amount
        Assertions.assertEquals(initialBalance - withdrawDTO.amount(),
                savedTransaction.getSourceAccount().getBalance());
    }

private void withdraw(WithdrawDTO withdrawDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'withdraw'");
}
}
