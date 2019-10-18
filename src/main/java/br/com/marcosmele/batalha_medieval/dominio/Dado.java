package br.com.marcosmele.batalha_medieval.dominio;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class Dado {

	@Getter
	private List<Integer> faces;
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
