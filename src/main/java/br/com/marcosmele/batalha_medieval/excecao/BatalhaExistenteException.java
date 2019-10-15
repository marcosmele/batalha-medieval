package br.com.marcosmele.batalha_medieval.excecao;

import lombok.Getter;

@SuppressWarnings("serial")
public class BatalhaExistenteException extends RuntimeException {

	@Getter
	private String id;
	
	public BatalhaExistenteException(String id) {
		super("Não é possível iniciar uma nova partida. Já existe uma partida " + id + " em andamento. Favor continuar ou desistir.");
		this.id = id;
	}
	

}
