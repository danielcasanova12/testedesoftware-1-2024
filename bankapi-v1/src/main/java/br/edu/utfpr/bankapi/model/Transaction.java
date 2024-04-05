package br.edu.utfpr.bankapi.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.ManyToAny;
import org.springframework.cglib.core.Local;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private double amount;
    @ManyToOne
    private long souceAccount;
    @ManyToOne
    private long resultAccount;

    private LocalDateTime localDateTime;

    public Transaction() {
        localDateTime = LocalDateTime.now();
    }

}
