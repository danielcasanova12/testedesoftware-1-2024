package br.edu.utfpr.bankapi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccountDTO(@NotBlank String name, @NotNull Long number, double balance, @Min(0) double specialLimit) {
}
