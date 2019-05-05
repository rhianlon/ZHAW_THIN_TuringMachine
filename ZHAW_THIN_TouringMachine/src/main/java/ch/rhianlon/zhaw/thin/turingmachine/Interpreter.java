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

	private final InterpreterModus modus;
	private final TuringMachine turingMachine;
	private boolean started;
	private Zustand currentZustand;
	private int zustandCounter;

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

		started = true;
		zustandCounter = 0;

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
				getZeichen(j, i, true);
			}
		}
		
		// Eingabe auf das erste Band schreiben
		for (int i = 0; i < eingabe.length(); i++) {
			char c = eingabe.charAt(i);
			setZeichen(0, c);
			shiftPosition(0, Richtung.RECHTS);
		}
		
		// Leseschreibkopf zurück an die Startposition setzten
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
			
			zustandCounter ++;
			
			print();
			takeABreak();
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
		
		int min = getMin();
		int max = getMax();
		
		System.out.println();
		System.out.println(String.format("==================== Zustandswechsel NR: %d", zustandCounter));
		System.out.println(String.format("==================== Aktueller Zustand: %s", currentZustand.getName()));
		for (int bandIndex : sortKeys) {
			
			for (int i=min; i < getPosition(bandIndex); i++) {
				System.out.print(" ");
			}
			System.out.print(".\n");
			
			for (int i=min; i < max; i++) {
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
		
		return result - 15;
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
		
		return result + 15;
	}
	
	private void takeABreak() throws IOException {
		if (modus == InterpreterModus.STEPMODUS) {
			System.in.read();
		}
	}
}
