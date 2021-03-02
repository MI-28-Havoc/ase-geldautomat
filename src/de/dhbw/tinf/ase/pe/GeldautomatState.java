package de.dhbw.tinf.ase.pe;

public abstract class GeldautomatState {
	protected int bargeld = 0;
	protected Karte karte = null;
	protected boolean pinKorrekt = false;
	protected int pinFalsch = 0;
	
	void best�cken(int bargeld) {
		//throw new IllegalStateException("Automat darf nicht w�hrend einer Transaktion best�ckt werden!");
		//Gem�� Diagramm....
		throw new IllegalStateException("Automat ist bereits best�ckt");
	}
	
	void einschieben(Karte karte){};
	void eingeben(String pin){};
	void auszahlen(int summe){};
	void ausgeben(){};
}
