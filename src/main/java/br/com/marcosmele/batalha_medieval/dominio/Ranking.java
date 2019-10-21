package br.com.marcosmele.batalha_medieval.dominio;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@NoArgsConstructor
public class Ranking {

	private String nickname;
	private int pontuacao;
	private long data;
	
	public Ranking(String nickname) {
		this.nickname = nickname;
		this.data = System.currentTimeMillis();
	}
}
