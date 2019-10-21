package br.com.marcosmele.batalha_medieval.excecao;

import lombok.Getter;

@SuppressWarnings("serial")
public class BatalhaExistenteException extends RuntimeException {

	@Getter
	private String id;
	
	@Getter
	private String proximaAcao;
	
	public BatalhaExistenteException(String id, String proximaAcao) {
		super("Não é possível iniciar uma nova partida. Já existe uma partida " + id + " em andamento. Prossiga com o próximo passo: " + proximaAcao);
		this.id = id;
		this.proximaAcao = proximaAcao;
	}
	

}
