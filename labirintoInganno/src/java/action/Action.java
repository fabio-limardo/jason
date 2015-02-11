package action;
import java.util.ArrayList;
import java.util.Random;

import model.Cella;
import env.*;
import model.*;

public class Action {
	LabirintoModel labirinto;
	int [] position = new int[2];
	boolean atStart = false;
	boolean checkNextPosition = false;
	boolean makeAnotherChoice = false;
	boolean gotIt = false;
	String move = "";
	boolean fineGioco = false;
	boolean findArtefacts = false;
	boolean correctWay = false;
	boolean wrongWay = false;
	ArrayList<Artefatto> artefactsList = new ArrayList<Artefatto>();
	boolean artefattoAnalizzato = false;
	public Action(LabirintoModel labirinto){
		this.labirinto = labirinto;		
	}
	
	public boolean trovaEntrata(){
		for(int i=0;i<labirinto.getDimX();i++)
			for(int j=0;j<labirinto.getDimY();j++){
				if(labirinto.getTable()[i][j].isEntrata()){
					position[0]=i;
					position[1]=j;
					atStart = true;
					return true;
				}
			}			
		return false;
	}
	

	public boolean selezionaDirezione(){
		Random rnd = new Random();

		int direction = rnd.nextInt(4)+1;
		switch (direction){
		case 1: //muove sopra				
			move = "moveUp";						
			break;
		case 2 : //muove a destra		
			move = "moveRight";
			break;
		case 3 : //muove in basso 
			move = "moveDown";
			break;
		case 4 : //muove a sinistra
			move = "moveLeft";
			break;

		}	
		checkNextPosition = true;
		return true;
	}
	public boolean selezionaDirezione(String dir){
		Random rnd = new Random();

		int direction = rnd.nextInt(4)+1;
		switch (direction){
		case 1: //muove sopra				
			move = "moveUp";						
			break;
		case 2 : //muove a destra		
			move = "moveRight";
			break;
		case 3 : //muove in basso 
			move = "moveDown";
			break;
		case 4 : //muove a sinistra
			move = "moveLeft";
			break;

		}
		
		if(dir.equals(move))
			selezionaDirezione(dir);
		else
			checkNextPosition = true;
		return true;
	}

	public boolean controllo(String dir){
		Cella cella = new Cella(0,0);
		log("before switch");
		switch (dir){
		case "moveUp": //muove sopra				
			cella = labirinto.getCella(position[0]-1,position[1]);							
			break;
		case "moveRight" : //muove a destra		
			cella = labirinto.getCella(position[0],position[1]+1);
			break;
		case "moveDown" : //muove in basso 
			cella = labirinto.getCella(position[0]+1,position[1]);
			break;
		case "moveLeft" : //muove a sinistra
			cella = labirinto.getCella(position[0],position[1]-1);
			break;

		}	

		try{
			if(cella.isAttraversabile()){
				gotIt = true; 
				position[0] = cella.getCoordinataX();
				position[1] = cella.getCoordinataY();
				log("(" + cella.getCoordinataX() + "," + cella.getCoordinataY() + ")");
				
			}else 
				makeAnotherChoice = true;
		}catch(NullPointerException e){
			makeAnotherChoice = true;
		}
		return true;
	}
	
	public boolean cercaArtefatto(){
		Cella cella = labirinto.getCella(position[0],position[1]);
		if(cella.getArtefatto() != null)
			findArtefacts = true;
		return true;		
	}
	public boolean apriArtefatto(){
		Cella cella = labirinto.getCella(position[0],position[1]);
		if(cella.getArtefatto().isStradaCorretta())
			correctWay = true;
		else
			wrongWay = true;
		
		return true;
	}
	
	public boolean puntoFermo(String name, String correctness){
		//TODO
		return true;
	}
	
	public boolean analizzaArtefatto(){
		Cella cella = labirinto.getCella(position[0],position[1]);
		//artefactsList.add(cella.getArtefatto());
		artefattoAnalizzato = true;
		return true;
	}
	
	public boolean cambiaArtefatto(String name,String correctness,String trustability){
		log("siamo a cambia artefatto");
		return true;
	}
	
	public Cella[][] getTable(){
		return labirinto.getTable();
	}
	public Cella getCella(int x, int y){
		try {
			return labirinto.getTable()[x][y];
		} catch (ArrayIndexOutOfBoundsException e) { 
			return null;
		}
	}
	
	public int[] getPosition() {
		return position;
	}

	public void log(String aMessage){
		System.out.println(aMessage);
	}

	public boolean isAtStart() {
		return atStart;
	}

	public boolean isCheckNextPosition() {
		return checkNextPosition;
	}

	public boolean isMakeAnotherChoice() {
		return makeAnotherChoice;
	}

	public boolean isGotIt() {
		return gotIt;
	}

	public boolean isFineGioco() {
		return fineGioco;
	}

	public boolean isFindArtefacts() {
		return findArtefacts;
	}

	public boolean isCorrectWay() {
		return correctWay;
	}

	public boolean isWrongWay() {
		return wrongWay;
	}

	public LabirintoModel getLabirinto() {
		return labirinto;
	}

	public void setLabirinto(LabirintoModel labirinto) {
		this.labirinto = labirinto;
	}

	public String getMove() {
		return move;
	}

	public void setMove(String move) {
		this.move = move;
	}

	public void setPosition(int[] position) {
		this.position = position;
	}

	public void setAtStart(boolean atStart) {
		this.atStart = atStart;
	}

	public void setCheckNextPosition(boolean checkNextPosition) {
		this.checkNextPosition = checkNextPosition;
	}

	public void setMakeAnotherChoice(boolean makeAnotherChoice) {
		this.makeAnotherChoice = makeAnotherChoice;
	}

	public void setGotIt(boolean gotIt) {
		this.gotIt = gotIt;
	}

	public void setFineGioco(boolean fineGioco) {
		this.fineGioco = fineGioco;
	}

	public void setFindArtefacts(boolean findArtefacts) {
		this.findArtefacts = findArtefacts;
	}

	public void setCorrectWay(boolean correctWay) {
		this.correctWay = correctWay;
	}

	public void setWrongWay(boolean wrongWay) {
		this.wrongWay = wrongWay;
	}

	public ArrayList<Artefatto> getArtefactsList() {
		return artefactsList;
	}

	public void setArtefactsList(ArrayList<Artefatto> artefactsList) {
		this.artefactsList = artefactsList;
	}

	public boolean isArtefattoAnalizzato() {
		return artefattoAnalizzato;
	}

	public void setArtefattoAnalizzato(boolean artefattoAnalizzato) {
		this.artefattoAnalizzato = artefattoAnalizzato;
	}

}
