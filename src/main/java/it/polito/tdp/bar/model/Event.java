package it.polito.tdp.bar.model;

import java.time.LocalTime;

public class Event implements Comparable <Event> {

	public enum EventType {
		ARRIVO_GRUPPO_CLIENTI, TAVOLO_LIBERATO
	}
	
	private LocalTime time;
	private EventType tyep;
	private int num_persone;
	private float tolleranza;
	private int tipo_tavolo;
	
	public Event(LocalTime time, EventType tyep) {
		super();
		this.time = time;
		this.tyep = tyep;
		this.num_persone=(int)((Math.random()*10)+1);
		this.tolleranza=(float) Math.random();
		this.tipo_tavolo=-1;
	}
	
	
	
	public int getTipo_tavolo() {
		return tipo_tavolo;
	}



	public void setTipo_tavolo(int tipo_tavolo) {
		this.tipo_tavolo = tipo_tavolo;
	}



	public float getTolleranza() {
		return tolleranza;
	}


	public int getNum_persone() {
		return num_persone;
	}



	public void setNum_persone(int num_persone) {
		this.num_persone = num_persone;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public EventType getTyep() {
		return tyep;
	}

	public void setTyep(EventType tyep) {
		this.tyep = tyep;
	}

	@Override
	public int compareTo(Event e) {
		return e.time.compareTo(this.time);
	}

	
	
	
	
}
