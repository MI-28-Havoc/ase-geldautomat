package de.dhbw.tinf.ase.pe;

public class StateOfPinCorrect extends GeldautomatState{
	Geldautomat geldautomat;
	
	public StateOfPinCorrect(Geldautomat geldautomat) {
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
		
	}

	@Override
	int auszahlen(int summe) {
		if (summe < 5 || summe > 500) {
			throw new IllegalArgumentException(
					"Betrag muss zwischen 5 und 500 Taler liegen!");
		}

		if (isPinKorrekt()) {
			int ausbezahlt = 0;
			if (summe % 5 == 0) {
				
				//Wenn mehr verlangt als vorhanden...
				if (getBargeld()-summe < 0) {
					//Kontobestände kleiner 5 Taler können nicht ausbezahlt werden...
					if (getBargeld()-5 < 0 ) {
						throw new IllegalStateException("Es sind nicht genügend Taler im Automat vorhanden!");
					}
					while(getBargeld()-5 >= 0)
					{
						setBargeld(getBargeld() - 5);
						ausbezahlt += 5;
					}	
					
				}
				else {
					ausbezahlt = summe;
					setBargeld(getBargeld() - summe);
				}
				if (getBargeld() < 5) {
					this.geldautomat.setState(this.geldautomat.getStateOfNoMoney());
					setKarte(null);
					setPinKorrekt(false);
					System.out.println("Die Karte wird nun wieder ausgegeben!");
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

	@Override
	void ausgeben() {
		setKarte(null);
		setPinKorrekt(false);
		this.geldautomat.setState(this.geldautomat.getStateOfReady());
	}

	@Override
	String info() {
		return  "Abhebung bis zu " + getBargeld() + " Taler ist möglich";
	}
	
}
