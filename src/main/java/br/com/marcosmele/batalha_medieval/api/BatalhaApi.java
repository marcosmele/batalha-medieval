package br.com.marcosmele.batalha_medieval.api;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.marcosmele.batalha_medieval.dominio.Personagem;
import br.com.marcosmele.batalha_medieval.servico.ServicoGuerreiros;

@RestController
@RequestMapping("/api")
public class BatalhaApi {
	
	@GetMapping("/herois")
	public List<Personagem> herois(){
		return ServicoGuerreiros.getHerois();
	}
	
	@GetMapping("/iniciar")
	public void iniciar(@RequestBody @NotBlank String nickname) {
	}
}
