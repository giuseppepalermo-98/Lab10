package it.polito.tdp.bar.model;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import it.polito.tdp.bar.model.Event.EventType;

public class Simulator {

	//CODA DEGLI EVENTI
	private PriorityQueue<Event> queue;
	
	//PARAMETRI DI SIMULAZIONE
	private int NUM_EVENTI = 2000;
	private int T_MIN_ARRIVO_MAX = 10;
	private int NUM_PERSONE_MAX = 10;
	private int DURATA_MIN = 60;
	private int DURATA_MAX = 120;
	private double TOLLERANZA_MAX = 0.9;
	private double OCCUPAZIONE_MIN = 0.5; 
	
	//MODELLO DEL MONDO
	private List<Tavolo> tavoli_disp;
	
	//VALORI DA CALCOLARE
	private Statistiche stat;
	
	//Metodo per inizializzare i tavoli
	private void aggiungiTavolo(int num, int nPosti) {
		for (int i = 0; i < num; i++) {
			Tavolo t = new Tavolo(nPosti, false);
			this.tavoli_disp.add(t);
		}
	}
	
	private void caricaTavoli() {

		this.tavoli_disp = new ArrayList<>();

		aggiungiTavolo(2, 10);
		aggiungiTavolo(4, 8);
		aggiungiTavolo(4, 6);
		aggiungiTavolo(5, 4);
	}
	
	
	//Metodo per ritornare i parametri calcolati nella SIMULAZIONE
	public Statistiche getStat() {
		return stat;
	}
	
	public void init() {
		caricaTavoli();
		stat=new Statistiche();
		this.queue= new PriorityQueue<>();
		
		Duration arrivo = Duration.ofMinutes(0); // non ci sono orari dichiarati, parto a contare da 0

		for (int i = 0; i < this.NUM_EVENTI; i++) {
			int numPersone = (int) (Math.random() * this.NUM_PERSONE_MAX + 1);
			Duration durata = Duration
					.ofMinutes(this.DURATA_MIN + (int) (Math.random() * (this.DURATA_MAX - this.DURATA_MIN)));
			double tolleranza = Math.random() * this.TOLLERANZA_MAX;
			Event e = new Event(arrivo, EventType.ARRIVO_GRUPPO_CLIENTI, numPersone, durata, tolleranza, null);
			this.queue.add(e);
			arrivo = arrivo.plusMinutes(1 + (int) (Math.random() * this.T_MIN_ARRIVO_MAX));
		}
		
	}
	
	public void run() {
		while(!this.queue.isEmpty()) {
			Event e= this.queue.poll();
			processEvent(e);
		}
	}

	

	private void processEvent(Event e) {
		switch (e.getType()) {
		case ARRIVO_GRUPPO_CLIENTI:
			//Conto i clieti totali
			stat.addNumClientiTot(e.getNumPersone());
			
			//Cerco di sistemarli in un tavolo
			Tavolo trovato = null;
			for (Tavolo t : this.tavoli_disp) {
				if (!t.isOccupato() && t.getnPosti() >= e.getNumPersone()
						&& t.getnPosti() * this.OCCUPAZIONE_MIN <= e.getNumPersone()) {
					trovato = t;
					break; // il primo che trovo sarà il più piccolo che soddisfa il requisito
				}
			}
			
			if(trovato != null) {
				//Stampa per fare debug
				System.out.format("Sedute %d persone a tavolo da %d\n", e.getNumPersone(), trovato.getnPosti());
				//Ho il tavolo disponibile quindi li faccio sedere
				stat.addNumClientiSoddisfatti(e.getNumPersone());
				trovato.setOccupato(true);
				
				//Questi dopo un tot di tempo si alzeranno e generano un altro 'tipo' di evento
				queue.add(new Event(e.getTime().plus(e.getDurata()), EventType.TAVOLO_LIBERATO, e.getNumPersone(),
						e.getDurata(), e.getTolleranza(), trovato));
			}else {
				//Se non ho nessun tavolo libero ==> Accettano il bancone?
				double bancone = Math.random();
				if(bancone<=e.getTolleranza()) 
					//Si, vanno al bancone
					stat.addNumClientiSoddisfatti(e.getNumPersone());
				else
					//No, non vogliono andare al bancone
					stat.addNumClientiInsoddisfatti(e.getNumPersone());
			}
			
			break;
		case TAVOLO_LIBERATO:
			//Quando finiscono la sosta l'unica cosa che devo fare è liberare il tavolo
			e.getTavolo().setOccupato(false);
			break;
	}
	}
}