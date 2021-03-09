package de.dhbw.tinf.ase.pe;

public class StateOfReady extends GeldautomatState{
	Geldautomat geldautomat;
	
	public StateOfReady(Geldautomat geldautomat) {
		this.geldautomat = geldautomat;
	}

	@Override
	public void best�cken(int bargeld) {
		setBargeld(getBargeld() + bargeld);
	}

	@Override
	void einschieben(Karte karte) {
//		if (this.karte != null) {
//			throw new IllegalStateException("Es befindet sich bereits eine Karte im Automat!");
//		}
		setKarte(karte);
		this.geldautomat.setState(this.geldautomat.getStateOfCardInside());
	}

	@Override
	void eingeben(String pin) {
		throw new IllegalStateException("Es muss zuvor eine Karte eingeschoben werden!");
	}

	@Override
	int auszahlen(int summe) {
		return -1;
	}

	@Override
	void ausgeben() {
		throw new IllegalStateException("Es befindet sich keine Karte im Automat!");
	}

	@Override
	String info() {
		return  "Abhebung bis zu " + getBargeld() + " Taler ist m�glich - bitte Karte eingeben.";
	}
	
}
