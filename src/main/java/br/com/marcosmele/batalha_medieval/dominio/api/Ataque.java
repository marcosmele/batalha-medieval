package br.com.marcosmele.batalha_medieval.dominio.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Generated;

@Data
@Generated
@ApiModel(value="Ataque realizado na rodada")
public class Ataque {
	
	@ApiModelProperty(value="Lançamentos dos dados do heroi")
	private Dado dadoHeroi;
	@ApiModelProperty(value="Total dos pontos (defesa ou ataque) do Heroi somando os dados e suas habilidades")
	private int totalHeroi;
	@ApiModelProperty(value="Lançamentos dos dados do monstro")
	private Dado dadoMonstro;
	@ApiModelProperty(value="Total dos pontos (defesa ou ataque) do Monstro somando os dados e suas habilidades")
	private int totalMonstro;
	@ApiModelProperty(value="Lançamentos dos dados do respresentando o dano do atacante")
	private Dado dano;
	@ApiModelProperty(value="Total do dano causado pelo atacante somando os dados e suas habilidades")
	private int totalDano;
	@ApiModelProperty(value="Pontos de vida restante do Herói")
	private int vidaRestanteHeroi;
	@ApiModelProperty(value="Pontos de vida restante do Monstro")
	private int vidaRestanteMonstro;

}
