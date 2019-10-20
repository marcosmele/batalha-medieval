package br.com.marcosmele.batalha_medieval.dominio;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * Batalha registrada com os jogadores.
 * @author marcos
 *
 */
@Document
@Data
public class Batalha {

	@Id
	private String id;
	
	@Indexed
	private String jogador;
	
	private boolean finalizada;
	
	private Classe heroi;
	
	private Classe oponente;
	
	private int vidaHeroi;
	
	private int vidaOponente;
	
	private Integer pontuacao;

	private Raca turno;
	
	public Batalha(String jogador) {
		this.jogador = jogador;
		this.finalizada = false;
		this.pontuacao = 0;
	}


}
