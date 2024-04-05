package br.edu.utfpr.bankapi.exception;

public class WithoutBalanceException extends RuntimeException {
    public WithoutBalanceException() {
        super("No balance in account");
    }
}
