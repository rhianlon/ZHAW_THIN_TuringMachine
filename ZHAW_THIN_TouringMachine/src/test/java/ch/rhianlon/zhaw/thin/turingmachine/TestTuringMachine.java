package ch.rhianlon.zhaw.thin.turingmachine;

import static org.junit.jupiter.api.Assertions.*;

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
		
	}

}
