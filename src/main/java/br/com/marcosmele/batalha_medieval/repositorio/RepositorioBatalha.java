package br.com.marcosmele.batalha_medieval.repositorio;

import org.springframework.data.repository.CrudRepository;

import br.com.marcosmele.batalha_medieval.dominio.Batalha;

public interface RepositorioBatalha extends CrudRepository<Batalha, String>{
	
	public Batalha findByJogador(String jogador);

}
