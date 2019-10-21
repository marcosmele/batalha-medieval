package br.com.marcosmele.batalha_medieval.dominio.api;

import java.util.HashMap;
import java.util.Map;

import br.com.marcosmele.batalha_medieval.dominio.Raca;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Generated;

@Data
@Generated
@ApiModel(description="Iniciativa de turno.")
public class Iniciativa {

	@ApiModelProperty(value="Mapa de lancamentos de dado por Raca(HEROI ou MONSTRO)")
	private Map<Raca, Dado> lancamentos;

	@ApiModelProperty(value="Raca(HEROI ou MONSTRO) vencedora do turno")
	private Raca vencedor;
	
	public Iniciativa() {
		this.lancamentos = new HashMap<Raca, Dado>();
	}
}
