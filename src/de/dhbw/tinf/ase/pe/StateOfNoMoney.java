package de.dhbw.tinf.ase.pe;

public class StateOfNoMoney extends GeldautomatState{
	Geldautomat geldautomat;
	
	public StateOfNoMoney(Geldautomat geldautomat) {
		this.geldautomat = geldautomat;
	}

	@Override
	public void best�cken(int bargeld) {
		/*if (this.karte != null) {
			throw new IllegalStateException("Automat darf nicht w�hrend einer Transaktion best�ckt werden!");
		}*/
		this.bargeld += bargeld;
	}

	@Override
	void einschieben(Karte karte) {
		throw new IllegalStateException("Der Automat muss zuvor best�ckt werden!");
	}

	@Override
	void eingeben(String pin) {
		throw new IllegalStateException("Es muss zuvor eine Karte eingeschoben werden!");
	}

	@Override
	void auszahlen(int summe) {
		throw new IllegalStateException("Es ist kein Geld vorhanden!");
	}

	@Override
	void ausgeben() {
		throw new IllegalStateException("Es befindet sich keine Karte im Automat!");
	}

	
	
}
