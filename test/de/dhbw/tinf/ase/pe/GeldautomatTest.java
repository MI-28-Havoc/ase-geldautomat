package de.dhbw.tinf.ase.pe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.dhbw.tinf.ase.pe.Geldautomat;
import de.dhbw.tinf.ase.pe.Karte;



public class GeldautomatTest {

	
	private final PrintStream standardOut = System.out;
	private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

	
	@Test (expected = IllegalStateException.class)
	@Ignore //TODO:
	public void testKarteIstNull() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestücken(1000);
		geldautomat.einschieben(null);
		fail("Expected IllegalArgumentException!");
	}
	
	@Test (expected = IllegalStateException.class)
	public void testZweiteKarteEinschieben() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestücken(1000);
		geldautomat.einschieben(new Karte("1111"));
		geldautomat.einschieben(new Karte("2222"));
	}

	@Test
	public void testFalschePin() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestücken(1000);
		geldautomat.einschieben(new Karte("1111"));
		geldautomat.eingeben("2222");
		int summe = geldautomat.auszahlen(500);
		assertEquals("Bei falscher PIN muss -1 zurückgegeben werden!", -1, summe);
	}
	
	@Test
	public void testAbhebungOhnePin() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestücken(1000);
		geldautomat.einschieben(new Karte("1111"));
		int summe = geldautomat.auszahlen(500);
		assertEquals("Bei falscher PIN muss -1 zurückgegeben werden!", -1, summe);
	}

	@Test
	public void testBestückung() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestücken(100);
		assertEquals("Bestand muss übereinstimmen!", 100, geldautomat.füllstand());
	}
	
	@Test
	public void testZuWenigGeldImAutomat() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestücken(100);
		geldautomat.einschieben(new Karte("1111"));
		geldautomat.eingeben("1111");
		int summe = geldautomat.auszahlen(300);
		assertEquals("Bei zu wenig Geld muss der Rest(100) zurückgegeben werden!", 100, summe);
		assertEquals("Bargeld muss jetzt 0 sein!", 0, geldautomat.füllstand());
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
		//TODO: Kathrins Idee: AUtomat muss bestückt werden bevor die Karte eingeschoben wird
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.einschieben(new Karte("1111"));
		geldautomat.bestücken(1000);
	}
	
	@Test (expected = IllegalStateException.class)
	public void testKarteInLeerenAutomat() {
		//TODO: Kathrins Idee: Automat muss bestückt werden bevor die Karte eingeschoben wird
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.einschieben(new Karte("1111")); 
	}
	
	@Test 
	public void testAuszahlungNurMitPin() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestücken(200);
		geldautomat.einschieben(new Karte("1111")); 
		geldautomat.auszahlen(300);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAuszahlenMehrAls500() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestücken(1000);
		geldautomat.einschieben(new Karte("1111"));
		geldautomat.eingeben("1111");
		geldautomat.auszahlen(501);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAuszahlenWenigerAls5() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestücken(1000);
		geldautomat.einschieben(new Karte("1111"));
		geldautomat.eingeben("1111");
		geldautomat.auszahlen(4);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAuszahlsbetragDurch5Teilbar() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestücken(1000);
		geldautomat.einschieben(new Karte("1111"));
		geldautomat.eingeben("1111");
		geldautomat.auszahlen(17);
	}
	
	/*TODO: Unit Tests schrieben für Zustandsübergänge sobald State implementiert wurde*/
	
	/* TODO: Kann zur Überprüfung von Console.Output benutzt werden
	@Before
	public void setUp() {
	    System.setOut(new PrintStream(outputStreamCaptor));
	}
	
	@Test
	public void kurzerTest() {
		
	        
	    assertEquals("Hello Baeldung Readers!!", outputStreamCaptor.toString()
	      .trim());
	}
	
	@After
	public void tearDown() {
	    System.setOut(standardOut);
	}
	*/
}
