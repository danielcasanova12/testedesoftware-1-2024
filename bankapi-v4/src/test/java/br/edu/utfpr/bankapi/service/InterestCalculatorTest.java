package br.edu.utfpr.bankapi.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class InterestCalculatorTest {

	@Test
	void deveriaCalcularJuros() {
		// ### ARRANGE ###

		double valor = 1000; // Valor emprestado
        float taxaDiaria = 0.05f; // Taxa de juros diária (5%)
        float taxaMensal = 1.5f; // Taxa de juros mensal (1.5%)
        int dias = 30; // Quantidade de dias
        int meses = 6; // Quantidade de meses

		

		// ### ACT ###

        // Chamada do método para calcular juros por dia
        double jurosPorDia = InterestCalculator.calcularJuros(valor, taxaDiaria, dias);

        // Chamada do método para calcular juros por mês
        double jurosPorMes = InterestCalculator.calcularJuros(valor, taxaMensal, meses);



		// ### ASSERT ###

		Assertions.assertEquals(15.11, jurosPorDia);
		Assertions.assertEquals(93.44, jurosPorMes);
	}

}
