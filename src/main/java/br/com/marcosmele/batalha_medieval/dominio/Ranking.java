package br.com.marcosmele.batalha_medieval.dominio;

import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@NoArgsConstructor
@ApiModel(value="Representacao de um rankeado vitorioso")
public class Ranking {


	@ApiModelProperty(value="Nome do jogador")
	private String nickname;
	@ApiModelProperty(value="Pontuação do jogador na batalha")
	private int pontuacao;
	@ApiModelProperty(value="Representacao em milissegundos da data da vitoria, para desempate.")
	private long data;
	
	public Ranking(String nickname) {
		this.nickname = nickname;
		this.data = System.currentTimeMillis();
	}
}
