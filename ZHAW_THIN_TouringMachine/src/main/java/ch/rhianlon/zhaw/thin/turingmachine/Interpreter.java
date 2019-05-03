package ch.rhianlon.zhaw.thin.turingmachine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Interpreter {

	private final InterpreterModus modus;
	private final TuringMachine turingMachine;
	private boolean started;
	private Zustand currentZustand;

	private Map<Integer, Map<Integer, Character>> bandBeiBandIndex;
	private Map<Integer, Integer> positionBeiBandIndex;

	public Interpreter(InterpreterModus modus, TuringMachine turingMachine) {
		if (modus == null) {
			throw new NullPointerException("modus is not specified");
		}
		this.modus = modus;
		if (turingMachine == null) {
			throw new NullPointerException("turingMachine is not specified");
		}
		this.turingMachine = turingMachine;
	}

	public void turingMachineStarten(String eingabe) {
		if (eingabe == null) {
			throw new NullPointerException("eingabe is not specified");
		}
		if (eingabe.length() == 0) {
			throw new IllegalArgumentException("eingabe darf nicht leer sein");
		}
		if (started) {
			throw new IllegalStateException("Maschine darf nicht 2x gestartet werden");
		}

		started = true;

		bandBeiBandIndex = new HashMap<>();
		positionBeiBandIndex = new HashMap<>();
		
		// Anzahl Bänder validieren
		int baender = 0;
		for (UebergangsFunktion uebergangsFunktion : turingMachine.getUebergangsFunktionen()) {
			int actualBaender = uebergangsFunktion.getBaender().size();
			if (baender == 0) {
				baender = actualBaender;
			} else if (actualBaender != baender) {
				throw new IllegalArgumentException(String.format(
					"Maschine muss pro Funktion genau %d Bänder haben, es wurden jedoch %d gefunden", baender, actualBaender));
			}
		}
		
		// 30 Zeichen pro Band vorschreiben (-15 -> 15)
		for (int i = -15; i <= 15; i++) {
			for (int j = 0; j < baender; j ++) {
				getZeichen(j);
			}
		}
		
		// Eingabe auf das erste Band schreiben
		for (int i = 0; i < eingabe.length(); i++) {
			char c = eingabe.charAt(i);
			setZeichen(0, c);
		}

		currentZustand = turingMachine.getStartZustand();
		
		while (!isFinished()) {
			for (UebergangsFunktion uebergangsFunktion : gimmeThemUebergangsFunktionen(currentZustand)) {
				List<Band> sort = new ArrayList<>(uebergangsFunktion.getBaender());
				sort.sort(Comparator.comparingInt(Band::getIndex));
				for (Band band : sort) {
					int bandIndex = band.getIndex();
					char zeichen = getZeichen(bandIndex);
					if (zeichen == band.getLesen()) {
						setZeichen(bandIndex, band.getSchreiben());
						shiftPosition(bandIndex, band.getRichtung());
					}
				}
			}
		}
	}

	private Map<Integer, Character> getBand(int bandIndex) {
		if (bandBeiBandIndex.containsKey(bandIndex)) {
			return bandBeiBandIndex.get(bandIndex);
		} else {
			bandBeiBandIndex.put(bandIndex, new HashMap<Integer, Character>());
			return bandBeiBandIndex.get(bandIndex);
		}
	}

	private int getPosition(int bandIndex) {
		if (positionBeiBandIndex.containsKey(bandIndex)) {
			return positionBeiBandIndex.get(bandIndex);
		} else {
			positionBeiBandIndex.put(bandIndex, 0);
			return positionBeiBandIndex.get(bandIndex);
		}
	}

	private char getZeichen(int bandIndex) {
		Map<Integer, Character> band = getBand(bandIndex);
		int position = getPosition(bandIndex);

		if (band.containsKey(position)) {
			return band.get(position);

		} else {
			band.put(position, turingMachine.getLeeresFeld());
			return band.get(position);
		}
	}

	private void setZeichen(int bandIndex, char zeichen) {
		Map<Integer, Character> band = getBand(bandIndex);
		int position = getPosition(bandIndex);

		band.put(position, zeichen);
	}

	private void shiftPosition(int bandIndex, Richtung richtung) {
		Map<Integer, Character> band = getBand(bandIndex);
		int position = getPosition(bandIndex);

		switch (richtung) {
		case RECHTS:
			position += 1;
			positionBeiBandIndex.put(bandIndex, position);
			break;
		case LINKS:
			position -= 1;
			positionBeiBandIndex.put(bandIndex, position);
			break;
		case NICHTSUNTERNEHMEN:
			break;

		}

	}

	private Set<UebergangsFunktion> gimmeThemUebergangsFunktionen(Zustand zustand) {
		Set<UebergangsFunktion> result = new HashSet<>();
		for (UebergangsFunktion uebergangsFunktion : turingMachine.getUebergangsFunktionen()) {
			if (zustand == uebergangsFunktion.getZustandA()) {
				result.add(uebergangsFunktion);
			}

		}

		return result;
	}
	
	private boolean isFinished() {
		boolean isDeadEnd = true;
		for (UebergangsFunktion uebergangsFunktion : gimmeThemUebergangsFunktionen(currentZustand)) {
			for (Band band : uebergangsFunktion.getBaender()) {
				char zeichen = getZeichen(band.getIndex());
				if (zeichen == band.getLesen()) {
					isDeadEnd = false;
					break;
				}
			}
			
			if (isDeadEnd) {
				break;
			}
		}
		
		return currentZustand.isAkzeptierend() && isDeadEnd; 
	}

	public boolean isStarted() {
		return started;
	}

}
