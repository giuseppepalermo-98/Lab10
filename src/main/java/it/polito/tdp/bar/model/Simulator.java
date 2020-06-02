package it.polito.tdp.bar.model;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.PriorityQueue;

import it.polito.tdp.bar.model.Event.EventType;

public class Simulator {
//NOTAAAAAAAA: IN QUESTO ESERCIZIO POTEVO NON CREARE LA CLASSE TAVOLI E GESTIRE IL TUTTO CON UN ARRAY DI INTEGER
//IN MODO DA AVERE PIU' SEMPLICE LA GESTIONE DEI TAVOLI CON UN SEMPLICE FOR INVECE DI FARE TUTTI GLI IF
	//CODA DEGLI EVENTI
	private PriorityQueue<Event> queue = new PriorityQueue<>();
	
	//PARAMETRI DI SIMULAZIONE
	private Tavoli tavoli= new Tavoli();
	private int temp= (int) ((Math.random()*10)+1); 
	private Duration T_IN = Duration.of(temp, ChronoUnit.MINUTES);
	
	//MODELLO DEL MONDO
	private Tavoli tavoli_disp;
	
	//VALORI DA CALCOLARE
	private int clienti ;
	private int insoddisfatti ;
	private int soddisfatti;
	
	//METODI PER RITORNARE I VALORI
	public int getClienti() {
		return this.clienti;
	}
	
	public int getInsoddisfatti() {
		return this.insoddisfatti;
	}
	
	public int getSoddisfatti() {
		return this.soddisfatti;
	}
	
	public void run() {
		//PREPARAZIONE INIZIALE (EVENTI+MONDO)
		this.tavoli_disp=this.tavoli;
		this.clienti=this.insoddisfatti=this.soddisfatti=0;
		this.queue.clear();
		
		LocalTime oraArrivoCliente = LocalTime.of(7, 00);
		
		for(int i=0; i<2000; i++) {
			Event e= new Event(oraArrivoCliente, EventType.ARRIVO_GRUPPO_CLIENTI);
			this.queue.add(e);
			temp= (int) ((Math.random()*10)+1); 
			this.T_IN=  Duration.of(temp, ChronoUnit.MINUTES);
			oraArrivoCliente = oraArrivoCliente.plus(T_IN);
		}
		
		while(!this.queue.isEmpty()) {
			Event e= this.queue.poll();
			processEvent(e);
		}
		
		
	}

	private void processEvent(Event e) {
		switch(e.getTyep()) {
			case ARRIVO_GRUPPO_CLIENTI:
				Event evento;
				this.clienti=this.clienti+e.getNum_persone();
				int numeroso=e.getNum_persone();
				
				//VERIFICO SE HO ANCORA TAVOLI DISPONIBILI
				if(this.tavoli_disp.tavolo_10>0 && numeroso>=5) {
					this.soddisfatti=this.soddisfatti+numeroso;
					tavoli_disp.tavolo_10--;
					int temp_d=(int)(60+Math.random()*66);
					Duration durata=Duration.of(temp_d, ChronoUnit.MINUTES);
					evento=new Event(e.getTime().plus(durata), EventType.TAVOLO_LIBERATO);
					evento.setTipo_tavolo(10);
					this.queue.add(evento);
					
				}else if(this.tavoli_disp.tavolo_8>0 && numeroso>=4 && numeroso<=8) {
					this.soddisfatti=this.soddisfatti+numeroso;
					tavoli_disp.tavolo_8--;
					int temp_d=(int)(60+Math.random()*66);
					Duration durata=Duration.of(temp_d, ChronoUnit.MINUTES);
					evento=new Event(e.getTime().plus(durata), EventType.TAVOLO_LIBERATO);
					evento.setTipo_tavolo(8);
					this.queue.add(evento);
					
				}else if(this.tavoli_disp.tavolo_6>0 && numeroso>=3 && numeroso<=6) {
					this.soddisfatti=this.soddisfatti+numeroso;
					tavoli_disp.tavolo_6--;
					int temp_d=(int)(60+Math.random()*66);
					Duration durata=Duration.of(temp_d, ChronoUnit.MINUTES);
					evento=new Event(e.getTime().plus(durata), EventType.TAVOLO_LIBERATO);
					evento.setTipo_tavolo(6);
					this.queue.add(evento);
					
				}
				else if(this.tavoli_disp.tavolo_4>0 && numeroso>=2 && numeroso<=4) {
					this.soddisfatti+=numeroso;
					tavoli_disp.tavolo_4--;
					int temp_d=(int)(60+Math.random()*66);
					Duration durata=Duration.of(temp_d, ChronoUnit.MINUTES);
					evento=new Event(e.getTime().plus(durata), EventType.TAVOLO_LIBERATO);
					evento.setTipo_tavolo(4);
					this.queue.add(evento);
				}
					else {
						if(e.getTolleranza()<0.5)
							this.insoddisfatti += numeroso;
						else
							this.soddisfatti +=numeroso;
				}
				break;
				
			case TAVOLO_LIBERATO:
				if(e.getTipo_tavolo()==10)
					tavoli_disp.tavolo_10++;
				else if(e.getTipo_tavolo()==8)
					tavoli_disp.tavolo_8++;
				else if(e.getTipo_tavolo()==6)
					tavoli_disp.tavolo_6++;
				else
					tavoli_disp.tavolo_4++;
				break;
		}
	}
}
