package ch.rhianlon.zhaw.thin.turingmachine;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TuringMachine {
	private Set<Zustand> zustaende;
	private Zustand startZustand;
	private Set<Character> eingabeAlphabet;
	private Set<Character> bandAlphabet;
	private char leeresFeld;
	private Set<UebergangsFunktion> uebergangsFunktionen;

	public TuringMachine(Zustand startZustand, char leeresFeld) {
		setStartZustand(startZustand);
		setLeeresFeld(leeresFeld);
		this.zustaende = new HashSet<>();
		this.eingabeAlphabet = new HashSet<>();
		this.bandAlphabet = new HashSet<>();
		this.uebergangsFunktionen = new HashSet<>();
	}

	public Zustand getStartZustand() {
		return startZustand;
	}

	public void setStartZustand(Zustand startZustand) {
		if (startZustand == null) {
			throw new NullPointerException("startZustand is not specified");
		}
		this.startZustand = startZustand;
	}

	public char getLeeresFeld() {
		return leeresFeld;
	}

	public void setLeeresFeld(char leeresFeld) {
		this.leeresFeld = leeresFeld;
	}

	public Set<Zustand> getZustaende() {
		return Collections.unmodifiableSet(zustaende);
	}

	public void addZustand(Zustand zustand) {
		if (zustand == null) {
			throw new NullPointerException("Zustand is not specified");
		}
		zustaende.add(zustand);
	}

	public void removeZustand(Zustand zustand) {
		zustaende.remove(zustand);
	}

	public Set<Character> getEingabeAlphabet() {
		return Collections.unmodifiableSet(eingabeAlphabet);
	}

	public void addEingabeAlphabet(Character c) {
		if (c == null) {
			throw new NullPointerException("Eingabealphabet is not specified");
		}
		eingabeAlphabet.add(c);
	}

	public void removeEingabeAlphabet(Character c) {
		eingabeAlphabet.remove(c);
	}

	public Set<Character> getBandAlphabet() {
		return Collections.unmodifiableSet(bandAlphabet);
	}

	public void addBandAlphabet(Character c) {
		if (c == null) {
			throw new NullPointerException("Bandalphabet is not specified");
		}
		bandAlphabet.add(c);
	}

	public void removeBandAlphabet(Character c) {
		bandAlphabet.remove(c);
	}

	public Set<UebergangsFunktion> getUebergangsFunktionen() {
		return Collections.unmodifiableSet(uebergangsFunktionen);
	}

	public void addUebergangsFunktion(UebergangsFunktion uebergangsFunktion) {
		if (uebergangsFunktion == null) {
			throw new NullPointerException("Uebrgangsfunktion is not specified");
		}
		uebergangsFunktionen.add(uebergangsFunktion);
	}

	public void removeUebergangsFunktion(UebergangsFunktion uebergangsFunktion) {
		uebergangsFunktionen.remove(uebergangsFunktion);
	}
}
