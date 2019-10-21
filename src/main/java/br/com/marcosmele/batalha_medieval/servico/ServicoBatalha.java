package br.com.marcosmele.batalha_medieval.servico;

import java.util.ArrayList;
import java.util.List;

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
import br.com.marcosmele.batalha_medieval.excecao.BatalhaExistenteException;
import br.com.marcosmele.batalha_medieval.excecao.MovimentoIncorretoException;
import br.com.marcosmele.batalha_medieval.repositorio.RepositorioBatalha;

/**
 * Servico de batalhas para apoiar a API.
 * @author marcos
 *
 */
@Service
public class ServicoBatalha {
	
	@Autowired
	private RepositorioBatalha repositorio;
	
	@Autowired
	private ServicoGuerreiros servicoGuerreiros;
	
	@Autowired
	private ServicoCalculadorBatalha servicoCalculo;
	
	/**
	 * Cria uma nova batalha para o jogador
	 * @param nickname do jogador
	 * @return A batalha criada
	 * @throws BatalhaExistenteException - caso já exista uma batalha em andamento para o jogador
	 */
	public Batalha novaBatalha(String jogador) throws BatalhaExistenteException{
		Batalha batalha = repositorio.findByJogadorAndFinalizada(jogador,false);
		if(batalha != null) {
			throw new BatalhaExistenteException(batalha.getId());
		} 
		return repositorio.save(new Batalha(jogador));
	}
	
	/**
	 * Salva a escolha do heroi e tambem define de forma randomica quem e o monstro oponente.
	 * @param idBatalha
	 * @param classe
	 * @throws MovimentoIncorretoException 
	 */
	public void escolherHeroi(String idBatalha, Classe classe) throws MovimentoIncorretoException {
		Batalha batalha = repositorio.findById(idBatalha).get();
		if(batalha.getHeroi() != null) {
			throw new MovimentoIncorretoException("Não é possível trocar seu personagem.");
		}
		batalha.setHeroi(classe);
		batalha.setVidaHeroi(servicoGuerreiros.buscarHeroi(classe).getPontosVida());

		Personagem monstro = servicoGuerreiros.escolherMonstro();
		batalha.setOponente(monstro.getClasse());
		batalha.setVidaOponente(monstro.getPontosVida());
		
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
	 * @throws MovimentoIncorretoException 
	 */
	public List<Iniciativa> definirIniciativa(String idBatalha) throws MovimentoIncorretoException {
		Batalha batalha = repositorio.findById(idBatalha).get();
		if(batalha.getHeroi() == null) {
			throw new MovimentoIncorretoException("Não é possível iniciar iniciativas e ataques sem selecionar seu personagem.");
		}
		if(batalha.getTurno() != null) {
			throw new MovimentoIncorretoException("A iniciativa já foi realizada, favor realizar seu ataque.");
		}
		List<Iniciativa> iniciativas = new ArrayList<Iniciativa>();
		Iniciativa iniciativa;
		do {
			iniciativa = servicoCalculo.criarIniciativa(batalha.getHeroi(),batalha.getOponente());
			iniciativas.add(iniciativa);
		} while(iniciativa.getVencedor() == null);
		
		batalha.setTurno(iniciativa.getVencedor());
		repositorio.save(batalha);
		
		return iniciativas;
	}
	
	
	/**
	 * Realiza um ataque para a batalha, de acordo com o dono atual do turno.<br>
	 * O ataque apos realizado, recebe a contagem do dano e define o ponto de vida restante de cada personagem.
	 * @param idBatalha
	 * @return
	 * @throws MovimentoIncorretoException 
	 */
	public Ataque atacar(String idBatalha) throws MovimentoIncorretoException {
		Batalha batalha = repositorio.findById(idBatalha).get();
		
		if(batalha.getHeroi() == null) {
			throw new MovimentoIncorretoException("Não é possível iniciar ataques sem selecionar seu personagem.");
		}
		if(batalha.getTurno() == null) {
			throw new MovimentoIncorretoException("Não é possível realizar um ataque sem realizar previamente a iniciativa.");
		}
		batalha.addTurno();
		Ataque ataque = null;
		
		Dado dadoHeroi = servicoCalculo.rolarDados(ConstanteBatalha.QTD_DADO_INICIATIVA, ConstanteBatalha.QTD_FACES_INICIATIVA);
		Dado dadoMonstro = servicoCalculo.rolarDados(ConstanteBatalha.QTD_DADO_INICIATIVA, ConstanteBatalha.QTD_FACES_INICIATIVA);
		
		Personagem heroi = servicoGuerreiros.buscarHeroi(batalha.getHeroi());
		Personagem monstro = servicoGuerreiros.buscarMonstro(batalha.getOponente());
		
		Raca atacante = batalha.getTurno();
		
		int totalHeroi = servicoCalculo.calcularForca(dadoHeroi, heroi, atacante.equals(Raca.HEROI));
		int totalMonstro = servicoCalculo.calcularForca(dadoMonstro, monstro, atacante.equals(Raca.MONSTRO));
		
		ataque = (atacante.equals(Raca.HEROI) ? servicoCalculo.calcularDano(totalHeroi,totalMonstro,heroi) : servicoCalculo.calcularDano(totalMonstro, totalHeroi, monstro));
		ataque.setDadoHeroi(dadoHeroi);
		ataque.setDadoMonstro(dadoMonstro);
		ataque.setTotalHeroi(totalHeroi);
		ataque.setTotalMonstro(totalMonstro);
		
		servicoCalculo.calcularTurno(ataque, batalha);
		
		batalha.setTurno(null);
		repositorio.save(batalha);
		
		return ataque;
		
	}

	

}
