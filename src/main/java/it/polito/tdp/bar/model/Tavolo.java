package it.polito.tdp.bar.model;

public class Tavolo {

	private int nPosti ;
	private boolean occupato ;
	
	public Tavolo(int nPosti, boolean occupato) {
		super();
		this.nPosti = nPosti;
		this.occupato = occupato;
	}

	public boolean isOccupato() {
		return occupato;
	}

	public void setOccupato(boolean occupato) {
		this.occupato = occupato;
	}

	public int getnPosti() {
		return nPosti;
	}

	
	/*
	int tavolo_10;
	int tavolo_8;
	int tavolo_6;
	int tavolo_4;
	
	public Tavoli() {
		this.tavolo_10=2;
		this.tavolo_8=4;
		this.tavolo_6=4;
		this.tavolo_4=5;
	}*/
}
