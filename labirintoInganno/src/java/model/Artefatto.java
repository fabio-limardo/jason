package model;

public class Artefatto {
	
	private boolean stradaCorretta;
	private String name = "";
	private int[] pos = new int[2];
	private String cambio = "";
	private float trustability;
	
	
	public Artefatto(boolean sc, String n, int x, int y){
		stradaCorretta = sc;
		name = n;
		pos[0] = x;
		pos[1] = y;
		cambio = "originale";
		trustability = 0.5F ;
	}
	public void setStradaCorretta(boolean sc){
		stradaCorretta = sc;
	}
	
	public boolean isStradaCorretta(){
		return stradaCorretta;
	}
	public String getName(){
		return name;
	}
	public int[]  getPos(){
		return pos;
	}
	public String getCambio(){
		return cambio;
	}
	public float getTrustability() {
		return trustability;
	}
	public void setTrustability(float trustability) {
		this.trustability = trustability;
	}
	public void setCambio(String cambio) {
		this.cambio = cambio;
	}

}
