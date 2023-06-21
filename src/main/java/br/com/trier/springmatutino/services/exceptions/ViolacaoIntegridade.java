package br.com.trier.springmatutino.services.exceptions;

@SuppressWarnings("serial")
public class ViolacaoIntegridade extends RuntimeException{

	public ViolacaoIntegridade(String mensagem) {
		super(mensagem);
	}
}
