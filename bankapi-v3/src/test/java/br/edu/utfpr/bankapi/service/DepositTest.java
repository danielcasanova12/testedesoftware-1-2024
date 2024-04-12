package br.edu.utfpr.bankapi.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.edu.utfpr.bankapi.dto.DepositDTO;
import br.edu.utfpr.bankapi.exception.NotFoundException;
import br.edu.utfpr.bankapi.model.Account;
import br.edu.utfpr.bankapi.model.Transaction;
import br.edu.utfpr.bankapi.model.TransactionType;
import br.edu.utfpr.bankapi.repository.AccountRepository;
import br.edu.utfpr.bankapi.repository.TransactionRepository;
import br.edu.utfpr.bankapi.validations.AvailableAccountValidation;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class DepositTest {

	@Mock
	AvailableAccountValidation availableAccountValidation;

	@Mock
	AccountRepository accountRepository;

	@Mock
	TransactionRepository transactionRepository;

	@InjectMocks
	TransactionService service;

	@Mock
	Transaction transaction;

	@Captor
	ArgumentCaptor<Transaction> transactionCaptor;

	DepositDTO depositDTO;

	@Mock
	Account sourceAccount;

	Account receiverAccount;

	/**
	 * @throws NotFoundException
	 * 
	 */
	@Test
	void deveriaDepositar() throws NotFoundException {
		// Garantir que as validações sejam executadas
		// Garantir que a transação foi salva

		// ### ARRANGE ###
		double saldoInicial = 150.85;

		depositDTO = new DepositDTO(12345, 1000);
		receiverAccount = new Account("John Smith", 12345, saldoInicial, 0);

		// Comportamento do availableAccountValidation
		BDDMockito.given(availableAccountValidation.validate(depositDTO.receiverAccountNumber()))
				.willReturn(receiverAccount);

		// Comportamento do receiverAccount
		// BDDMockito.given(receiverAccount.getBalance()).willReturn(1000D);
		// BDDMockito.given(receiverAccount.getSpecialLimit()).willReturn(0D);

		// ### ACT ###
		service.deposit(depositDTO);

		// ### ASSERT ###
		// BDDMockito.then(transactionRepository).should().save(BDDMockito.any());
		BDDMockito.then(transactionRepository).should().save(transactionCaptor.capture());
		Transaction transactionSalva = transactionCaptor.getValue();

		// A conta destinatária deveria ser a mesma na transação
		Assertions.assertEquals(receiverAccount, transactionSalva.getReceiverAccount());
		// O valor de depósito deveria ser o mesmo na transação
		Assertions.assertEquals(depositDTO.amount(), transactionSalva.getAmount());
		// O tipo de operação deveria ser DEPOSIT na transação
		Assertions.assertEquals(TransactionType.DEPOSIT, transactionSalva.getType());
		// O saldo na conta de destino deveria ser acrescido com o valor da transação
		Assertions.assertEquals(saldoInicial + depositDTO.amount(), transactionSalva.getReceiverAccount().getBalance());
	}

}
