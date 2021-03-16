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
		setBargeld(getBargeld() + bargeld);
		this.geldautomat.setState(this.geldautomat.getStateOfReady());
	}

	@Override
	void einschieben(Karte karte) {
		throw new IllegalStateException("Der Automat muss zuvor best�ckt werden! Die Karte wird wieder ausgeworfen!");
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
		return "Der Automat enth�lt " + getBargeld() + " Taler. Um Fortzufahren den Automat bitte ausreichend best�cken!";
	}
	
}
