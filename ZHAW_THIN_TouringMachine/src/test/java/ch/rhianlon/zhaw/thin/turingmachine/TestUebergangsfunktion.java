package ch.rhianlon.zhaw.thin.turingmachine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testet die Klasse Uebergangsfunktion")
class TestUebergangsfunktion {

	@Test
	@DisplayName("Testet die Getter und Setter Methoden und add & remove von Set")
	void test() {
		Zustand zustandA = new Zustand(true);
		Zustand zustandB = new Zustand(false);
		
		Assertions.assertThrows(NullPointerException.class, () -> {
			new UebergangsFunktion(null,zustandB);
		});
		Assertions.assertThrows(NullPointerException.class, () -> {
			new UebergangsFunktion(zustandA,null);
		});
		
		Assertions.assertThrows(NullPointerException.class, () -> {
			new UebergangsFunktion(zustandA,zustandB).addBand(null);
		});
			
		UebergangsFunktion uebergangsFunktion1 = new UebergangsFunktion(zustandA, zustandB);
		
		uebergangsFunktion1.setZustandA(zustandA);
		Assertions.assertEquals(zustandA, uebergangsFunktion1.getZustandA(), "ZustandA falsch");
		
		uebergangsFunktion1.setZustandB(zustandB);
		Assertions.assertEquals(zustandB, uebergangsFunktion1.getZustandB(), "ZustandB falsch");

		Band band1 = new Band(2,'T','F',Richtung.LINKS);
		uebergangsFunktion1.addBand(band1);
		Assertions.assertEquals(true,uebergangsFunktion1.getBänder().contains(band1),"Bänder fllllasch");
		
		uebergangsFunktion1.removeBand(band1);
		Assertions.assertEquals(false,uebergangsFunktion1.getBänder().contains(band1),"Bänder fllllasch");
		
		Band band2 = new Band(4,'Q','L',Richtung.RECHTS);
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			uebergangsFunktion1.getBänder().add(band2);
		});
		
	}

}
