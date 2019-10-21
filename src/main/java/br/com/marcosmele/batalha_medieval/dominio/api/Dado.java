package br.com.marcosmele.batalha_medieval.dominio.api;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel(value="Lancamento de dados por iniciativa ou ataque")
public class Dado {

	@ApiModelProperty(value="Todos as faces(valores) que foram tirados nos dados em N lancamentos.")
	@Getter
	private List<Integer> faces;
	
	@ApiModelProperty(value="Somatorio dos valores dos N lancamentos.")
	@Getter
	private int total;
	
	public Dado() {
		faces = new ArrayList<Integer>();
		total = 0;
	}
	
	public void lancar(int valor) {
		faces.add(valor);
		total += valor;
	}
}
