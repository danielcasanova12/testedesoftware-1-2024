package br.edu.utfpr.bankapi.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.utfpr.bankapi.exception.NotFoundException;
import br.edu.utfpr.bankapi.model.Account;
import br.edu.utfpr.bankapi.repository.AccountRepository;

/**
 * Validar se existe uma conta cadastrada
 */
@Component
public class AvailableAccountValidation {

    @Autowired
    private AccountRepository accountRepository;

    public Account validate(long number) throws NotFoundException {
        var account = accountRepository.getByNumber(number); // Busca uma conta

        if (account.isEmpty())
            throw new NotFoundException("Conta " + number + " inexistente");

        return account.get();
    }

}