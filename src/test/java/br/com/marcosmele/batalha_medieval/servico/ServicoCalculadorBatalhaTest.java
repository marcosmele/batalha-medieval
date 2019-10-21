package br.com.marcosmele.batalha_medieval.servico;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.marcosmele.batalha_medieval.dominio.Batalha;
import br.com.marcosmele.batalha_medieval.dominio.Classe;
import br.com.marcosmele.batalha_medieval.dominio.Personagem;
import br.com.marcosmele.batalha_medieval.dominio.Raca;
import br.com.marcosmele.batalha_medieval.dominio.api.Ataque;
import br.com.marcosmele.batalha_medieval.dominio.api.Dado;
import br.com.marcosmele.batalha_medieval.dominio.api.Iniciativa;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServicoCalculadorBatalhaTest {
	
	@Autowired
	private ServicoCalculadorBatalha servico;
	
	@MockBean
	private ServicoGuerreiros servicoGuerreiro;
	
	@MockBean
	private Dado dado;
	
	@MockBean
	private Ataque ataque;
	
	@MockBean
	private Batalha batalha;
	
	@Test
	public void rolarDadosCheckRolagenstest() {
		Dado dado = servico.rolarDados(2,6);
		
		Assert.assertEquals(dado.getFaces().size(),2);
	}
	
	@Test
	public void rolarDadosCheckTotaltest() {
		Dado dado = servico.rolarDados(3,10);
		
		Assert.assertTrue(dado.getTotal()>3);
	}
	
	@Test
	public void criarIniciativatest() {
		
		Personagem heroi =  new Personagem(Classe.GUERREIRO,12,4,3,3,2,4);
		Personagem monstro = new Personagem(Classe.ORC,20,6,2,2,1,8);
		
		BDDMockito.given(servicoGuerreiro.buscarHeroi(Classe.GUERREIRO)).willReturn(heroi);
		BDDMockito.given(servicoGuerreiro.buscarMonstro(Classe.ORC)).willReturn(monstro);
		
		Iniciativa iniciativa = servico.criarIniciativa(Classe.GUERREIRO, Classe.ORC);
		
		
		Assert.assertTrue(iniciativa.getLancamentos().size() >= 2);
	}
	
	@Test
	public void calcularForcatest() {
		int valorDado = 5;
		Personagem personagem =  new Personagem(Classe.GUERREIRO,12,4,3,3,2,4);
		BDDMockito.given(dado.getTotal()).willReturn(valorDado);
		
		int forca = servico.calcularForca(dado, personagem, true);
		
		Assert.assertTrue(forca>valorDado);
	}
	
	@Test
	public void calcularForcaFullDefesatest() {
		int valorDado = 5, agilidade = 3, defesa = 3;
		Personagem personagem =  new Personagem(Classe.GUERREIRO,12,4,agilidade,defesa,2,4);
		
		BDDMockito.given(dado.getTotal()).willReturn(valorDado);
		
		int forca = servico.calcularForca(dado, personagem, false);
		
		Assert.assertEquals(forca,valorDado + agilidade + defesa);
	}
	
	@Test
	public void calcularDanoAtaqueVencetest() {
		int valorDado = 5, forca = 4, totalAtaque = 10, totalDefesa = 8;
		Personagem personagem =  new Personagem(Classe.GUERREIRO,12,forca,3,3,2,4);
		
		ServicoCalculadorBatalha espiao = Mockito.spy(servico);
		BDDMockito.doReturn(dado).when(espiao).rolarDados(2, 4);
		BDDMockito.given(dado.getTotal()).willReturn(valorDado);
		
		Ataque ataque = espiao.calcularDano(totalAtaque, totalDefesa, personagem);
		
		Assert.assertEquals(ataque.getTotalDano(), forca + valorDado);
	}
	
	@Test
	public void calcularDanoAtaqueEmpatetest() {
		int forca = 4, totalAtaque = 10, totalDefesa = 10;
		Personagem personagem =  new Personagem(Classe.GUERREIRO,12,forca,3,3,2,4);
		
		Ataque ataque = servico.calcularDano(totalAtaque, totalDefesa, personagem);
		
		Assert.assertEquals(ataque.getTotalDano(), 0);
	}
	
	@Test
	public void calcularDanoAtaquePerdetest() {
		int forca = 4, totalAtaque = 10, totalDefesa = 11;
		Personagem personagem =  new Personagem(Classe.GUERREIRO,12,forca,3,3,2,4);
		
		Ataque ataque = servico.calcularDano(totalAtaque, totalDefesa, personagem);
		
		Assert.assertEquals(ataque.getTotalDano(), 0);
	}
	
	@Test
	public void calcularTurnotest() {
		int vidaOponente = 10,totalDano = 8;
		
		BDDMockito.given(batalha.getTurno()).willReturn(Raca.HEROI);
		BDDMockito.given(batalha.getVidaOponente()).willReturn(vidaOponente);
		BDDMockito.given(ataque.getTotalDano()).willReturn(totalDano);
		
		servico.calcularTurno(ataque, batalha);
		
		Assert.assertFalse(batalha.isFinalizada());
	}
	
	@Test
	public void calcularTurnoMonstrotest() {
		int vidaOponente = 10,totalDano = 8;
		
		BDDMockito.given(batalha.getTurno()).willReturn(Raca.MONSTRO);
		BDDMockito.given(batalha.getVidaHeroi()).willReturn(vidaOponente);
		BDDMockito.given(ataque.getTotalDano()).willReturn(totalDano);
		
		servico.calcularTurno(ataque, batalha);
		
		Assert.assertFalse(batalha.isFinalizada());
	}

	@Test
	public void calcularTurnoFinalizadotest() {
		int vidaOponente = 7,totalDano = 8;
		
		Batalha batalha = new Batalha("");
		Batalha espiao = Mockito.spy(batalha);
		
		BDDMockito.doReturn(Raca.HEROI).when(espiao).getTurno();
		BDDMockito.doReturn(vidaOponente).when(espiao).getVidaOponente();
		BDDMockito.given(ataque.getTotalDano()).willReturn(totalDano);
		
		servico.calcularTurno(ataque, espiao);
		
		Assert.assertTrue(espiao.isFinalizada());
	}
}
