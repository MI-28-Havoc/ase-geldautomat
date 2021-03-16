package de.dhbw.tinf.ase.pe;

public abstract class GeldautomatState {
	private int bargeld = 0;
	private Karte karte = null;
	private boolean pinKorrekt = false;
	private int pinFalsch = 0;
	
	void best�cken(int bargeld) {
		//throw new IllegalStateException("Automat darf nicht w�hrend einer Transaktion best�ckt werden!");
		//Gem�� Diagramm....
		throw new IllegalStateException("Automat ist bereits best�ckt");
	}
	
	void einschieben(Karte karte){};
	void eingeben(String pin){};
	int auszahlen(int summe){return 0;};
	void ausgeben(){};
	String info() {return "";}

	public int getBargeld() {
		return bargeld;
	}

	public void setBargeld(int bargeld) {
		this.bargeld = bargeld;
	}

	public Karte getKarte() {
		return karte;
	}

	public void setKarte(Karte karte) {
		this.karte = karte;
	}

	public boolean isPinKorrekt() {
		return pinKorrekt;
	}

	public void setPinKorrekt(boolean pinKorrekt) {
		this.pinKorrekt = pinKorrekt;
	}

	public int getPinFalsch() {
		return pinFalsch;
	}

	public void setPinFalsch(int pinFalsch) {
		this.pinFalsch = pinFalsch;
	};
	
	public boolean hasCardInserted() {
		return (karte != null);
	}
	
	public boolean isBestueckt() {
		return (bargeld > 0);
	}
}
