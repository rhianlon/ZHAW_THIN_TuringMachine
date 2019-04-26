package ch.rhianlon.zhaw.thin.turingmachine;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testet die Klasse 'Band'")
class TestBand {
	@Test
	@DisplayName("Testet die Setter und Getter Methoden")
	void test() {
		
		Assertions.assertThrows(NullPointerException.class, () -> {
			new Band(1, 'P', 'L', null);
		});
		Band band1 = new Band(2,'T','F',Richtung.LINKS);
		band1.setIndex(1);
		Assertions.assertEquals(1, band1.getIndex(), "index wird falsch gesetzt");
		band1.setLesen('P');
		Assertions.assertEquals('P', band1.getLesen(), "Falsch gläse yo");
		band1.setSchreiben('L');
		Assertions.assertEquals('L', band1.getSchreiben(), "Falsch gschriebä yo");
		band1.setRichtung(Richtung.RECHTS);
		Assertions.assertEquals(Richtung.RECHTS, band1.getRichtung(), "Falschi Richtig yo");

		Assertions.assertThrows(NullPointerException.class, () -> {
			band1.setRichtung(null);
		});
	}

}
