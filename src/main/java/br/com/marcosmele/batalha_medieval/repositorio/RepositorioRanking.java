package br.com.marcosmele.batalha_medieval.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.marcosmele.batalha_medieval.dominio.Ranking;

public interface RepositorioRanking extends MongoRepository<Ranking, String>{

}
