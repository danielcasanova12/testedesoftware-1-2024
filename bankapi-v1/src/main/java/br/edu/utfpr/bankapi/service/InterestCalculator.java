package br.edu.utfpr.bankapi.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Calculadora de juros
 */
public class InterestCalculator {

    public double calcularJuros(double valor, float taxa,
            int prazo) {
        float taxaDecimal = taxa / 100;
        double juros = valor
                * Math.pow(1 + taxaDecimal, prazo) - valor;

        return new BigDecimal(juros).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }
}
