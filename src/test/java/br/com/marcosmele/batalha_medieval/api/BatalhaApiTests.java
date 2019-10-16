package br.com.marcosmele.batalha_medieval.api;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.marcosmele.batalha_medieval.dominio.Batalha;
import br.com.marcosmele.batalha_medieval.dominio.Classe;
import br.com.marcosmele.batalha_medieval.dominio.Personagem;
import br.com.marcosmele.batalha_medieval.excecao.BatalhaExistenteException;
import br.com.marcosmele.batalha_medieval.servico.ServicoBatalha;
import br.com.marcosmele.batalha_medieval.servico.ServicoGuerreiros;

@RunWith(SpringRunner.class)
@WebMvcTest(BatalhaApi.class)
public class BatalhaApiTests {

	private static final String API_BATALHA = "/api/";

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ServicoBatalha servicoBatalha;
	
	@MockBean
	private ServicoGuerreiros servicoGuerreiros;

	@Test
	public void iniciarBatalhaTest() throws Exception {
		String jogador = "marcos";
		String id = "123";
		Batalha batalha = new Batalha(jogador);
		batalha.setId(id);
		
		BDDMockito.given(servicoBatalha.novaBatalha(jogador)).willReturn(batalha);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API_BATALHA + "iniciar").content(jogador);
		ResultMatcher retorno = MockMvcResultMatchers.content().json(id);
		mvc.perform(request).andExpect(retorno);
	}
	
	@Test
	public void iniciarBatalhaJaExistenteTest() throws Exception {
		String jogador = "marcos";
		
		BDDMockito.given(servicoBatalha.novaBatalha(jogador)).willThrow(BatalhaExistenteException.class);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API_BATALHA + "iniciar").content(jogador);
		ResultMatcher retorno = MockMvcResultMatchers.status().isBadRequest();
		mvc.perform(request).andExpect(retorno);
	}
	
	@Test
	public void iniciarBatalhaNicknameVazioTest() throws Exception {
		String jogador = "";
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API_BATALHA + "iniciar").content(jogador);
		ResultMatcher retorno = MockMvcResultMatchers.status().isBadRequest();
		mvc.perform(request).andExpect(retorno);
	}
	
	@Test
	public void listarHeroisTest() throws Exception {
		
		List<Personagem> personagens = Arrays.asList(new Personagem(Classe.GUERREIRO,12,4,3,3,2,4), new Personagem(Classe.BARBARO,13,6,1,3,2,6), new Personagem(Classe.PALADINO,15,2,5,1,2,4));
		
		BDDMockito.given(servicoGuerreiros.herois()).willReturn(personagens);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API_BATALHA + "herois");
		ResultMatcher retorno = MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(personagens));
		mvc.perform(request).andExpect(retorno);
	}
	
	@Test
	public void apiSemHeaderTest() throws Exception {
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API_BATALHA + "heroi").content(Classe.BARBARO.toString());
		ResultMatcher retorno = MockMvcResultMatchers.status().isForbidden();
		mvc.perform(request).andExpect(retorno);
	}

}