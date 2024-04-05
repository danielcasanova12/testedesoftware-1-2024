package br.edu.utfpr.bankapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.utfpr.bankapi.dto.DepositDTO;
import br.edu.utfpr.bankapi.dto.TransferDTO;
import br.edu.utfpr.bankapi.dto.WithdrawDTO;
import br.edu.utfpr.bankapi.model.Transaction;
import br.edu.utfpr.bankapi.repository.TransactionRepository;
import jakarta.transaction.Transactional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * Operação de depósito em uma conta
     */
    @Transactional
    public Transaction deposit(DepositDTO dto) {
        return null;
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
