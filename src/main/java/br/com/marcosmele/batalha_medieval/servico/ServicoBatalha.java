package br.com.marcosmele.batalha_medieval.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.marcosmele.batalha_medieval.dominio.Batalha;
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
	
	public void novaBatalha(String jogador) {
		//TODO Verificar batalha existente
		repositorio.save(new Batalha(jogador));
	}

}
