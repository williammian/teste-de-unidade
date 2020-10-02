package br.com.caelum.leilao.servico;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.caelum.leilao.dominio.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

public class AvaliadorTest {
	
	private Avaliador leiloeiro;
	
	private Usuario maria;
    private Usuario jose;
    private Usuario joao;
    
    @BeforeClass
    public static void testandoBeforeClass() {
      System.out.println("before class");
    }

    @AfterClass
    public static void testandoAfterClass() {
      System.out.println("after class");
    }
	
    @Before
    public void setUp() {
    	System.out.println("inicio teste");
        this.leiloeiro = new Avaliador();
        this.joao = new Usuario("João");
        this.jose = new Usuario("José");
        this.maria = new Usuario("Maria");
    }
    
    @After
    public void finaliza() {
    	System.out.println("fim teste");
    }
    
    @Test
	public void deveCalcularAMediaCorreta() {
    	Leilao leilao = new CriadorDeLeilao()
	            .para("Playstation 3 Novo")
	            .lance(joao, 250)
	            .lance(maria, 300)
	            .lance(joao, 400)
	            .constroi();
        
        leiloeiro.avalia(leilao);
        
        assertEquals(316.67, leiloeiro.getMedia(), 0.0001);
        
        assertThat(316.67, equalTo(316.67));
	}
	
	@Test
    public void deveEntenderLancesEmOrdemCrescente() {
		Leilao leilao = new CriadorDeLeilao()
	            .para("Playstation 3 Novo")
	            .lance(joao, 250)
	            .lance(jose, 300)
	            .lance(maria, 400)
	            .constroi();

        leiloeiro.avalia(leilao);

        assertEquals(400, leiloeiro.getMaiorLance(), 0.0001);
        assertEquals(250, leiloeiro.getMenorLance(), 0.0001);
        
        assertThat(leiloeiro.getMaiorLance(), equalTo(400.0));
        assertThat(leiloeiro.getMenorLance(), equalTo(250.0));
    }
	
	@Test
    public void deveEntenderLeilaoComApenasUmLance() {
		Leilao leilao = new CriadorDeLeilao()
		        .para("Playstation 3 Novo")
		        .lance(joao, 1000)
		        .constroi();

        leiloeiro.avalia(leilao);

        assertEquals(1000.0, leiloeiro.getMaiorLance(), 0.0001);
        assertEquals(1000.0, leiloeiro.getMenorLance(), 0.0001);
        
        assertThat(leiloeiro.getMenorLance(), equalTo(leiloeiro.getMaiorLance()));
    }
	
	@Test
    public void deveEncontrarOsTresMaioresLances() {
		Leilao leilao = new CriadorDeLeilao()
	            .para("Playstation 3 Novo")
	            .lance(joao, 100)
	            .lance(maria, 200)
	            .lance(joao, 300)
	            .lance(maria, 400)
	            .constroi();

        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.getTresMaiores();

        assertEquals(3, maiores.size());
        assertEquals(400.0, maiores.get(0).getValor(), 0.00001);
        assertEquals(300.0, maiores.get(1).getValor(), 0.00001);
        assertEquals(200.0, maiores.get(2).getValor(), 0.00001);
        
        assertThat(maiores, hasItems(
        	    new Lance(maria, 400),
        	    new Lance(joao, 300),
        	    new Lance(maria, 200)
        ));
    }
	
	@Test
    public void deveEntenderLancesEmOrdemCrescenteComOutrosValores() {
		Leilao leilao = new CriadorDeLeilao()
	            .para("Playstation 3 Novo")
	            .lance(joao, 1000)
	            .lance(jose, 2000)
	            .lance(maria, 3000)
	            .constroi();

        leiloeiro.avalia(leilao);

        assertEquals(3000.0, leiloeiro.getMaiorLance(), 0.0001);
        assertEquals(1000.0, leiloeiro.getMenorLance(), 0.0001);
        
        assertThat(leiloeiro.getMaiorLance(), equalTo(3000.0));
        assertThat(leiloeiro.getMenorLance(), equalTo(1000.0));
    }
	
	@Test
    public void deveEntenderLeilaoComLancesEmOrdemRandomica() {
		Leilao leilao = new CriadorDeLeilao()
	            .para("Playstation 3 Novo")
	            .lance(joao, 200)
	            .lance(maria, 450)
	            .lance(joao, 120)
	            .lance(maria, 700)
	            .lance(joao, 630)
	            .lance(maria, 230.0)
	            .constroi();

        leiloeiro.avalia(leilao);

        assertEquals(700.0, leiloeiro.getMaiorLance(), 0.0001);
        assertEquals(120.0, leiloeiro.getMenorLance(), 0.0001);
        
        assertThat(leiloeiro.getMaiorLance(), equalTo(700.0));
        assertThat(leiloeiro.getMenorLance(), equalTo(120.0));
    }
	
	@Test
    public void deveEntenderLeilaoComLancesEmOrdemDecrescente() {
		Leilao leilao = new CriadorDeLeilao()
	            .para("Playstation 3 Novo")
	            .lance(joao, 400)
	            .lance(maria, 300)
	            .lance(joao, 200)
	            .lance(maria, 100)
	            .constroi();

        leiloeiro.avalia(leilao);

        assertEquals(400.0, leiloeiro.getMaiorLance(), 0.0001);
        assertEquals(100.0, leiloeiro.getMenorLance(), 0.0001);
        
        assertThat(leiloeiro.getMaiorLance(), equalTo(400.0));
        assertThat(leiloeiro.getMenorLance(), equalTo(100.0));
    }
	
	@Test
    public void deveDevolverTodosLancesCasoNaoHajaNoMinimo3() {
		Leilao leilao = new CriadorDeLeilao()
	            .para("Playstation 3 Novo")
	            .lance(joao, 100)
	            .lance(maria, 200)
	            .constroi();

        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.getTresMaiores();

        assertEquals(2, maiores.size());
        assertEquals(200, maiores.get(0).getValor(), 0.00001);
        assertEquals(100, maiores.get(1).getValor(), 0.00001);
        
        assertThat(maiores.get(0).getValor(), equalTo(200.0));
        assertThat(maiores.get(1).getValor(), equalTo(100.0));
    }
	
	@Test(expected=RuntimeException.class)
	public void naoDeveAvaliarLeiloesSemNenhumLanceDado() {
	    Leilao leilao = new CriadorDeLeilao()
	        .para("Playstation 3 Novo")
	        .constroi();

	    leiloeiro.avalia(leilao);
	}

}
