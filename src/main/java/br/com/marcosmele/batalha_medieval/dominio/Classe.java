package br.com.marcosmele.batalha_medieval.dominio;

import lombok.Getter;

/**
 * Classe de personagens
 * @author marcos
 *
 */
public enum Classe {
	
	GUERREIRO("Guerreiro", Raca.HEROI),
	BARBARO("Bárbaro", Raca.HEROI),
	PALADINO("Paladino", Raca.HEROI),
	MORTO_VIVO("Morto-Vivo", Raca.MONSTRO),
	ORC("Orc", Raca.MONSTRO),
	KOBOLD("Kobold", Raca.MONSTRO);
	
	@Getter
	private String nome;
	@Getter
	private Raca raca;
	
	Classe(String nome,Raca raca) {
		this.nome = nome;
		this.raca = raca;
	}
}
