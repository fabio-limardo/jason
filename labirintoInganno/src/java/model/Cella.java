package model;

import java.util.ArrayList;

public class Cella {
	private int coordinataX;
	private int coordinataY;
	private boolean aperta;
	private boolean attraversabile = false;
	private boolean inPath = false; 
	private boolean viaCorretta;
	private boolean entrata = false;
	private boolean uscita = false;
	private boolean muro;
	private ArrayList<Cella> neighbors = new ArrayList<>();
	private Artefatto artefatto = null;
	
	public Cella(int x,int y){
		coordinataX = x;
		coordinataY = y;
	}
	
	public Cella(int x,int y, boolean attraversabile){
		coordinataX = x;
		coordinataY = y;
		this.attraversabile = attraversabile;
	}
	
	public int getCoordinataX() {
		return coordinataX;
	}

	public int getCoordinataY() {
		return coordinataY;
	}

	public Artefatto getArtefatto() {
		return artefatto;		
	}

	public void setArtefatto(Artefatto artefatto) {
		this.artefatto = artefatto;
	}
	public void setEntrata(){
		entrata = true;
	}
	public void setUscita(){
		uscita = true;
	}
	public boolean isUscita(){
		return uscita;
	}

	public boolean isEntrata() {
		return entrata;
	}

	public void setEntrata(boolean entrata) {
		this.entrata = entrata;
	}

	public boolean isAperta() {
		return aperta;
	}

	public void setAperta(boolean aperta) {
		this.aperta = aperta;
	}

	public boolean isMuro() {
		return muro;
	}

	public void setMuro() {
		this.muro = true;
	}
	public void addNeighbor(Cella other) {
        if (!this.neighbors.contains(other)) { // avoid duplicates
            this.neighbors.add(other);
        }
        if (!other.neighbors.contains(this)) { // avoid duplicates
            other.neighbors.add(this);
        }
    }
	
    // used in updateGrid()
    public boolean isCellBelowNeighbor() {
        return this.neighbors.contains(new Cella(this.coordinataX, this.coordinataY + 1));
    }
    // used in updateGrid()
    public boolean isCellRightNeighbor() {
        return this.neighbors.contains(new Cella(this.coordinataX + 1, this.coordinataY));
    }
    // useful Cell representation
    @Override
    public String toString() {
        return String.format("Cell(%s, %s)", coordinataX, coordinataY);
    }
    
	public boolean isInPath() {
		return inPath;
	}

	public void setInPath(boolean inPath) {
		this.inPath = inPath;
	}

	public boolean isViaCorretta() {
		return viaCorretta;
	}

	public void setViaCorretta() {
		viaCorretta = true;
	}

	public boolean isAttraversabile() {
		return attraversabile;
	}

	public void setAttraversabile() {
		this.attraversabile = true;
	}
	public void setAttraversabile2(boolean attraversabile) {
		this.attraversabile = attraversabile;
	}
	
	

	
}
