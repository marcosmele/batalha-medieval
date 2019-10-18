package br.com.marcosmele.batalha_medieval.dominio.api;

import java.util.HashMap;
import java.util.Map;

import br.com.marcosmele.batalha_medieval.dominio.Raca;
import lombok.Data;

@Data
public class Iniciativa {

	private Map<Raca, Dado> lancamentos;

	private Raca vencedor;
	
	public Iniciativa() {
		this.lancamentos = new HashMap<Raca, Dado>();
	}
}
