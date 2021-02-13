package de.dhbw.tinf.ase.pe;

public class Geldautomat {

	private int bargeld = 0;
	private Karte karte;
	private boolean pinKorrekt = false;
	private int pinFalsch = 0;

	public void best�cken(int bargeld) {
		if (this.karte != null) {
			throw new IllegalStateException("Automat darf nicht w�hrend einer Transaktion best�ckt werden!");
		}
		this.bargeld += bargeld;
	}
	
	public void einschieben(Karte karte) {
		if (this.karte != null) {
			throw new IllegalStateException("Es befindet sich bereits eine Karte im Automat!");
		}
		//TODO: Abfragen ob Automat grade leer. Falls ja, Karte wieder nauswerfen
		this.karte = karte;
	}

	public void ausgeben() {
		karte = null;
		pinKorrekt = false;
	}

	public void eingeben(String pin) {
		try {
			Integer.parseInt(pin);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("PIN muss aus Zahlen bestehen!",
					e);
		}

		if (pin.length() != 4) {
			throw new IllegalArgumentException("PIN muss vierstellig sein!");
		}

		if (karte != null) {
			if (karte.istKorrekt(pin)) {
				pinKorrekt = true;
			} else {
				pinFalsch++;
			}
			
			if (pinFalsch > 2) {
				ausgeben();
				throw new IllegalStateException("PIN zu oft falsch eingegeben!");
			}
		}

	}

	public int auszahlen(int summe) {
		if (summe < 5 || summe > 500) {
			throw new IllegalArgumentException(
					"Betrag muss zwischen 5 und 500 Geld liegen!");
		}

		/* Raus, verursachte Bug mit Auszahlung des kompletten Betrags ohne PIN, 
		 * wenn man mehr auszahlen m�chte als vorhanden war.
		 * if (bargeld < summe) {
			int ausbezahlt = bargeld;
			bargeld = 0;
			return ausbezahlt;
		}*/

		if (pinKorrekt) {
			int ausbezahlt = summe;
			if (summe % 5 == 0) {
				bargeld -= summe;
				
				if (bargeld < 0) {
					//TODO: Der tats�chlich ausgezahlte Betrag muss auch modulo 5 pr�fung unterzogen werden.
					ausbezahlt = summe+bargeld;
					bargeld = 0;
				}
				return ausbezahlt;
			}
		}

		return -1;
	}

	public String info() {
		if (bargeld > 500) {
			if (karte != null) {
				if (pinKorrekt) {
					return "Maximalbetrag kann abgehoben werden";
				} else {
					return "Falsche PIN oder PIN nicht eingegeben - Abhebung nicht m�glich!";
				}
			} else {
				return "Alles OK - bitte Karte eingeben";
			}
		} else if (bargeld > 0) {
			if (karte != null) {
				if (pinKorrekt) {
					return "Abhebung bis zu " + bargeld + " Geld ist m�glich";
				} else {
					return "Falsche PIN oder PIN nicht eingegeben - Abhebung nicht m�glich!";
				}
			} else {
				return "Abhebung bis zu " + bargeld + " Geld ist m�glich - bitte Karte eingeben";
			}
		}
		
		return "Der Automat enth�lt " + f�llstand() + " Taler.";
	}
	
	public int f�llstand() {
		return bargeld;
	}
	
}
