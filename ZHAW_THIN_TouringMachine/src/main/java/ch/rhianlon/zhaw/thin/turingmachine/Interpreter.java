package ch.rhianlon.zhaw.thin.turingmachine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Interpreter {
	private static final int OFFSET = 30;
	private static final int OFFSET_LEFT = OFFSET;
	private static final int OFFSET_RIGHT = OFFSET;

	private final InterpreterModus modus;
	private final TuringMachine turingMachine;
	
	private boolean started;
	private Zustand currentZustand;
	private int uebergangsCounter;
	private int baender;

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

	public void turingMachineStarten(String eingabe) throws IOException {
		if (eingabe == null) {
			throw new NullPointerException("eingabe is not specified");
		}
		if (eingabe.length() == 0) {
			throw new IllegalArgumentException("eingabe darf nicht leer sein");
		}
		if (started) {
			throw new IllegalStateException("Maschine darf nicht 2x gestartet werden");
		}
		
		for (int i = 0; i < eingabe.length(); i++) {
			char zeichen = eingabe.charAt(i);
			if (!turingMachine.getEingabeAlphabet().contains(zeichen)) {
				throw new IllegalArgumentException(String.format("zeichen %s ist nicht im Eingabealphabet definitiert", String.valueOf(zeichen)));
			}
		}

		started = true;
		uebergangsCounter = 0;
		baender = 0;

		bandBeiBandIndex = new HashMap<>();
		positionBeiBandIndex = new HashMap<>();
		
		// Anzahl B�nder validieren
		baender = validateBaender();
		
		for (int i = 0; i < baender; i++) {
			getZeichen(i);
		}
		
		// Eingabe auf das erste Band schreiben
		for (int i = 0; i < eingabe.length(); i++) {
			char c = eingabe.charAt(i);
			setZeichen(0, c);
			shiftPosition(0, Richtung.RECHTS);
		}
		
		// Leseschreibkopf zur�ck an die Startposition setzten
		positionBeiBandIndex.put(0, 0);
		
		

		currentZustand = turingMachine.getStartZustand();
		
		print();
		takeABreak();
		
		while (!isFinished()) {
			for (UebergangsFunktion uebergangsFunktion : gimmeThemUebergangsFunktionen(currentZustand)) {
				List<Band> sort = new ArrayList<>(uebergangsFunktion.getBaender());
				sort.sort(Comparator.comparingInt(Band::getIndex));
				
				boolean match = true;
				for (Band band : sort) {
					int bandIndex = band.getIndex();
					char zeichen = getZeichen(bandIndex);
					if (zeichen != band.getLesen()) {
						match = false;
						break;
					}
				}
				
				if (match) {
					for (Band band : sort) {
						int bandIndex = band.getIndex();
						char zeichen = getZeichen(bandIndex);
						if (zeichen == band.getLesen()) {
							setZeichen(bandIndex, band.getSchreiben());
							shiftPosition(bandIndex, band.getRichtung());
						}
					}
					currentZustand = uebergangsFunktion.getZustandB();
					break;
				}
			}
			
			uebergangsCounter ++;
			
			print();
			takeABreak();
		}
		
		System.out.println();
		System.out.println();
		printLine();
		printLine();
		printLine();
		System.out.println("== TuringMachine beendet");
		System.out.println();
		
		System.out.println("Anzahl Zustandswechsel: " + uebergangsCounter);
		System.out.println("End-Zustand: " + currentZustand.getName());
		System.out.println();
		
		for (int i = 0; i < baender; i++) {
			System.out.print(i + ": ");
			for (int j = getMin(); j <= getMax(); j++) {
				System.out.print(getZeichen(i, j, false));
			}
			System.out.println();
		}
	}
	
	private int validateBaender() {
		int baender = 0;
		for (UebergangsFunktion uebergangsFunktion : turingMachine.getUebergangsFunktionen()) {
			int actualBaender = uebergangsFunktion.getBaender().size();
			if (baender == 0) {
				baender = actualBaender;
			} else if (actualBaender != baender) {
				throw new IllegalArgumentException(String.format(
					"Maschine muss pro Funktion genau %d Baender haben, es wurden jedoch %d gefunden", baender, actualBaender));
			}
		}
		
		return baender;
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
	
	private char getZeichen(int bandIndex, int at, boolean create) {
		Map<Integer, Character> band = getBand(bandIndex);

		if (band.containsKey(at)) {
			return band.get(at);

		} else {
			char leeresFeld = turingMachine.getLeeresFeld();
			if (create) {
				band.put(at, leeresFeld);
				return band.get(at);
			} else {
				return leeresFeld;
			}
		}
	}

	private void setZeichen(int bandIndex, char zeichen) {
		Map<Integer, Character> band = getBand(bandIndex);
		int position = getPosition(bandIndex);

		band.put(position, zeichen);
	}

	private void shiftPosition(int bandIndex, Richtung richtung) {
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
			boolean match = true;
			for (int bandIndex : bandBeiBandIndex.keySet()) {
				Band band = uebergangsFunktion.getBaender().stream().filter((val) -> bandIndex == val.getIndex()).findFirst().orElseGet(null);
				char zeichen = getZeichen(bandIndex);
				if (zeichen != band.getLesen()) {
					match = false;
					break;
				}
			}
			
			if (match) {
				isDeadEnd = false;
				break;
			}
		}
		
		return currentZustand.isAkzeptierend() && isDeadEnd; 
	}

	public boolean isStarted() {
		return started;
	}
	
	private void print() {
		List<Integer> sortKeys = new ArrayList<>(bandBeiBandIndex.keySet());
		sortKeys.sort(Comparator.naturalOrder());
		
		printLine();
		
		System.out.println(String.format("== Zustandswechsel NR: %d", uebergangsCounter));
		System.out.println(String.format("== Aktueller Zustand: %s", currentZustand.getName()));
		
		for (int i = 0; i < OFFSET_LEFT; i++) {
			System.out.print(" ");
		}
		System.out.println(".");
		
		for (int bandIndex : sortKeys) {
			int pos = getPosition(bandIndex);
			for (int i = pos - OFFSET_LEFT; i <= pos + OFFSET_RIGHT; i++) {
				System.out.print(getZeichen(bandIndex, i, false));
			}
			System.out.print("\n");
		}
	}
	
	private int getMin() {
		int result = Integer.MAX_VALUE;
		
		List<Integer> sortKeys = new ArrayList<>(bandBeiBandIndex.keySet());
		sortKeys.sort(Comparator.naturalOrder());
		
		for (int bandIndex : sortKeys) {
			for (Integer position : bandBeiBandIndex.get(bandIndex).keySet()) {
				if (position < result) {
					result = position;
				}
			}
		}
		
		return result - OFFSET_LEFT;
	}
	
	private int getMax() {
		int result = Integer.MIN_VALUE;
		
		List<Integer> sortKeys = new ArrayList<>(bandBeiBandIndex.keySet());
		sortKeys.sort(Comparator.naturalOrder());
		
		for (int bandIndex : sortKeys) {
			for (Integer position : bandBeiBandIndex.get(bandIndex).keySet()) {
				if (position > result) {
					result = position;
				}
			}
		}
		
		return result + OFFSET_RIGHT;
	}
	
	private void printLine() {
		for (int i = 0; i < OFFSET_LEFT + OFFSET_RIGHT + 1; i++) {
			System.out.print("=");
		}
		System.out.println();
	}
	
	private void takeABreak() throws IOException {
		if (modus == InterpreterModus.STEPMODUS) {
			System.out.println();
			System.out.println("== Press enter to continue");
			System.in.read();
		}
	}
}
