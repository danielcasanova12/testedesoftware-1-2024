package br.edu.utfpr.bankapi.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "source_account_id")
    private Account sourceAccount;

    @ManyToOne
    @JoinColumn(name = "receiver_account_id")
    private Account receiverAccount;

    private double amount;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private LocalDateTime dateTime;

    public Transaction() {
        dateTime = LocalDateTime.now();
    }

    public Transaction(Account sourceAccount, Account receiverAccount, double amount, TransactionType type) {
        this.sourceAccount = sourceAccount;
        this.receiverAccount = receiverAccount;
        this.amount = amount;
        this.type = type;
        this.dateTime = LocalDateTime.now();
    }
}