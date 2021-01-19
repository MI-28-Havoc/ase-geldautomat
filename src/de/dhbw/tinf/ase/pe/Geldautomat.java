package de.dhbw.tinf.ase.pe;

public class Geldautomat {

	private int bargeld = 0;
	private Karte karte;
	private boolean pinKorrekt = false;
	private int pinFalsch = 0;

	public void bestücken(int bargeld) {
		if (this.karte != null) {
			throw new IllegalStateException("Automat darf nicht während einer Transaktion bestückt werden!");
		}
		this.bargeld += bargeld;
	}
	
	public void einschieben(Karte karte) {
		if (this.karte != null) {
			throw new IllegalStateException("Es befindet sich bereits eine Karte im Automat!");
		}
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

		if (bargeld < summe) {
			int ausbezahlt = bargeld;
			bargeld = 0;
			return ausbezahlt;
		}

		if (pinKorrekt) {
			bargeld -= summe;
			return summe;
		}

		return -1;
	}

	public String info() {
		if (bargeld > 500) {
			if (karte != null) {
				if (pinKorrekt) {
					return "Maximalbetrag kann abgehoben werden";
				} else {
					return "Falsche PIN oder PIN nicht eingegeben - Abhebung nicht möglich!";
				}
			} else {
				return "Alles OK - bitte Karte eingeben";
			}
		} else if (bargeld > 0) {
			if (karte != null) {
				if (pinKorrekt) {
					return "Abhebung bis zu " + bargeld + " Geld ist möglich";
				} else {
					return "Falsche PIN oder PIN nicht eingegeben - Abhebung nicht möglich!";
				}
			} else {
				return "Abhebung bis zu " + bargeld + " Geld ist möglich - bitte Karte eingeben";
			}
		}
		
		return "Der Automat enthält " + füllstand() + " Taler.";
	}
	
	public int füllstand() {
		return bargeld;
	}
	
}
