package br.edu.utfpr.bankapi.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InterestCalculatorTest {

    // deveria retornar o valor do juros calculado
    @Test
    public void deveriaRetornarOValorDoJurosCalculadoMes() {
        // ARRANGE
        double valor = 1000;
        float taxaMensal = 1.5f;
        int meses = 6;

        InterestCalculator calculatorJuros = new InterestCalculator();
        // ACT
        double juros = calculatorJuros.calcularJuros(valor, taxaMensal, meses);

        // ASSERT
        Assertions.assertEquals(93.44, juros);
    }

    @Test
    public void deveriaRetornarOValorDoJurosCalculadoDia() {
        // ARRANGE
        double valor = 1000;

        float taxaDia = 0.05f;
        int dias = 30;

        InterestCalculator calculatorJuros = new InterestCalculator();
        // ACT
        double jurosDia = calculatorJuros.calcularJuros(valor, taxaDia, dias);
        // ASSERT
        Assertions.assertEquals(15.11, jurosDia);
    }
}
