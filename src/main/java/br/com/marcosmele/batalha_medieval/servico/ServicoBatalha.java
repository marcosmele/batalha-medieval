package br.com.marcosmele.batalha_medieval.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.marcosmele.batalha_medieval.dominio.Batalha;
import br.com.marcosmele.batalha_medieval.dominio.Classe;
import br.com.marcosmele.batalha_medieval.excecao.BatalhaExistenteException;
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
	
	public void escolherHeroi(String idBatalha, Classe classe) {
		Batalha batalha = repositorio.findById(idBatalha).get();
		batalha.setHeroi(classe);
		batalha.setOponente(servicoGuerreiros.escolherMonstro().getClasse());
		
		repositorio.save(batalha);
		
	}

	public boolean existe(String idBatalha) {
		return repositorio.existsById(idBatalha);
	}
		
}
