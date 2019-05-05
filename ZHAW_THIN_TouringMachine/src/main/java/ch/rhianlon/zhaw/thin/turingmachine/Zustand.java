package ch.rhianlon.zhaw.thin.turingmachine;

public class Zustand {

	private String name;
	private boolean akzeptierend;

	public Zustand(String name, boolean akzeptierend) {
		setName(name);
		setAkzeptierend(akzeptierend);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public boolean isAkzeptierend() {

		return akzeptierend;
	}

	public void setAkzeptierend(boolean akzeptierend) {

		this.akzeptierend = akzeptierend;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
