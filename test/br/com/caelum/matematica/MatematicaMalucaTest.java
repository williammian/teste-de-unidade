package br.com.caelum.matematica;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MatematicaMalucaTest {
	
	@Test
	public void deveTestarNumeroMaiorQue30() {
		MatematicaMaluca matematicaMaluca = new MatematicaMaluca();
		assertEquals(40*4, matematicaMaluca.contaMaluca(40));
	}
	
	@Test
	public void deveTestarNumeroMaiorQue10MenorQue30() {
		MatematicaMaluca matematicaMaluca = new MatematicaMaluca();
		assertEquals(20*3, matematicaMaluca.contaMaluca(20));
	}
	
	@Test
	public void deveTestarNumeroMenorQue10() {
		MatematicaMaluca matematicaMaluca = new MatematicaMaluca();
		assertEquals(5*2, matematicaMaluca.contaMaluca(5));
	}

}
