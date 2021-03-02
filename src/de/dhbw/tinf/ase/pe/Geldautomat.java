package de.dhbw.tinf.ase.pe;

public class Geldautomat {

	/* in GeldautomatState export
	private int bargeld = 0;
	private Karte karte;
	private boolean pinKorrekt = false;
	private int pinFalsch = 0;*/
	GeldautomatState stateOfNoMoney = new StateOfNoMoney(this);
	
	GeldautomatState state = stateOfNoMoney;

	public void bestücken(int bargeld) {
		state.bestücken(bargeld);
		/* rüber in StateOfNoMoney
		if (this.karte != null) {
			throw new IllegalStateException("Automat darf nicht während einer Transaktion bestückt werden!");
		}
		this.bargeld += bargeld;
		*/
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
					"Betrag muss zwischen 5 und 500 Taler liegen!");
		}

		/* Raus, verursachte Bug mit Auszahlung des kompletten Betrags ohne PIN, 
		 * wenn man mehr auszahlen möchte als vorhanden war.
		 * if (bargeld < summe) {
			int ausbezahlt = bargeld;
			bargeld = 0;
			return ausbezahlt;
		}*/

		if (pinKorrekt) {
			int ausbezahlt = 0;
			if (summe % 5 == 0) {
				
				//Wenn mehr verlangt als vorhanden...
				if (bargeld-summe < 0) {
					if (bargeld-5 < 0 ) {
						throw new IllegalStateException("Es sind nicht genügend Taler im Automat vorhanden!");
					}
					while(bargeld-5 >= 0)
					{
						bargeld -= 5;
						ausbezahlt += 5;
					}				
				}
				else {
					ausbezahlt = summe;
					bargeld -= summe;
				}
				return ausbezahlt;
			}
			else {
				throw new IllegalArgumentException(
						"Betrag muss durch 5 Taler teilbar sein!");
			}
		}

		return -1; //Passiert wenn PIN nicht korrekt oder Betrag nicht durch 5 teilbar
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
					return "Abhebung bis zu " + bargeld + " Taler ist möglich";
				} else {
					return "Falsche PIN oder PIN nicht eingegeben - Abhebung nicht möglich!";
				}
			} else {
				return "Abhebung bis zu " + bargeld + " Taler ist möglich - bitte Karte eingeben";
			}
		}
		
		return "Der Automat enthält " + füllstand() + " Taler.";
	}
	
	public int füllstand() {
		return bargeld;
	}
	
}
