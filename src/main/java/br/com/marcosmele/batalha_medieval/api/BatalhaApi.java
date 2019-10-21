package br.com.marcosmele.batalha_medieval.api;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.marcosmele.batalha_medieval.dominio.Classe;
import br.com.marcosmele.batalha_medieval.dominio.Personagem;
import br.com.marcosmele.batalha_medieval.dominio.Ranking;
import br.com.marcosmele.batalha_medieval.dominio.api.Ataque;
import br.com.marcosmele.batalha_medieval.dominio.api.Iniciativa;
import br.com.marcosmele.batalha_medieval.servico.ServicoBatalha;
import br.com.marcosmele.batalha_medieval.servico.ServicoGuerreiros;
import br.com.marcosmele.batalha_medieval.servico.ServicoRanking;

@RestController
@RequestMapping("/api")
public class BatalhaApi {

	@Autowired
	private ServicoGuerreiros servicoGuerreiros;

	@Autowired
	private ServicoBatalha servicoBatalha;
	
	@Autowired
	private ServicoRanking servicoRanking;

	@GetMapping("/herois")
	public List<Personagem> herois() {
		return servicoGuerreiros.herois();
	}

	@PostMapping("/iniciar")
	public String iniciar(@RequestBody @NotBlank String nickname) {
		return servicoBatalha.novaBatalha(nickname).getId();
	}
	
	@PostMapping("/heroi")
	public void escolherHeroi(@RequestHeader("ID_BATALHA") String idBatalha, @RequestBody @NotBlank String classe) {
		servicoBatalha.escolherHeroi(idBatalha, Classe.valueOf(classe));
	}
	
	@PostMapping("/iniciativa")
	public List<Iniciativa> iniciativa(@RequestHeader("ID_BATALHA") String idBatalha){
		return servicoBatalha.definirIniciativa(idBatalha);
	}
	
	@PostMapping("/ataque")
	public Ataque ataque(@RequestHeader("ID_BATALHA") String idBatalha){
		return servicoBatalha.atacar(idBatalha);
	}
	
	@GetMapping("/ranking")
	public List<Ranking> ranking(){
		return servicoRanking.listar();
	}
	
}
