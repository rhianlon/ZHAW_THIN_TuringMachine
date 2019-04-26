package ch.rhianlon.zhaw.thin.turingmachine;

public class Zustand {

	private boolean akzeptierend;

	public Zustand(boolean akzeptierend) {
		setAkzeptierend(akzeptierend);
	}

	public boolean isAkzeptierend() {

		return akzeptierend;
	}

	public void setAkzeptierend(boolean akzeptierend) {

		this.akzeptierend = akzeptierend;
	}
}
