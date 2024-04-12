package br.edu.utfpr.bankapi.model;

import br.edu.utfpr.bankapi.dto.AccountDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private long number;
    // private int checkDigit;

    @Column(nullable = false)
    private double balance;

    @Column(nullable = false)
    private double specialLimit;

    public double getBalanceWithLimit() {
        return balance + specialLimit;
    }

    public Account(String name, long number, double balance, double specialLimit) {
        this.name = name;
        this.number = number;
        this.balance = balance;
        this.specialLimit = specialLimit;
    }

    public Account(AccountDTO dto) {
        name = dto.name();
        number = dto.number();
        balance = dto.balance();
        specialLimit = dto.specialLimit();
    }
}
