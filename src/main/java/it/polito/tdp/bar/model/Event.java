package it.polito.tdp.bar.model;

import java.time.Duration;


public class Event implements Comparable <Event> {

	public enum EventType {
		ARRIVO_GRUPPO_CLIENTI,
		TAVOLO_LIBERATO
	}
	
	private Duration time; // orario di arrivo rispetto all'inizio della simulazione
	private EventType type;
	private int numPersone;
	private Duration durata;
	private double tolleranza;
	private Tavolo tavolo;
	
	public Event(Duration time, EventType type, int numPersone, Duration durata, double tolleranza, Tavolo tavolo) {
		super();
		this.time = time;
		this.type = type;
		this.numPersone = numPersone;
		this.durata = durata;
		this.tolleranza = tolleranza;
		this.tavolo = tavolo;
	}
	
	
	public Duration getTime() {
		return time;
	}

	public EventType getType() {
		return type;
	}

	public int getNumPersone() {
		return numPersone;
	}

	public Duration getDurata() {
		return durata;
	}

	public double getTolleranza() {
		return tolleranza;
	}

	@Override
	public int compareTo(Event other) {
		return this.time.compareTo(other.time);
	}

	@Override
	public String toString() {
		return type + " [" + time + ", " + numPersone + "pp, " + durata + ", " + (int) (tolleranza * 100) + "%]";
	}

	public Tavolo getTavolo() {
		return tavolo;
	}
	
	
	
	
}
