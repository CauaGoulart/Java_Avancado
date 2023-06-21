package br.com.trier.springmatutino.services;

import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping(value = "/jogar")
public class DiceRoll {

	@GetMapping("/roll")
	public String rollDice(@RequestParam(name = "dados") int numberOfDice,
	                       @RequestParam(name = "aposta") int betAmount) {
	    if (numberOfDice < 1 || numberOfDice > 4) {
	        throw new IllegalArgumentException("Número inválido de dados lançados. Deve ser entre 1 e 4.");
	    }

	    if (betAmount <= 0) {
	        throw new IllegalArgumentException("Valor da aposta inválido. Deve ser maior que zero.");
	    }

	    List<Integer> valorDados = new ArrayList<>();
	    int totalSum = 0;

	    Random random = new Random();
	    for (int i = 0; i < numberOfDice; i++) {
	        int dadoSorteio = random.nextInt(6) + 1; 
	        valorDados.add(dadoSorteio);
	        totalSum += dadoSorteio;
	    }

	    double percentagemAposta = (betAmount * 100.0) / totalSum;
	    double percentagemRolagem = (totalSum * 100.0) / (numberOfDice * 6);
	    double diferencaPercentual = Math.abs(percentagemAposta - percentagemRolagem);

	    StringBuilder result = new StringBuilder();
	    for (int i = 0; i < numberOfDice; i++) {
	        result.append("Dado ").append(i + 1).append(" = ").append(valorDados.get(i)).append("\n");
	    }
	    result.append("Total = ").append(totalSum).append("\n");
	    result.append("Aposta: ").append(betAmount).append("\n");
	    
	    if (totalSum == betAmount) {
			result.append("Você ganhou a aposta!\n");
	    } else {
	        result.append("Diferença de percentual = ").append(diferencaPercentual).append("%");
	        result.append("\nVocê não ganhou a aposta");
	    }

	    return result.toString();
	}

}