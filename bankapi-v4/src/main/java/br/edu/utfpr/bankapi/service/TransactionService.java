package br.edu.utfpr.bankapi.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.utfpr.bankapi.dto.DepositDTO;
import br.edu.utfpr.bankapi.dto.TransferDTO;
import br.edu.utfpr.bankapi.dto.WithdrawDTO;
import br.edu.utfpr.bankapi.exception.NotFoundException;
import br.edu.utfpr.bankapi.model.Transaction;
import br.edu.utfpr.bankapi.model.TransactionType;
import br.edu.utfpr.bankapi.repository.TransactionRepository;
import br.edu.utfpr.bankapi.validations.AvailableAccountValidation;
import br.edu.utfpr.bankapi.validations.AvailableBalanceValidation;
import jakarta.transaction.Transactional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AvailableBalanceValidation availableBalanceValidation;

    @Autowired
    private AvailableAccountValidation availableAccountValidation;

    @Transactional
    public Transaction transfer(TransferDTO dto) throws NotFoundException {
        var transaction = new Transaction();
        transaction.setType(TransactionType.TRANSFER);
        BeanUtils.copyProperties(dto, transaction);

        // Valida e obtém a Conta de ORIGEM da transferência
        var source = availableAccountValidation.validate(dto.sourceAccountNumber());
        // Valida e obtém Conta de DESTINO da transferência
        var receiver = availableAccountValidation.validate(dto.receiverAccountNumber());

        // Seta a conta de origem da transferência
        transaction.setSourceAccount(source);
        // Seta a conta de destino da transferência
        transaction.setReceiverAccount(receiver);

        // Verifica se a conta de origem possui saldo
        availableBalanceValidation.validate(transaction);

        // Debitando o valor da conta de origem
        transaction.getSourceAccount()
                .setBalance(transaction.getSourceAccount().getBalance() - transaction.getAmount());

        // Creditando o valor na conta de destino
        transaction.getReceiverAccount()
                .setBalance(transaction.getReceiverAccount().getBalance() + transaction.getAmount());

        System.out.println(transaction);

        // Salvando a transação
        return transactionRepository.save(transaction);
    }

    /**
     * Operação de depósito em uma conta
     * 
     * @throws NotFoundException
     * 
     * @throws Exception
     */
    @Transactional
    public Transaction deposit(DepositDTO dto) throws NotFoundException {
        var transaction = new Transaction();
        transaction.setType(TransactionType.DEPOSIT);
        BeanUtils.copyProperties(dto, transaction);

        // Valida e obtém Conta de DESTINO do depósito
        var receiver = availableAccountValidation.validate(dto.receiverAccountNumber());

        // Seta a conta de destino do depósito
        transaction.setReceiverAccount(receiver);

        System.out.println(transaction);

        // Creditando o valor do depósito na conta de destino
        transaction.getReceiverAccount()
                .setBalance(transaction.getReceiverAccount().getBalance() + transaction.getAmount());

        // Salvando a transação
        return transactionRepository.save(transaction);
    }

    /**
     * Operação de SAQUE de uma conta
     * 
     * @throws NotFoundException
     * 
     * @throws Exception
     */
    @Transactional
    public Transaction withdraw(WithdrawDTO dto) throws NotFoundException {
        var transaction = new Transaction();
        transaction.setType(TransactionType.WITHDRAW);
        BeanUtils.copyProperties(dto, transaction);

        // Valida e obtém a Conta de ORIGEM do Saque
        var source = availableAccountValidation.validate(dto.sourceAccountNumber());

        // Setando a conta de origem do saque
        transaction.setSourceAccount(source);

        // Verifica se a conta de origem possui saldo
        availableBalanceValidation.validate(transaction);

        System.out.println(transaction);

        // Debitando o valor da conta de origem do saque
        transaction.getSourceAccount()
                .setBalance(transaction.getSourceAccount().getBalance() - transaction.getAmount());

        // Salvando a transação
        return transactionRepository.save(transaction);
    }
}
