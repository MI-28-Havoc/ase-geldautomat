package de.dhbw.tinf.ase.pe;

public class StateOfCardInside extends GeldautomatState{
	Geldautomat geldautomat;
	
	public StateOfCardInside(Geldautomat geldautomat) {
		this.geldautomat = geldautomat;
	}

	@Override
	public void bestücken(int bargeld) {
		throw new IllegalStateException("Der Automat kann nur bestückt werden, wenn sich keine Karte in ihm befindet!");
	}

	@Override
	void einschieben(Karte karte) {
		throw new IllegalStateException("Es befindet sich bereits eine Karte im Automat!");
	}

	@Override
	void eingeben(String pin) {
		try {
			Integer.parseInt(pin);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("PIN muss aus Zahlen bestehen!",
					e);
		}

		if (pin.length() != 4) {
			throw new IllegalArgumentException("PIN muss vierstellig sein!");
		}

		if (getKarte() != null) {
			if (getKarte().istKorrekt(pin)) {
				setPinKorrekt(true);
				this.geldautomat.setState(this.geldautomat.getStateOfPinCorrect());
				setPinFalsch(0);
			} else {
				setPinFalsch(getPinFalsch()+1);
				if (getPinFalsch() > 2) {
					setPinFalsch(0);
					ausgeben();
					throw new IllegalStateException("PIN zu oft falsch eingegeben!");
				}
				throw new IllegalArgumentException("PIN muss vierstellig sein!");
			}
		}
	}

	@Override
	int auszahlen(int summe) {
		return -1;
	}

	@Override
	void ausgeben() {
		setKarte(null); 
		setPinKorrekt(false);
		this.geldautomat.setState(this.geldautomat.getStateOfReady());
	}

	@Override
	String info() {
		return  "Falsche PIN oder PIN nicht eingegeben - Abhebung nicht möglich!";
	}
	
}
