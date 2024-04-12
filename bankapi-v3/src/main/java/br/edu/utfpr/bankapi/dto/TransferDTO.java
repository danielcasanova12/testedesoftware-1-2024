package br.edu.utfpr.bankapi.dto;

public record TransferDTO(long sourceAccountNumber, long receiverAccountNumber, double amount) {
}
