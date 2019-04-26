package ch.rhianlon.zhaw.thin.turingmachine;

public class Band {
	private int index;
	private char lesen;
	private char schreiben;
	private Richtung richtung;

	public Band(int index, char lesen, char schreiben, Richtung richtung) {
		setIndex(index);
		setLesen(lesen);
		setSchreiben(schreiben);
		setRichtung(richtung);
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void setLesen(char lesen) {
		this.lesen = lesen;
	}

	public char getLesen() {
		return lesen;
	}

	public void setSchreiben(char schreiben) {
		this.schreiben = schreiben;
	}

	public char getSchreiben() {
		return schreiben;
	}

	public void setRichtung(Richtung richtung) {
		if (richtung == null) {
			throw new NullPointerException("richtung is not specified");
		}
		this.richtung = richtung;
	}

	public Richtung getRichtung() {
		return richtung;
	}
}
