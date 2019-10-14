package br.com.marcosmele.batalha_medieval.dominio;

import lombok.Data;

/**
 * Personagem responsavel pelas batalhas
 * 
 * @author marcos
 *
 */
@Data
public class Personagem {

	private Raca raca;

	private Classe classe;

	private Integer pontosVida;

	private Integer forca;

	private Integer defesa;

	private Integer agilidade;

	private Integer quantidadeDadosDano;

	private Integer facesDadosDano;

	public Personagem(Classe classe, Integer pontosVida, Integer forca, Integer defesa, Integer agilidade,
			Integer quantidadeDadosDano, Integer facesDadosDano) {
		super();
		this.classe = classe;
		this.raca = classe.getRaca();
		this.pontosVida = pontosVida;
		this.forca = forca;
		this.defesa = defesa;
		this.agilidade = agilidade;
		this.quantidadeDadosDano = quantidadeDadosDano;
		this.facesDadosDano = facesDadosDano;
	}

}
