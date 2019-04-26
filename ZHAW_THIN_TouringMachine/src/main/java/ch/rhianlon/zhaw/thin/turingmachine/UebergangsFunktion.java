package ch.rhianlon.zhaw.thin.turingmachine;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class UebergangsFunktion {

	private Zustand zustandA;
	private Zustand zustandB;
	private Set<Band> bänder;

	public UebergangsFunktion(Zustand zustandA, Zustand zustandB) {
		setZustandA(zustandA);
		setZustandB(zustandB);
		this.bänder = new HashSet<>();
	}

	public Zustand getZustandA() {
		return zustandA;
	}

	public void setZustandA(Zustand zustandA) {
		if (zustandA == null) {
			throw new NullPointerException("zustandA is not specified");
		}
		this.zustandA = zustandA;
	}

	public Zustand getZustandB() {
		return zustandB;
	}

	public void setZustandB(Zustand zustandB) {
		if (zustandB == null) {
			throw new NullPointerException("zustandB is not specified");
		}
		this.zustandB = zustandB;

	}

	public Set<Band> getBänder() {
		return Collections.unmodifiableSet(bänder);
	}

	public void addBand(Band band) {
		if (band == null) {
			throw new NullPointerException("band is not specified");
		}
		bänder.add(band);
	}

	public void removeBand(Band band) {
		bänder.remove(band);
	}

}
