package de.dhbw.tinf.ase.pe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;



public class GeldautomatTest {
	
	@Test (expected = IllegalStateException.class)
	public void testZweiteKarteEinschieben() {
		Geldautomat geldautomat = Geldautomat.getInstance();
		geldautomat.resetAutomat();
		geldautomat.bestücken(1000);
		geldautomat.einschieben(new Karte("1111"));
		geldautomat.einschieben(new Karte("2222"));
	}

	@Test (expected = IllegalArgumentException.class)
	public void testFalschePin() {
		Geldautomat geldautomat = Geldautomat.getInstance();
		geldautomat.resetAutomat();
		geldautomat.bestücken(1000);
		geldautomat.einschieben(new Karte("1111"));
		geldautomat.eingeben("2222");
	}
	
	@Test
	public void testAbhebungOhnePin() {
		Geldautomat geldautomat = Geldautomat.getInstance();
		geldautomat.resetAutomat();
		geldautomat.bestücken(1000);
		geldautomat.einschieben(new Karte("1111"));
		int summe = geldautomat.auszahlen(500);
		assertEquals("Bei falscher PIN muss -1 zurückgegeben werden!", -1, summe);
	}

	@Test
	public void testBestückung() {
		Geldautomat geldautomat = Geldautomat.getInstance();
		geldautomat.resetAutomat();
		geldautomat.bestücken(100);
		assertEquals("Bestand muss übereinstimmen!", 100, geldautomat.getBargeld());
	}
	
	@Test
	public void testZuWenigGeldImAutomat() {
		Geldautomat geldautomat = Geldautomat.getInstance();
		geldautomat.resetAutomat();
		geldautomat.bestücken(100);
		geldautomat.einschieben(new Karte("1111"));
		geldautomat.eingeben("1111");
		int summe = geldautomat.auszahlen(300);
		assertEquals("Bei zu wenig Geld muss der Rest(100) zurückgegeben werden!", 100, summe);
		assertEquals("Bargeld muss jetzt 0 sein!", 0, geldautomat.getBargeld());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testPin3Stellig() {
		new Karte("123");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testPin5Stellig() {
		new Karte("12345");
	}

	@Test (expected = IllegalArgumentException.class)
	public void testPinNurZiffern() {
		new Karte("123a");
		new Karte("aa?!");
	}
	
	@Test 
	public void testPinMitFuehrendenNullen() {
		boolean fortfahren = true;
		int counter = 0;
		String pin = "";
		do {
			pin = Anwendung.erzeugePin();
			if (pin.matches("0.{3}")) {
				fortfahren = false;
			}
			
			if (counter >= 1000) { 
				break;
			}
			counter++;
		} while(fortfahren);
		assertEquals("In 1000 Versuchen gab es keine PIN mit führenden Nullen!", false, fortfahren);
	}
	
	@Test (expected = IllegalStateException.class)
	public void testBestueckenNurOhneKarte() {
		Geldautomat geldautomat = Geldautomat.getInstance();
		geldautomat.resetAutomat();
		geldautomat.bestücken(100);
		geldautomat.einschieben(new Karte("1111"));
		geldautomat.bestücken(100);
	}
	
	@Test (expected = IllegalStateException.class)
	public void testKarteInLeerenAutomat() {
		Geldautomat geldautomat = Geldautomat.getInstance();
		geldautomat.resetAutomat();
		geldautomat.einschieben(new Karte("1111")); 
	}
	
	@Test 
	public void testAuszahlungNurMitPin() {
		Geldautomat geldautomat = Geldautomat.getInstance();
		geldautomat.resetAutomat();
		geldautomat.bestücken(200);
		geldautomat.einschieben(new Karte("1111")); 
		geldautomat.auszahlen(300);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAuszahlenMehrAls500() {
		Geldautomat geldautomat = Geldautomat.getInstance();
		geldautomat.resetAutomat();
		geldautomat.bestücken(1000);
		geldautomat.einschieben(new Karte("1111"));
		geldautomat.eingeben("1111");
		geldautomat.auszahlen(501);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAuszahlenWenigerAls5() {
		Geldautomat geldautomat = Geldautomat.getInstance();
		geldautomat.resetAutomat();
		geldautomat.bestücken(1000);
		geldautomat.einschieben(new Karte("1111"));
		geldautomat.eingeben("1111");
		geldautomat.auszahlen(4);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAuszahlsbetragDurch5Teilbar() {
		Geldautomat geldautomat = Geldautomat.getInstance();
		geldautomat.resetAutomat();
		geldautomat.bestücken(10);
		geldautomat.einschieben(new Karte("1111"));
		geldautomat.eingeben("1111");
		geldautomat.auszahlen(6);
	}
	
	@Test (expected = IllegalStateException.class)
	public void testAuszahlenBestandWenigerAls5() {
		Geldautomat geldautomat = Geldautomat.getInstance();
		geldautomat.resetAutomat();
		geldautomat.bestücken(3);
		geldautomat.einschieben(new Karte("1111"));
		geldautomat.eingeben("1111");
		geldautomat.auszahlen(5);
	}
	
	@Test
	public void testTeilweiseAuszahlung() {
		Geldautomat geldautomat = Geldautomat.getInstance();
		geldautomat.resetAutomat();
		geldautomat.bestücken(6);
		geldautomat.einschieben(new Karte("1111"));
		geldautomat.eingeben("1111");
		assertEquals("Wenn der gewünschte Betrag nicht ausbezahlt werden kann, wird das größtmögliche Vielfache von 5 ausbezahlt!", 5, geldautomat.auszahlen(10));
	}
	
	@Test
	public void testDreimalFalschePin() {
		Geldautomat geldautomat = Geldautomat.getInstance();
		geldautomat.resetAutomat();
		geldautomat.bestücken(6);
		geldautomat.einschieben(new Karte("1111"));
		for (int i = 0; i <3; i++) {
			try {
				geldautomat.eingeben("1234");
			}
			catch (Exception e) {
				
			}
		}
		assertTrue(geldautomat.getState() instanceof StateOfReady);
	}	
}
