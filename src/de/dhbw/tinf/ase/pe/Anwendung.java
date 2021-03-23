package de.dhbw.tinf.ase.pe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Anwendung {

	private static BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws Exception {
		Geldautomat geldautomat = Geldautomat.getInstance();

		System.out.println("Willkommen beim DHBW Geldautomat!");

		System.out.println(geldautomat.info());

		while (true) {

			wasWillstDuTun();

			String input = cin.readLine();

			int aktion = 0;

			try {
				aktion = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				System.out.println("Unzulässige Eingabe!");
				continue;
			}

			if (aktion == 1) {
				System.out.println(geldautomat.info());
				System.out.println(geldautomat.getState());
			} else if (aktion == 2) {
				geldautomatBestücken(geldautomat);
			} else if (aktion == 3) {
				karteEinschieben(geldautomat);
			} else if (aktion == 4) {
				pinEingeben(geldautomat);
			} else if (aktion == 5) {
				geldAuszahlen(geldautomat);
			} else if (aktion == 6) {
				karteAuswerfen(geldautomat);
			} else if (aktion == 7) {
				System.out.println("Der Automat enthält " + geldautomat.getBargeld() + " Taler");
			} else if (aktion == 8) {
				break;
			}

		}

		System.out.println("Danke dass du den DHBW Geldautomat benutzt hast :-)");

		cin.close();

	}

	private static void geldAuszahlen(Geldautomat geldautomat) {
		System.out.print("Bitte gib den gewünschten Betrag ein: ");
		try {
			String input = cin.readLine();
			int abheben = Integer.parseInt(input);
			int summe = geldautomat.auszahlen(abheben);
			
			if (summe == -1) {
				System.out.println("Keine Karte oder falsche PIN - bitte noch einmal versuchen!");
			}
			else {
				System.out.println(summe + " Taler ausgegeben - viel Spaß damit");
			}
		}
		 catch (IOException | NumberFormatException e) {
			geldAuszahlen(geldautomat);
		}
		catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			geldAuszahlen(geldautomat);
		}
	}

	private static void pinEingeben(Geldautomat geldautomat) {
		System.out.print("Bitte gib jetzt deine PIN ein: ");
		try {
			String pin = cin.readLine();
			geldautomat.eingeben(pin);
			
		} catch (IllegalStateException e) {
			System.out.println(e.getMessage());
		} catch (IllegalArgumentException | IOException e) {
			pinEingeben(geldautomat);
		} 
	}

	private static void karteEinschieben(Geldautomat geldautomat) {
		if (!geldautomat.hasCardInserted()) {
			String pin = erzeugePin();
			System.out.println("Die Pin für deine Karte ist " + pin);
			Karte karte = new Karte(pin);
			try {
				geldautomat.einschieben(karte);
				System.out.println("Die Karte ist jetzt im Automat");
			} catch (IllegalStateException e) {
				System.out.println(e.getMessage());
			}
		}
		else {
			System.out.println("Es befindet sich bereits eine Karte im Automat!");
		}
	}

	
	private static void karteAuswerfen(Geldautomat geldautomat) {
		try {
			geldautomat.ausgeben();
			System.out.println("Deine Karte wurde wieder ausgeworfen");
		} catch (IllegalStateException e) {
			System.out.println(e.getMessage());
		}
	}
	
	static String erzeugePin() {
		String tempPin = String.valueOf((int)((Math.random() * (9999 - 1)) + 1));
		String zeroPad = "0000";
		return zeroPad.substring(tempPin.length()) + tempPin;
	}

	private static void geldautomatBestücken(Geldautomat geldautomat) {
		System.out.print("Bitte gib die Summe ein: ");
		try {
			String input = cin.readLine();
			int summe = Integer.parseInt(input);
			geldautomat.bestücken(summe);
		} catch (NumberFormatException | IOException e) {
			geldautomatBestücken(geldautomat);
		}
		catch (IllegalStateException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void wasWillstDuTun() {
		System.out.println("Was willst du tun?");
		System.out.println("[1] - Info ausgeben");
		System.out.println("[2] - Geldautomat bestücken");
		System.out.println("[3] - Karte einschieben");
		System.out.println("[4] - PIN eingeben");
		System.out.println("[5] - Geld auszahlen");
		System.out.println("[6] - Karte entnehmen");
		System.out.println("[7] - Füllstand anzeigen");
		System.out.println("[8] - Programm beenden");

	}

}
