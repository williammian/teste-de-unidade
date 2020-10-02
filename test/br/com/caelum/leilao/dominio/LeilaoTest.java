package br.com.caelum.leilao.dominio;

import static br.com.caelum.leilao.matcher.LeilaoMatcher.temUmLance;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LeilaoTest {
	
	@Test
	public void deveReceberUmLance() {
		Leilao leilao = new CriadorDeLeilao().para("Macbook Pro 15").constroi();
		assertEquals(0, leilao.getLances().size());
		
		Lance lance = new Lance(new Usuario("Steve Jobs"), 2000);
		leilao.propoe(lance);
		
		assertEquals(1, leilao.getLances().size());
		assertEquals(2000, leilao.getLances().get(0).getValor(), 0.00001);
		
		assertThat(leilao.getLances().size(), equalTo(1));
        assertThat(leilao, temUmLance(lance));
	}
	
	@Test
    public void deveReceberVariosLances() {
		Leilao leilao = new CriadorDeLeilao()
	            .para("Macbook Pro 15")
	            .lance(new Usuario("Steve Jobs"), 2000)
	            .lance(new Usuario("Steve Wozniak"), 3000)
	            .constroi();

        assertEquals(2, leilao.getLances().size());
        assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.00001);
        assertEquals(3000.0, leilao.getLances().get(1).getValor(), 0.00001);
    }
	
	@Test
	public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario() {
		Usuario steveJobs = new Usuario("Steve Jobs");
        Leilao leilao = new CriadorDeLeilao()
            .para("Macbook Pro 15")
            .lance(steveJobs, 2000.0)
            .lance(steveJobs, 3000.0)
            .constroi();
        
        assertEquals(1, leilao.getLances().size());
        assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.00001);
	}
	
	@Test
	public void naoDeveAceitarMaisDoQue5LancesDeUmMesmoUsuario() {
		Usuario steveJobs = new Usuario("Steve Jobs");
        Usuario billGates = new Usuario("Bill Gates");

        Leilao leilao = new CriadorDeLeilao().para("Macbook Pro 15")
                .lance(steveJobs, 2000)
                .lance(billGates, 3000)
                .lance(steveJobs, 4000)
                .lance(billGates, 5000)
                .lance(steveJobs, 6000)
                .lance(billGates, 7000)
                .lance(steveJobs, 8000)
                .lance(billGates, 9000)
                .lance(steveJobs, 10000)
                .lance(billGates, 11000)
                .lance(steveJobs, 12000)
                .constroi();
	    
	    assertEquals(10, leilao.getLances().size());
	    assertEquals(11000.0, leilao.getLances().get(leilao.getLances().size()-1).getValor(), 0.00001);
	}
	
	@Test
    public void deveDobrarOUltimoLanceDado() {
		Usuario steveJobs = new Usuario("Steve Jobs");
		Usuario billGates = new Usuario("Bill Gates");
		
		Leilao leilao = new CriadorDeLeilao().para("Macbook Pro 15")
				.lance(steveJobs, 2000)
                .lance(billGates, 3000)
                .constroi();
				
	    leilao.dobraLance(steveJobs);
	    
	    assertEquals(3, leilao.getLances().size());
	    assertEquals(4000.0, leilao.getLances().get(leilao.getLances().size()-1).getValor(), 0.00001);
	}
	
	@Test
    public void naoDeveDobrarCasoNaoHajaLanceAnterior() {
		Usuario steveJobs = new Usuario("Steve Jobs");
		
		Leilao leilao = new CriadorDeLeilao()
	            .para("Macbook Pro 15")
	            .constroi();

        leilao.dobraLance(steveJobs);

        assertEquals(0, leilao.getLances().size());
    }

}
