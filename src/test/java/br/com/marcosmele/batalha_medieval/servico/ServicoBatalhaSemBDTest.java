package br.com.marcosmele.batalha_medieval.servico;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.marcosmele.batalha_medieval.dominio.Batalha;
import br.com.marcosmele.batalha_medieval.dominio.Classe;
import br.com.marcosmele.batalha_medieval.dominio.ConstanteBatalha;
import br.com.marcosmele.batalha_medieval.dominio.Personagem;
import br.com.marcosmele.batalha_medieval.dominio.Raca;
import br.com.marcosmele.batalha_medieval.dominio.api.Ataque;
import br.com.marcosmele.batalha_medieval.dominio.api.Dado;
import br.com.marcosmele.batalha_medieval.dominio.api.Iniciativa;
import br.com.marcosmele.batalha_medieval.excecao.BatalhaExistenteException;
import br.com.marcosmele.batalha_medieval.repositorio.RepositorioBatalha;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServicoBatalhaSemBDTest {
	
	@Autowired
	private ServicoBatalha servico;
	
	@MockBean
	private RepositorioBatalha repositorio;

	@MockBean
	private ServicoCalculadorBatalha servicoCalculo;
	
	@MockBean
	private ServicoGuerreiros servicoGuerreiro;
	
	@MockBean(name="dado1")
	private Dado dado1;

	@MockBean(name="dado2")
	private Dado dado2;
	
	private static String IDBATALHA = "q213jiojh1l2897ujdas897";
	private static String JOGADOR = "marcos";
	
	private Batalha batalhaMock;
	
	@Before
	public void setUp() {
		batalhaMock = new Batalha(JOGADOR);
		batalhaMock.setHeroi(Classe.BARBARO);
		batalhaMock.setOponente(Classe.MORTO_VIVO);
		BDDMockito.given(repositorio.findById(IDBATALHA)).willReturn(Optional.of(batalhaMock));
	}
	
	@Test
	public void novaBatalhaTest() {
		
		BDDMockito.given(repositorio.findByJogadorAndFinalizada(JOGADOR, false)).willReturn(null);
		
		servico.novaBatalha(JOGADOR);
	}
	
	@Test(expected=BatalhaExistenteException.class)
	public void novaBatalhaErroTest() {
		
		Batalha batalha = new Batalha(JOGADOR);
		BDDMockito.given(repositorio.findByJogadorAndFinalizada(JOGADOR, false)).willReturn(batalha);
		
		servico.novaBatalha(JOGADOR);
	}
	
	@Test
	public void definirIniciativaTest() {
		
		Iniciativa iniciativa = new Iniciativa();
		iniciativa.setVencedor(Raca.HEROI);
		
		Iniciativa iniciativa2 = new Iniciativa();
		iniciativa2.setVencedor(Raca.MONSTRO);
		
		BDDMockito.when(servicoCalculo.criarIniciativa(Classe.BARBARO, Classe.MORTO_VIVO)).thenReturn(iniciativa,iniciativa2);
		
		Assert.assertEquals(Raca.HEROI,servico.definirIniciativa(IDBATALHA).get(0).getVencedor());
	}
	
	@Test
	public void definirMultiplasIniciativaTest() {
		
		Iniciativa iniciativa = new Iniciativa();
		
		Iniciativa iniciativa2 = new Iniciativa();
		iniciativa2.setVencedor(Raca.MONSTRO);
		
		BDDMockito.when(servicoCalculo.criarIniciativa(Classe.BARBARO, Classe.MORTO_VIVO)).thenReturn(iniciativa,iniciativa2);
		
		Assert.assertEquals(2,servico.definirIniciativa(IDBATALHA).size());
	}
	
	@Test
	public void atacarTest() {
		int totalHeroi = 10, totalMonstro = 8;
		
		Ataque ataque = new Ataque();
		ataque.setTotalDano(10);
		
		batalhaMock.setTurno(Raca.HEROI);
		batalhaMock.setVidaOponente(20);
		
		Personagem heroi =  new Personagem(batalhaMock.getHeroi(),12,4,3,3,2,4);
		Personagem monstro = new Personagem(batalhaMock.getOponente(),20,6,2,2,1,8);
		
		BDDMockito.when(servicoCalculo.rolarDados(ConstanteBatalha.QTD_DADO_INICIATIVA,ConstanteBatalha.QTD_FACES_INICIATIVA)).thenReturn(dado1,dado2);
		
		BDDMockito.given(servicoGuerreiro.buscarHeroi(batalhaMock.getHeroi())).willReturn(heroi);
		BDDMockito.given(servicoGuerreiro.buscarMonstro(batalhaMock.getOponente())).willReturn(monstro);
		
		BDDMockito.when(servicoCalculo.calcularForca(dado1, heroi, true)).thenReturn(totalHeroi);
		BDDMockito.when(servicoCalculo.calcularForca(dado2, monstro, false)).thenReturn(totalMonstro);
		BDDMockito.when(servicoCalculo.calcularDano(totalHeroi, totalMonstro, heroi)).thenReturn(ataque);		
		
		BDDMockito.doCallRealMethod().when(servicoCalculo).calcularTurno(ataque, batalhaMock);
		
		servico.atacar(IDBATALHA);
		
		Assert.assertFalse(batalhaMock.isFinalizada());
	}
	
	@Test
	public void atacarFinalizadoTest() {
		int totalHeroi = 8, totalMonstro = 10;
		
		Ataque ataque = new Ataque();
		ataque.setTotalDano(30);
		
		batalhaMock.setTurno(Raca.MONSTRO);
		batalhaMock.setVidaOponente(20);
		batalhaMock.setQuantidadeTurnos(1);
		
		Personagem heroi =  new Personagem(batalhaMock.getHeroi(),12,4,3,3,2,4);
		Personagem monstro = new Personagem(batalhaMock.getOponente(),20,6,2,2,1,8);
		
		BDDMockito.when(servicoCalculo.rolarDados(ConstanteBatalha.QTD_DADO_INICIATIVA,ConstanteBatalha.QTD_FACES_INICIATIVA)).thenReturn(dado1,dado2);
		
		BDDMockito.given(servicoGuerreiro.buscarHeroi(batalhaMock.getHeroi())).willReturn(heroi);
		BDDMockito.given(servicoGuerreiro.buscarMonstro(batalhaMock.getOponente())).willReturn(monstro);
		
		BDDMockito.when(servicoCalculo.calcularForca(dado1, heroi, false)).thenReturn(totalHeroi);
		BDDMockito.when(servicoCalculo.calcularForca(dado2, monstro, true)).thenReturn(totalMonstro);
		BDDMockito.when(servicoCalculo.calcularDano(totalMonstro, totalHeroi, monstro)).thenReturn(ataque);		
		
		BDDMockito.doCallRealMethod().when(servicoCalculo).calcularTurno(ataque, batalhaMock);
		
		
		servico.atacar(IDBATALHA);
		
		Assert.assertTrue(batalhaMock.isFinalizada());
	}

}
