package de.dhbw.tinf.ase.pe;

public class Karte {

	private String pin;

	public Karte(String pin) {
		try {
			Integer.parseInt(pin);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("PIN muss aus Zahlen bestehen!", e);
		}
		
		if (pin.length() != 4) {
			throw new IllegalArgumentException("PIN muss vierstellig sein!");
		}
		
		this.pin = pin;
	}

	public boolean istKorrekt(String pin) {
		return this.pin.equalsIgnoreCase(pin);
	}
	
}
