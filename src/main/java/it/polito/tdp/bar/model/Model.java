package it.polito.tdp.bar.model;

public class Model {
	private Simulator sim;
	
	public Model() {
		sim= new Simulator();
	}
	
	public void eseguiSimulator() {
		this.sim.init();
		this.sim.run();
	}
	
	public Statistiche getStatistiche() {
		return sim.getStat();
	}
}
