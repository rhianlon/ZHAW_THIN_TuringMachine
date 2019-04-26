package ch.rhianlon.zhaw.thin.turingmachine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testet die Klasse Zustand")
class TestZustand {
	@Test
	@DisplayName("Testet die Sette&Getter Methoden der Klasse Zustand")
	void test() {
		Zustand zustand1 = new Zustand(true);
		
		zustand1.setAkzeptierend(true);
		Assertions.assertEquals(true, zustand1.isAkzeptierend(), "Funktioniert halt nöd");
		zustand1.setAkzeptierend(false);
		Assertions.assertEquals(false, zustand1.isAkzeptierend(), "Funktioniert halt nöd");
		
	}

}
