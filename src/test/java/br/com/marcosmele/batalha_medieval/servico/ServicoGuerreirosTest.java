package br.com.marcosmele.batalha_medieval.servico;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.marcosmele.batalha_medieval.dominio.Classe;
import br.com.marcosmele.batalha_medieval.dominio.Raca;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServicoGuerreirosTest {
	
	@Autowired
	private ServicoGuerreiros servico;
	
	@Test
	public void listaHeroisTest() {
		Assert.assertEquals(servico.herois().size(),3);
	}
	
	@Test
	public void buscaHeroiGuerreiroTest() {
		Assert.assertNotNull(servico.buscarHeroi(Classe.GUERREIRO));
	}
	
	@Test
	public void buscaHeroiInexistenteTest() {
		Assert.assertNull(servico.buscarHeroi(Classe.MORTO_VIVO));
	}
	
	@Test
	public void buscaHeroiMonstroTest() {
		Assert.assertNotNull(servico.buscarMonstro(Classe.KOBOLD));
	}
	
	@Test
	public void buscaMonstroInexistenteTest() {
		Assert.assertNull(servico.buscarMonstro(Classe.BARBARO));
	}
	
	@Test
	public void escolherMonstroTest() {
		Assert.assertNotNull(servico.escolherMonstro());
	}
	
	@Test
	public void escolherMonstroCheckRacaTest() {
		Assert.assertEquals(servico.escolherMonstro().getRaca(),Raca.MONSTRO);
	}

}
