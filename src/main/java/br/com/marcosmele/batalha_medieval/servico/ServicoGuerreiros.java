package br.com.marcosmele.batalha_medieval.servico;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Service;

import br.com.marcosmele.batalha_medieval.dominio.Classe;
import br.com.marcosmele.batalha_medieval.dominio.Personagem;

@Service
public class ServicoGuerreiros {
	
	private static Map<Classe,Personagem> herois;
	private static Map<Classe, Personagem> monstros;

	static {
		inicializar();
	}

	/**
	 * Listagem estatica de herois e monstros
	 */
	private static void inicializar() {
		herois = new HashMap<Classe, Personagem>();
		herois.put(Classe.GUERREIRO, new Personagem(Classe.GUERREIRO,12,4,3,3,2,4));
		herois.put(Classe.BARBARO, new Personagem(Classe.BARBARO,13,6,1,3,2,6));
		herois.put(Classe.PALADINO, new Personagem(Classe.PALADINO,15,2,5,1,2,4));
		
		monstros = new HashMap<Classe, Personagem>();
		monstros.put(Classe.MORTO_VIVO, new Personagem(Classe.MORTO_VIVO,25,4,0,1,2,4));
		monstros.put(Classe.ORC, new Personagem(Classe.ORC,20,6,2,2,1,8));
		monstros.put(Classe.KOBOLD, new Personagem(Classe.KOBOLD,20,4,2,4,3,2));
	}
	
	public List<Personagem> herois(){
		return (List<Personagem>) herois.values();
	}
	
	public Personagem buscarHeroi(Classe classe) {
		return herois.get(classe);
	}
	
	public Personagem escolherMonstro() {
		Set<Classe> arrayMonstros = monstros.keySet();
		return (Personagem) arrayMonstros.toArray()[new Random().nextInt(arrayMonstros.size())];
	}

}
