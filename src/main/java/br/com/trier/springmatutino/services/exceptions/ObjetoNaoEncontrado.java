package br.com.trier.springmatutino.services.exceptions;

@SuppressWarnings("serial")
public class ObjetoNaoEncontrado extends RuntimeException{
	

	public ObjetoNaoEncontrado(String mensagem) {
		super(mensagem);
	}

}
