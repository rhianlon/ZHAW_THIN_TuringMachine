package ch.rhianlon.zhaw.thin.turingmachine;

import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		try {
			Zustand q0 = new Zustand("q0", false);
			Zustand q1 = new Zustand("q1", false);
			Zustand q2 = new Zustand("q2", false);
			Zustand q3 = new Zustand("q3", false);
			Zustand q4 = new Zustand("q4", false);
			Zustand q5 = new Zustand("q5", false);
			Zustand q6 = new Zustand("q6", true);

			TuringMachine turingMachine = new TuringMachine(q0,'_');
			
			turingMachine.addZustand(q0);
			turingMachine.addZustand(q1);
			turingMachine.addZustand(q2);
			turingMachine.addZustand(q3);
			turingMachine.addZustand(q4);
			turingMachine.addZustand(q5);
			turingMachine.addZustand(q6);
			
			UebergangsFunktion q0Toq1 = new UebergangsFunktion(q0, q1);
			q0Toq1.addBand(new Band(0,'0','_',Richtung.RECHTS));
			q0Toq1.addBand(new Band(1,'_','_',Richtung.NICHTSUNTERNEHMEN));
			turingMachine.addUebergangsFunktion(q0Toq1);
			
			UebergangsFunktion q0Toq4A = new UebergangsFunktion(q0, q6);
			q0Toq4A.addBand(new Band(0,'1','1',Richtung.NICHTSUNTERNEHMEN));
			q0Toq4A.addBand(new Band(1,'_','_',Richtung.NICHTSUNTERNEHMEN));
			turingMachine.addUebergangsFunktion(q0Toq4A);
			
			UebergangsFunktion q0Toq4B = new UebergangsFunktion(q0, q6);
			q0Toq4B.addBand(new Band(0,'_','_',Richtung.NICHTSUNTERNEHMEN));
			q0Toq4B.addBand(new Band(1,'_','_',Richtung.NICHTSUNTERNEHMEN));
			turingMachine.addUebergangsFunktion(q0Toq4B);
			
			UebergangsFunktion q1Toq1 = new UebergangsFunktion(q1, q1);
			q1Toq1.addBand(new Band(0,'0','0',Richtung.RECHTS));
			q1Toq1.addBand(new Band(1,'_','_',Richtung.NICHTSUNTERNEHMEN));
			turingMachine.addUebergangsFunktion(q1Toq1);
			
			UebergangsFunktion q1Toq2 = new UebergangsFunktion(q1, q2);
			q1Toq2.addBand(new Band(0,'1','1',Richtung.RECHTS));
			q1Toq2.addBand(new Band(1,'_','_',Richtung.NICHTSUNTERNEHMEN));
			turingMachine.addUebergangsFunktion(q1Toq2);
			
			UebergangsFunktion q2Toq2 = new UebergangsFunktion(q2, q2);
			q2Toq2.addBand(new Band(0,'0','0',Richtung.RECHTS));
			q2Toq2.addBand(new Band(1,'_','0',Richtung.RECHTS));
			turingMachine.addUebergangsFunktion(q2Toq2);
			
			UebergangsFunktion q2Toq3 = new UebergangsFunktion(q2, q3);
			q2Toq3.addBand(new Band(0,'_','_',Richtung.LINKS));
			q2Toq3.addBand(new Band(1,'_','_',Richtung.NICHTSUNTERNEHMEN));
			turingMachine.addUebergangsFunktion(q2Toq3);
			
			UebergangsFunktion q3Toq3 = new UebergangsFunktion(q3, q3);
			q3Toq3.addBand(new Band(0,'0','0',Richtung.LINKS));
			q3Toq3.addBand(new Band(1,'_','_',Richtung.NICHTSUNTERNEHMEN));
			turingMachine.addUebergangsFunktion(q3Toq3);
			
			UebergangsFunktion q3Toq4 = new UebergangsFunktion(q3, q4);
			q3Toq4.addBand(new Band(0,'1','1',Richtung.LINKS));
			q3Toq4.addBand(new Band(1,'_','_',Richtung.NICHTSUNTERNEHMEN));
			turingMachine.addUebergangsFunktion(q3Toq4);
			
			UebergangsFunktion q4Toq5 = new UebergangsFunktion(q4, q5);
			q4Toq5.addBand(new Band(0,'0','0',Richtung.LINKS));
			q4Toq5.addBand(new Band(1,'_','_',Richtung.NICHTSUNTERNEHMEN));
			turingMachine.addUebergangsFunktion(q4Toq5);
			
			UebergangsFunktion q4Toq6 = new UebergangsFunktion(q4, q0);
			q4Toq6.addBand(new Band(0,'_','_',Richtung.RECHTS));
			q4Toq6.addBand(new Band(1,'_','_',Richtung.NICHTSUNTERNEHMEN));
			turingMachine.addUebergangsFunktion(q4Toq6);
			
			UebergangsFunktion q5Toq5 = new UebergangsFunktion(q5, q5);
			q5Toq5.addBand(new Band(0,'0','0',Richtung.LINKS));
			q5Toq5.addBand(new Band(1,'_','_',Richtung.NICHTSUNTERNEHMEN));
			turingMachine.addUebergangsFunktion(q5Toq5);
			
			UebergangsFunktion q5Toq0 = new UebergangsFunktion(q5, q0);
			q5Toq0.addBand(new Band(0,'_','_',Richtung.RECHTS));
			q5Toq0.addBand(new Band(1,'_','_',Richtung.NICHTSUNTERNEHMEN));
			turingMachine.addUebergangsFunktion(q5Toq0);
			
			UebergangsFunktion q6Toq6A = new UebergangsFunktion(q6, q6);
			q6Toq6A.addBand(new Band(0,'0','_',Richtung.RECHTS));
			q6Toq6A.addBand(new Band(1,'_','_',Richtung.NICHTSUNTERNEHMEN));
			turingMachine.addUebergangsFunktion(q6Toq6A);
			
			UebergangsFunktion q6Toq6B = new UebergangsFunktion(q6, q6);
			q6Toq6B.addBand(new Band(0,'1','_',Richtung.RECHTS));
			q6Toq6B.addBand(new Band(1,'_','_',Richtung.NICHTSUNTERNEHMEN));
			turingMachine.addUebergangsFunktion(q6Toq6B);
			
			turingMachine.addBandAlphabet('_');
			turingMachine.addBandAlphabet('0');
			turingMachine.addBandAlphabet('1');
			
			turingMachine.addEingabeAlphabet('0');
			turingMachine.addEingabeAlphabet('1');
			
			Interpreter interpreter = new Interpreter(InterpreterModus.LAUFMODUS,turingMachine);
			interpreter.turingMachineStarten("000001000");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
