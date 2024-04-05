package br.edu.utfpr.bankapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.utfpr.bankapi.dto.AccountDTO;
import br.edu.utfpr.bankapi.exception.NotFoundException;
import br.edu.utfpr.bankapi.model.Account;
import br.edu.utfpr.bankapi.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Optional<Account> getByNumber(long number) {
        return accountRepository.getByNumber(number);
    }

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public Account save(AccountDTO dto) {
        var account = new Account();
        BeanUtils.copyProperties(dto, account);

        account.setBalance(0); // Inicializar a conta com saldo 0.

        System.out.println(account);

        // Salva a conta
        return accountRepository.save(account);
    }

    /**
     * 
     * @param id
     * @param dto
     * @return
     * @throws NotFoundException
     */
    public Account update(long id, AccountDTO dto) throws NotFoundException {
        var res = accountRepository.findById(id);

        if (res.isEmpty())
            throw new NotFoundException();

        var account = res.get();
        account.setName(dto.name());
        account.setNumber(dto.number());
        account.setSpecialLimit(dto.specialLimit());

        System.out.println(account);

        // Salva a conta
        return accountRepository.save(account);
    }
}
