package br.com.marcosmele.batalha_medieval.servico;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.marcosmele.batalha_medieval.dominio.Classe;
import br.com.marcosmele.batalha_medieval.dominio.Personagem;
import lombok.Getter;

@Service
public class ServicoGuerreiros {
	
	@Getter
	private static List<Personagem> herois;
	@Getter
	private static List<Personagem> monstros;

	static {
		inicializar();
	}

	private static void inicializar() {
		herois = Arrays.asList(
				new Personagem(Classe.GUERREIRO,12,4,3,3,2,4),
				new Personagem(Classe.BARBARO,13,6,1,3,2,6),
				new Personagem(Classe.PALADINO,15,2,5,1,2,4)
		);
		monstros = Arrays.asList(
				new Personagem(Classe.MORTO_VIVO,25,4,0,1,2,4),
				new Personagem(Classe.ORC,20,6,2,2,1,8),
				new Personagem(Classe.KOBOLD,20,4,2,4,3,2)
		);
	}

}
