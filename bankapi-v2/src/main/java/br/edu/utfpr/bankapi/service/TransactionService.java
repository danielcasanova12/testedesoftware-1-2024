package br.edu.utfpr.bankapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.utfpr.bankapi.dto.DepositDTO;
import br.edu.utfpr.bankapi.dto.TransferDTO;
import br.edu.utfpr.bankapi.dto.WithdrawDTO;
import br.edu.utfpr.bankapi.exception.NotFoundException;
import br.edu.utfpr.bankapi.model.Transaction;
import br.edu.utfpr.bankapi.model.TransactionType;
import br.edu.utfpr.bankapi.repository.AccountRepository;
import br.edu.utfpr.bankapi.repository.TransactionRepository;
import jakarta.transaction.Transactional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;

    /**
     * Operação de depósito em uma conta
     */
    @Transactional
    public Transaction deposit(DepositDTO dto) throws NotFoundException {
        // faça o codigo para o deposito
        Transaction transaction = new Transaction(null, null, dto.amount(), TransactionType.DEPOSIT);
        transactionRepository.save(transaction);

        var res = accountRepository.getByNumber(dto.receiverAccountNumber());
        // verifica se a conta de destino existe
        if (res.isEmpty()) {
            throw new NotFoundException();
        }

        // verifica se o mount e menor ou = a 0
        if (dto.amount() <= 0) {
            throw new IllegalArgumentException();
        }

        // credito na conta de destino
        transaction.getReceiverAccount().setBalance(
                transaction.getReceiverAccount().getBalance() + dto.amount());

        return transactionRepository.save(transaction);
    }

    /**
     * Operação de SAQUE de uma conta
     */
    @Transactional
    public Transaction withdraw(WithdrawDTO dto) {
        return null;
    }

    /**
     * Operação de transferência entre contas.
     */
    @Transactional
    public Transaction transfer(TransferDTO dto) {

        return null;
    }

}
