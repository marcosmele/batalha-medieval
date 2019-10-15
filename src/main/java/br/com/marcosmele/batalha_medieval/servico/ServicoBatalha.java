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
	
	public Batalha novaBatalha(String jogador) throws BatalhaExistenteException{
		Batalha batalha = repositorio.findByJogador(jogador);
		if(batalha != null) {
			throw new BatalhaExistenteException(batalha.getId());
		} 
		return repositorio.save(new Batalha(jogador));
	}
	
	public void escolherHeroi(String idBatalha, Classe classe) {
		//TODO tratar erro ao enviar batalha errada
		Batalha batalha = repositorio.findById(idBatalha).get();
		batalha.setHeroi(servicoGuerreiros.buscarHeroi(classe));
		batalha.setOponente(servicoGuerreiros.escolherMonstro());
		
		repositorio.save(batalha);
		
	}
		
}
