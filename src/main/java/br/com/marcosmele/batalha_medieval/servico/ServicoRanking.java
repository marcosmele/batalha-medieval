package br.com.marcosmele.batalha_medieval.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.marcosmele.batalha_medieval.dominio.Ranking;
import br.com.marcosmele.batalha_medieval.repositorio.RepositorioRanking;

@Service
public class ServicoRanking {
	
	private static final int QTD_MAX_TURNOS = 100;
	
	@Autowired
	private RepositorioRanking repositorio;
	
	public void criar(String jogador, int totalTurnos) {
		Ranking ranking = new Ranking(jogador);
		ranking.setPontuacao(QTD_MAX_TURNOS - totalTurnos);
		repositorio.save(ranking);
	}
	
}
