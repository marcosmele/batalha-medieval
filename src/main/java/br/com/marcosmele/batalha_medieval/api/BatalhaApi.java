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
import br.com.marcosmele.batalha_medieval.servico.ServicoBatalha;
import br.com.marcosmele.batalha_medieval.servico.ServicoGuerreiros;

@RestController
@RequestMapping("/api")
public class BatalhaApi {

	@Autowired
	private ServicoGuerreiros servicoGuerreiros;

	@Autowired
	private ServicoBatalha servicoBatalha;

	@GetMapping("/herois")
	public List<Personagem> herois() {
		return servicoGuerreiros.herois();
	}

	@PostMapping("/iniciar")
	public String iniciar(@RequestBody @NotBlank String nickname) {
		return servicoBatalha.novaBatalha(nickname).getId();
	}
	
	@PostMapping("/heroi")
	public void escolherHeroi(@RequestHeader("ID_BATALHA") String idBatalha, @RequestBody @NotBlank Classe classe) {
		servicoBatalha.escolherHeroi(idBatalha, classe);
	}
	
}
