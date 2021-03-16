package de.dhbw.tinf.ase.pe;

public class Geldautomat {
	private static Geldautomat instance;
	GeldautomatState stateOfNoMoney = new StateOfNoMoney(this);
	GeldautomatState stateOfReady = new StateOfReady(this);
	GeldautomatState stateOfCardInside = new StateOfCardInside(this);
	GeldautomatState stateOfPinCorrect = new StateOfPinCorrect(this);
	
	private Geldautomat() {
		
	}
	
	public void resetAutomat() {
		this.state = stateOfNoMoney;
		this.state.setBargeld(0);
		this.state.setKarte(null);
		this.state.setPinFalsch(0);
		this.state.setPinKorrekt(false);
	}
	
	public GeldautomatState getStateOfPinCorrect() {
		return stateOfPinCorrect;
	}

	private GeldautomatState state = stateOfNoMoney;

	public void bestücken(int bargeld) {
		state.bestücken(bargeld);
	}
	
	public void einschieben(Karte karte) {
		state.einschieben(karte);
	}

	public void ausgeben() {
		state.ausgeben();
		System.out.println("Deine Karte wurde wieder ausgeworfen");
	}

	public void eingeben(String pin) {
		state.eingeben(pin);
	}

	public int auszahlen(int summe) {
		return state.auszahlen(summe);
	}

	public String info() {
		return state.info();
	}
	
	public int getBargeld() {
		return state.getBargeld();
	}

	public GeldautomatState getState() {
		return state;
	}

	public void setState(GeldautomatState state) {
		state.setBargeld(this.state.getBargeld());
		state.setKarte(this.state.getKarte());
		state.setPinFalsch(this.state.getPinFalsch());
		state.setPinKorrekt(this.state.isPinKorrekt());
		this.state = state;
	}

	public GeldautomatState getStateOfNoMoney() {
		return stateOfNoMoney;
	}

	public GeldautomatState getStateOfReady() {
		return stateOfReady;
	}

	public GeldautomatState getStateOfCardInside() {
		return stateOfCardInside;
	}

	public static Geldautomat getInstance() {
		if(instance == null) {
			instance = new Geldautomat();
		}
		return instance;
	}
	
	public boolean hasCardInserted() {
		return state.hasCardInserted();
	}
	
	public boolean isBestueckt() {
		return state.isBestueckt();
	}

}
