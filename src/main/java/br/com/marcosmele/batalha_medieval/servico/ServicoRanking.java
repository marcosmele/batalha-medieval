package br.com.marcosmele.batalha_medieval.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import br.com.marcosmele.batalha_medieval.dominio.Ranking;
import br.com.marcosmele.batalha_medieval.repositorio.RepositorioRanking;

@Service
public class ServicoRanking {
	
	private static final int QTD_MAX_TURNOS = 100;
	
	@Autowired
	private RepositorioRanking repositorio;
	
	/**
	 * Cria um novo item na listagem de ranking com o jogador e sua pontuacao considerando a quantidade de turnos jogada.
	 * @param jogador
	 * @param totalTurnos
	 */
	public void criar(String jogador, int totalTurnos) {
		Ranking ranking = new Ranking(jogador);
		ranking.setPontuacao(QTD_MAX_TURNOS - totalTurnos);
		repositorio.save(ranking);
	}
	
	/**
	 * Listagem do ranking ordenado por pontuacao.
	 * @return
	 */
	public List<Ranking> listar() {
		return repositorio.findAll(Sort.by(Order.desc("pontuacao"),Order.asc("data")));
	}
	
}
