package br.edu.utfpr.bankapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.bankapi.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    public Optional<Account> getByNumber(long number);
}
