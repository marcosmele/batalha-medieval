package br.com.marcosmele.batalha_medieval.dominio;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Ranking {

	private String nickname;
	private int pontuacao;
	
	public Ranking(String jogador) {
		this.nickname = jogador;
	}
}
