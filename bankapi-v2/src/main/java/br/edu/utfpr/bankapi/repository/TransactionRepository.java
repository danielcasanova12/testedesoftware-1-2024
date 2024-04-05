package br.edu.utfpr.bankapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.bankapi.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
