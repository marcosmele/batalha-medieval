package br.com.marcosmele.batalha_medieval.excecao;

@SuppressWarnings("serial")
public class MovimentoIncorretoException extends RuntimeException {

	public MovimentoIncorretoException(String mensagem) {
		super(mensagem);
	}
}
