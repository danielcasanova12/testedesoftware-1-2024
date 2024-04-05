package br.edu.utfpr.bankapi.service;

import java.util.Optional;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.edu.utfpr.bankapi.dto.DepositDTO;
import br.edu.utfpr.bankapi.model.Account;
import br.edu.utfpr.bankapi.model.Transaction;
import br.edu.utfpr.bankapi.repository.AccountRepository;
import br.edu.utfpr.bankapi.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
public class DepositTeste {
    @InjectMocks
    TransactionService transactionService;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private AccountRepository accountRepository;

    @Captor
    ArgumentCaptor<Transaction> Transactioncaptor;

    @Test
    public void deveriaExistirAcontaDeDestino() throws Exception {

        // arrange
        long Number = 12345;
        double amount = 100.0;
        var dto = new DepositDTO(Number, amount);

        var receiverAccount = new Account("juc", 12345, 0, 0);

        BDDMockito.given(accountRepository.getByNumber(Number)).willReturn(Optional.of(receiverAccount));

        // act

        transactionService.deposit(dto);

        // assert
        BDDMockito.then(transactionRepository).should().save(Transactioncaptor.capture());

        BDDMockito.then(accountRepository).should().getByNumber(Number);

    }

}
