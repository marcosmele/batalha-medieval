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
import br.com.marcosmele.batalha_medieval.excecao.BatalhaExistenteException;
import br.com.marcosmele.batalha_medieval.excecao.BatalhaInvalidaException;
import br.com.marcosmele.batalha_medieval.excecao.MovimentoIncorretoException;
import br.com.marcosmele.batalha_medieval.servico.ServicoBatalha;
import br.com.marcosmele.batalha_medieval.servico.ServicoGuerreiros;
import br.com.marcosmele.batalha_medieval.servico.ServicoRanking;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api")
public class BatalhaApi {

	@Autowired
	private ServicoGuerreiros servicoGuerreiros;

	@Autowired
	private ServicoBatalha servicoBatalha;
	
	@Autowired
	private ServicoRanking servicoRanking;

	@ApiOperation(value = "Listagem de Heróis", response = Personagem.class, responseContainer = "List" )
	@GetMapping("/herois")
	public List<Personagem> herois() {
		return servicoGuerreiros.herois();
	}

	@ApiOperation(value = "Inicia uma nova batalha")
	@ApiResponses(value = { 
			@ApiResponse(code = 400,response=BatalhaExistenteException.class, message = ""), 
			@ApiResponse(code = 200,message = "ID da batalha")})
	@PostMapping("/iniciar")
	public String iniciar(@RequestBody @NotBlank String nickname) {
		return servicoBatalha.novaBatalha(nickname).getId();
	}
	
	@ApiOperation(value = "Escolhe um herói para a batalha")
	@ApiResponses(value = { 
			@ApiResponse(code = 403,response=BatalhaInvalidaException.class, message = ""), 
			@ApiResponse(code = 400,response=MovimentoIncorretoException.class, message = "")})
	@PostMapping("/heroi")
	public void escolherHeroi(@RequestHeader("ID_BATALHA") String idBatalha, @RequestBody @NotBlank String classe) {
		servicoBatalha.escolherHeroi(idBatalha, Classe.valueOf(classe));
	}
	
	@ApiOperation(value = "Realiza uma iniciativa para o turno", response=Iniciativa.class, responseContainer="List")
	@ApiResponses(value = { 
			@ApiResponse(code = 403,response=BatalhaInvalidaException.class, message = ""), 
			@ApiResponse(code = 400,response=MovimentoIncorretoException.class, message = "")})
	@PostMapping("/iniciativa")
	public List<Iniciativa> iniciativa(@RequestHeader("ID_BATALHA") String idBatalha){
		return servicoBatalha.definirIniciativa(idBatalha);
	}
	
	@ApiOperation(value = "Realiza o ataque de quem tiver vencido a iniciativa", response=Ataque.class)
	@ApiResponses(value = { 
			@ApiResponse(code = 403,response=BatalhaInvalidaException.class, message = ""), 
			@ApiResponse(code = 400,response=MovimentoIncorretoException.class, message = "")})
	@PostMapping("/ataque")
	public Ataque ataque(@RequestHeader("ID_BATALHA") String idBatalha){
		return servicoBatalha.atacar(idBatalha);
	}
	
	@ApiOperation(value = "Lista todo o ranking ordenado por pontuacao", response=Ranking.class, responseContainer="List")
	@GetMapping("/ranking")
	public List<Ranking> ranking(){
		return servicoRanking.listar();
	}
	
}
