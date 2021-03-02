package de.dhbw.tinf.ase.pe;

public abstract class GeldautomatState {
	protected int bargeld = 0;
	protected Karte karte = null;
	protected boolean pinKorrekt = false;
	protected int pinFalsch = 0;
	
	void bestücken(int bargeld) {
		//throw new IllegalStateException("Automat darf nicht während einer Transaktion bestückt werden!");
		//Gemäß Diagramm....
		throw new IllegalStateException("Automat ist bereits bestückt");
	}
	
	void einschieben(Karte karte){};
	void eingeben(String pin){};
	void auszahlen(int summe){};
	void ausgeben(){};
}
