package br.com.marcosmele.batalha_medieval.servico;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.marcosmele.batalha_medieval.dominio.Batalha;
import br.com.marcosmele.batalha_medieval.dominio.Classe;
import br.com.marcosmele.batalha_medieval.dominio.ConstanteBatalha;
import br.com.marcosmele.batalha_medieval.dominio.Personagem;
import br.com.marcosmele.batalha_medieval.dominio.Raca;
import br.com.marcosmele.batalha_medieval.dominio.api.Ataque;
import br.com.marcosmele.batalha_medieval.dominio.api.Dado;
import br.com.marcosmele.batalha_medieval.dominio.api.Iniciativa;

@Service
class ServicoCalculadorBatalha {
	
	@Autowired
	private ServicoGuerreiros servicoGuerreiros;
	
	@Autowired 
	private ServicoRanking servicoRanking;
	
	/**
	 * Calcula a força de um personagem para o turno baseado na rolagem de dados e se o personagem é o atacante ou defensor.
	 * @param dado - Rolagem do dado realizada.
	 * @param personagem - O personagem 
	 * @param atacante - Se o mesmo é atacante ou defensor.
	 * @return - Forca total
	 */
	int calcularForca(Dado dado, Personagem personagem, boolean atacante) {
		return dado.getTotal() + personagem.getAgilidade() + (atacante ? personagem.getForca() : personagem.getDefesa());
	}
	
	/**
	 * Calcula o dano do ataque de acordo com personagem e a diferença do total das forcas.
	 * @param totalAtacante
	 * @param totalDefensor
	 * @param atacante
	 * @return dano do Ataque
	 */
	Ataque calcularDano(int totalAtacante, int totalDefensor, Personagem atacante) {
		Ataque ataque = new Ataque();
		
		int dano = 0;
		if(totalAtacante > totalDefensor) {
			Dado dado = rolarDados(atacante.getQuantidadeDadosDano(), atacante.getFacesDadosDano());
			
			ataque.setDano(dado);
			dano = dado.getTotal() + atacante.getForca();
		}
		ataque.setTotalDano(dano);
		
		return ataque;
	}
	
	/**
	 * Calcula uma nova iniciativa rolando os dados para os personagens e define um vencedor.
	 * @param heroi
	 * @param monstro
	 * @return Resumo da Iniciativa e seu vencedor.
	 */
	Iniciativa criarIniciativa(Classe heroi, Classe monstro) {
		Iniciativa iniciativa = new Iniciativa();
		
		Dado dadoHeroi = rolarDados(ConstanteBatalha.QTD_DADO_INICIATIVA, ConstanteBatalha.QTD_FACES_INICIATIVA);
		Dado dadoMonstro = rolarDados(ConstanteBatalha.QTD_DADO_INICIATIVA, ConstanteBatalha.QTD_FACES_INICIATIVA);
		
		iniciativa.getLancamentos().put(Raca.HEROI, dadoHeroi);
		iniciativa.getLancamentos().put(Raca.MONSTRO, dadoMonstro);
		
		int totalHeroi = dadoHeroi.getTotal() + servicoGuerreiros.buscarHeroi(heroi).getAgilidade();
		int totalMonstro = dadoMonstro.getTotal() + servicoGuerreiros.buscarMonstro(monstro).getAgilidade();
		
		Raca vencedor = null;
		if(totalHeroi > totalMonstro) {
			vencedor = Raca.HEROI;
		}else if(totalHeroi < totalMonstro) {
			vencedor = Raca.MONSTRO;
		}
		iniciativa.setVencedor(vencedor);
		
		return iniciativa;
	}
	
	/**
	 * Rola os dados N vezes e calcula os lancamentos de forma randomica para o total de faces.
	 * @param quantidade
	 * @param faces
	 * @return
	 */
	Dado rolarDados(int quantidade, int faces) {
		Dado dado = new Dado();
		for(int i = 1; i<=quantidade; i++) {
			dado.lancar(new Random().ints(1,faces+1).findFirst().getAsInt());
		}
		return dado;
	}
	
	/**
	 * Calcula o total do turno baseado no dano ja calculado, e define se a batalha foi finalizada.
	 * @param ataque
	 * @param batalha
	 */
	void calcularTurno(Ataque ataque, Batalha batalha) {
		
		if(batalha.getTurno().equals(Raca.HEROI)) {
			int vida = (batalha.getVidaOponente() > ataque.getTotalDano()) ? batalha.getVidaOponente()-ataque.getTotalDano() : 0;
			batalha.setVidaOponente(vida);
			ataque.setVidaRestanteHeroi(batalha.getVidaHeroi());
			ataque.setVidaRestanteMonstro(vida);
			if(vida == 0) {
				batalha.setFinalizada(true);
				servicoRanking.criar(batalha.getJogador(), batalha.getQuantidadeTurnos());
			}
		}else {
			int vida = (batalha.getVidaHeroi() > ataque.getTotalDano()) ? batalha.getVidaHeroi()-ataque.getTotalDano() : 0;
			batalha.setVidaHeroi(vida);
			ataque.setVidaRestanteMonstro(batalha.getVidaOponente());
			ataque.setVidaRestanteHeroi(vida);
			if(vida == 0) {
				batalha.setFinalizada(true);
			}
		}
	}

}
