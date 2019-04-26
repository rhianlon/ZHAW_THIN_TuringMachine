package ch.rhianlon.zhaw.thin.turingmachine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
@DisplayName("Testet die Klasse TuringMachine")
class TestTuringMachine {

	@Test
	@DisplayName("Testet die Setter/Getter/add/remove Methoden der Klasse TuringMachine")
	void test() {
		
		Zustand startZustand1 = new Zustand(false);
		char leeresFeld1 = 'c';
		
		Assertions.assertThrows(NullPointerException.class, () -> {
			new TuringMachine(null,leeresFeld1);
		});

		TuringMachine turingmachine1 = new TuringMachine(startZustand1, leeresFeld1);
		
		turingmachine1.setStartZustand(startZustand1);
		Assertions.assertEquals(startZustand1, turingmachine1.getStartZustand());
		
		turingmachine1.setLeeresFeld(leeresFeld1);
		Assertions.assertEquals(leeresFeld1, turingmachine1.getLeeresFeld());
		
		
		Zustand zustand1 = new Zustand(false);
		turingmachine1.addZustand(zustand1);
		Assertions.assertEquals(true, turingmachine1.getZustaende().contains(zustand1));

		turingmachine1.removeZustand(zustand1);
		Assertions.assertEquals(false, turingmachine1.getZustaende().contains(zustand1));
		
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			turingmachine1.getZustaende().add(zustand1);
		});
		
		Assertions.assertThrows(NullPointerException.class, () -> {
			turingmachine1.addZustand(null);
		});
		
		
		Character eingabeZeichen = 'a';
		turingmachine1.addEingabeAlphabet(eingabeZeichen);
		Assertions.assertEquals(true, turingmachine1.getEingabeAlphabet().contains(eingabeZeichen));

		turingmachine1.removeEingabeAlphabet(eingabeZeichen);
		Assertions.assertEquals(false, turingmachine1.getEingabeAlphabet().contains(eingabeZeichen));
		
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			turingmachine1.getEingabeAlphabet().add(eingabeZeichen);
		});
		
		Assertions.assertThrows(NullPointerException.class, () -> {
			turingmachine1.addEingabeAlphabet(null);
		});
		
		
		Character bandZeichen = 'a';
		turingmachine1.addBandAlphabet(bandZeichen);
		Assertions.assertEquals(true, turingmachine1.getBandAlphabet().contains(bandZeichen));

		turingmachine1.removeBandAlphabet(bandZeichen);
		Assertions.assertEquals(false, turingmachine1.getBandAlphabet().contains(bandZeichen));
		
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			turingmachine1.getBandAlphabet().add(bandZeichen);
		});
		
		Assertions.assertThrows(NullPointerException.class, () -> {
			turingmachine1.addBandAlphabet(null);
		});
		
		
		
		UebergangsFunktion uebergangsFunktion = new UebergangsFunktion(new Zustand(false), new Zustand(false));
		turingmachine1.addUebergangsFunktion(uebergangsFunktion);
		Assertions.assertEquals(true, turingmachine1.getUebergangsFunktionen().contains(uebergangsFunktion));

		turingmachine1.removeUebergangsFunktion(uebergangsFunktion);
		Assertions.assertEquals(false, turingmachine1.getUebergangsFunktionen().contains(uebergangsFunktion));
		
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			turingmachine1.getUebergangsFunktionen().add(uebergangsFunktion);
		});
		
		Assertions.assertThrows(NullPointerException.class, () -> {
			turingmachine1.addUebergangsFunktion(null);
		});
	}

}
