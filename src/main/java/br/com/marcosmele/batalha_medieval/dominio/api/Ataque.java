package br.com.marcosmele.batalha_medieval.dominio.api;

import lombok.Data;

@Data
public class Ataque {
	
	private Dado dadoheroi;
	private int totalHeroi;
	private Dado dadoMonstro;
	private int totalMonstro;
	private Dado dano;
	private int totalDano;
	private int vidaRestanteHeroi;
	private int vidaRestanteMonstro;

}
