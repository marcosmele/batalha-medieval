package br.com.marcosmele.batalha_medieval.servico;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.marcosmele.batalha_medieval.dominio.Batalha;
import br.com.marcosmele.batalha_medieval.dominio.Classe;
import br.com.marcosmele.batalha_medieval.dominio.Dado;
import br.com.marcosmele.batalha_medieval.dominio.Iniciativa;
import br.com.marcosmele.batalha_medieval.dominio.Personagem;
import br.com.marcosmele.batalha_medieval.dominio.Raca;
import br.com.marcosmele.batalha_medieval.excecao.BatalhaExistenteException;
import br.com.marcosmele.batalha_medieval.repositorio.RepositorioBatalha;

/**
 * Servico de batalhas para apoiar a API.
 * @author marcos
 *
 */
@Service
public class ServicoBatalha {
	
	private static final int QTD_FACES_INICIATIVA = 10;
	private static final int QTD_DADO_INICIATIVA = 1;

	@Autowired
	private RepositorioBatalha repositorio;
	
	@Autowired
	private ServicoGuerreiros servicoGuerreiros;
	
	/**
	 * Cria uma nova batalha para o jogador
	 * @param nickname do jogador
	 * @return A batalha criada
	 * @throws BatalhaExistenteException - caso já exista uma batalha em andamento para o jogador
	 */
	public Batalha novaBatalha(String jogador) throws BatalhaExistenteException{
		Batalha batalha = repositorio.findByJogador(jogador);
		if(batalha != null) {
			throw new BatalhaExistenteException(batalha.getId());
		} 
		return repositorio.save(new Batalha(jogador));
	}
	
	/**
	 * Salva a escolha do heroi e tambem define de forma randomica quem e o monstro oponente.
	 * @param idBatalha
	 * @param classe
	 */
	public void escolherHeroi(String idBatalha, Classe classe) {
		Batalha batalha = repositorio.findById(idBatalha).get();
		batalha.setHeroi(classe);
		batalha.setOponente(servicoGuerreiros.escolherMonstro().getClasse());
		
		repositorio.save(batalha);
		
	}

	/**
	 * Identifica se existe ou nao a batalha em andamento
	 * @param idBatalha
	 * @return
	 */
	public boolean existe(String idBatalha) {
		return repositorio.existsById(idBatalha);
	}
	
	/**
	 * Define quem ira iniciar a batalha e comecar com a iniciativa. 
	 * Tenta varias tentativas a partir da rolagem dos dados
	 * @param idBatalha
	 * @return
	 */
	public List<Iniciativa> definirIniciativa(String idBatalha) {
		Batalha batalha = repositorio.findById(idBatalha).get();
		List<Iniciativa> iniciativas = new ArrayList<Iniciativa>();
		Iniciativa iniciativa;
		do {
			iniciativa = criarIniciativa(batalha.getHeroi(),batalha.getOponente());
			iniciativas.add(iniciativa);
		} while(iniciativa.getVencedor() == null);
		
		batalha.setTurno(iniciativa.getVencedor());
		repositorio.save(batalha);
		
		return iniciativas;
	}
	
	public int atacar(String idBatalha) {
		Batalha batalha = repositorio.findById(idBatalha).get();
		Raca atacante = batalha.getTurno();
		
		Dado dadoHeroi = rolarDados(QTD_DADO_INICIATIVA, QTD_FACES_INICIATIVA);
		Dado dadoMonstro = rolarDados(QTD_DADO_INICIATIVA, QTD_FACES_INICIATIVA);
		
		Personagem heroi = servicoGuerreiros.buscarHeroi(batalha.getHeroi());
		Personagem monstro = servicoGuerreiros.buscarMonstro(batalha.getOponente());
		
		int totalHeroi = dadoHeroi.getTotal() + heroi.getAgilidade();
		int totalMonstro = dadoMonstro.getTotal() + monstro.getAgilidade();
		
		if(atacante.equals(Raca.HEROI)) {
			totalHeroi += heroi.getForca();
			totalMonstro += monstro.getDefesa();
			
			if(totalHeroi > totalMonstro) {
				return calcularDano();
			}
			return 0;
		} else {
			totalHeroi += heroi.getDefesa();
			totalMonstro += monstro.getForca();
			
			if(totalMonstro > totalHeroi) {
				return calcularDano();
			}
			return 0;
		}
		
		
	}
	
	private int calcularDano() {
		return 5;
	}
	
	private Iniciativa criarIniciativa(Classe heroi, Classe monstro) {
		Iniciativa iniciativa = new Iniciativa();
		
		Dado dadoHeroi = rolarDados(QTD_DADO_INICIATIVA, QTD_FACES_INICIATIVA);
		Dado dadoMonstro = rolarDados(QTD_DADO_INICIATIVA, QTD_FACES_INICIATIVA);
		
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
	
	private Dado rolarDados(int quantidade, int faces) {
		Dado dado = new Dado();
		for(int i = 1; i<=quantidade; i++) {
			dado.lancar(new Random().nextInt(faces));
		}
		return dado;
	}
		
}
