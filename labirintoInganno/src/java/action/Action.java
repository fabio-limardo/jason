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
	int[] posPuntoFermo = new int[2];
	boolean artefattoAnalizzato = false;
	boolean usaPuntoFermo;
	Artefatto artefattoUtilizzato;
	boolean vincoloProva;
	boolean vincoloArtefatto0;
	boolean vincoloArtefatto1;
	boolean vincoloArtefatto2;
	boolean vincoloArtefatto3;
	boolean vincoloArtefatto4;
	boolean vincoloArtefatto5;
	boolean corretezzaArtefatto0;
	boolean corretezzaArtefatto1;
	boolean corretezzaArtefatto2;
	boolean corretezzaArtefatto3;
	boolean corretezzaArtefatto4;
	boolean corretezzaArtefatto5;
	private String nameArt;
	
	
	
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
		Cella cellaSupporto;
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
	
	
	public boolean controllo(String dir){
		Cella cella = new Cella(0,0);
		int[] posSupporto = new int[2];
		log("before switch");
		try{
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
		}catch(ArrayIndexOutOfBoundsException e){
			makeAnotherChoice = true;
			return true;
		}
		try{
			if(cella.isAttraversabile()){
				if(usaPuntoFermo){
					for(int i=0;i<artefactsList.size();i++){
						if(artefactsList.get(i).getName().equals("prova")){
							vincoloProva = true;												
						}
						if(artefactsList.get(i).getName().equals("artefatto0")){
							vincoloArtefatto0 = true;
							corretezzaArtefatto0 = artefactsList.get(i).isStradaCorretta();		
							
						}
						if(artefactsList.get(i).getName().equals("artefatto1")){
							vincoloArtefatto1 = true;
							corretezzaArtefatto1 = artefactsList.get(i).isStradaCorretta();		
						}
						if(artefactsList.get(i).getName().equals("artefatto2")){
							vincoloArtefatto2 = true;
							corretezzaArtefatto2 = artefactsList.get(i).isStradaCorretta();		
						}
						if(artefactsList.get(i).getName().equals("artefatto3")){
							vincoloArtefatto3 = true;
							corretezzaArtefatto3 = artefactsList.get(i).isStradaCorretta();		
						}
						if(artefactsList.get(i).getName().equals("artefatto4")){
							vincoloArtefatto4 = true;
							corretezzaArtefatto4 = artefactsList.get(i).isStradaCorretta();		
						}
						if(artefactsList.get(i).getName().equals("artefatto5")){
							vincoloArtefatto5 = true;
							corretezzaArtefatto5 = artefactsList.get(i).isStradaCorretta();		
						}
					}
					posSupporto[0] = cella.getCoordinataX();
					posSupporto[1] = cella.getCoordinataY(); 
					
					checkPositionUnderCostraint(posSupporto,corretezzaArtefatto0,
							corretezzaArtefatto1,corretezzaArtefatto2,corretezzaArtefatto3,corretezzaArtefatto4,corretezzaArtefatto5);
				}
				else{
					gotIt = true; 
					position[0] = cella.getCoordinataX();
					position[1] = cella.getCoordinataY();
					log("(" + cella.getCoordinataX() + "," + cella.getCoordinataY() + ")");
					return true;
				}
				
				
			}else 
				makeAnotherChoice = true;
		}catch(NullPointerException e){
				makeAnotherChoice = true;
		}
		return true;
	}
	
	private void  checkPositionUnderCostraint(int[] position, boolean cA0, boolean cA1, boolean cA2, boolean cA3, boolean cA4, boolean cA5){
		//TODO
		if(vincoloProva & position[0] == 1 & position[1] == 0){
			this.position[0] = 2;
			this.position[1] = 0;
			return;
		}
		if(vincoloArtefatto0 & position[0] == 10 & position[1] == 4){
			if(cA0){
			this.position[0] = 10;
			this.position[1] = 5;
			}else
			{
				this.position[0] = 10;
				this.position[1] = 3;
			}
			return;
		}
		if(vincoloArtefatto1 & position[0] == 18 & position[1] == 15){
			if(cA1){
			this.position[0] = 19;
			this.position[1] = 15;
			}else
			{
				this.position[0] = 18;
				this.position[1] = 16;
				
			}
			return;
		}
		if(vincoloArtefatto2 & position[0] == 7 & position[1] == 23){
			if(cA2){
			this.position[0] = 8;
			this.position[1] = 23;
			}else
			{
				this.position[0] = 6;
				this.position[1] = 23;
			}
			return;
		}
		if(vincoloArtefatto3 & position[0] == 8 & position[1] == 3){
			if(!cA3){
			this.position[0] = 9;
			this.position[1] = 3;
			}else
			{
				this.position[0] = 7;
				this.position[1] = 3;
			}
			return;
		}
		if(vincoloArtefatto4 & position[0] == 3 & position[1] == 18){
			if(!cA4){
			this.position[0] = 3;
			this.position[1] = 17;
			}else
			{
				this.position[0] = 4;
				this.position[1] = 18;
			}
			return;
		}
		if(vincoloArtefatto5 & position[0] == 6 & position[1] == 28){
			if(!cA5){
				this.position[0] = 6;
				this.position[1] = 27;
			}else
			{
				this.position[0] = 6;
				this.position[1] = 29;
			}
			return;
		}
		this.position[0] = position[0];
		this.position[1] = position[1];
		gotIt = true;
	}
	
	public boolean cercaArtefatto(){
		Cella cella = labirinto.getCella(position[0],position[1]);
		if(cella.getArtefatto() != null)
			findArtefacts = true;
		return true;		
	}
	public boolean apriArtefatto(){
		Cella cella = labirinto.getCella(position[0],position[1]);
		nameArt = cella.getArtefatto().getName();
		if(cella.getArtefatto().isStradaCorretta())
			correctWay = true;
		else
			wrongWay = true;
		
		return true;
	}
	
	public boolean puntoFermo(String name, String correctness){
		
		for(int i=0;i<labirinto.getArtefattoArray().length;i++){
			if(name.equals(labirinto.getArtefattoArray()[i].getName())){
				artefattoUtilizzato = labirinto.getArtefattoArray()[i];
				posPuntoFermo[0] = labirinto.getArtefattoArray()[i].getPos()[0];
				posPuntoFermo[1] = labirinto.getArtefattoArray()[i].getPos()[1];
				artefactsList.add(artefattoUtilizzato);
			}
			
		}
		usaPuntoFermo = true;
		return true;
	}
	
	public boolean analizzaArtefatto(){
		Cella cella = labirinto.getCella(position[0],position[1]);
		artefattoAnalizzato = true;
		return true;
	}
	
	public boolean cambiaArtefatto(String name,String correctness,String trustability, String agent){
		log("siamo a cambia artefatto");
		//TODO
		for(int i=0;i<labirinto.getArtefattoArray().length;i++){
			if(labirinto.getArtefattoArray()[i].getName().equals(name)){
				labirinto.getArtefattoArray()[i].setStradaCorretta(!labirinto.getArtefattoArray()[i].isStradaCorretta());
				if(agent.equals("detectiveRosso")){
					float t = labirinto.getArtefattoArray()[i].getTrustability();
					t = t + 0.1f;
					labirinto.getArtefattoArray()[i].setTrustability(t) ;
				}else
				{
					float t = labirinto.getArtefattoArray()[i].getTrustability();
					t = t - 0.1f;
					labirinto.getArtefattoArray()[i].setTrustability(t);
				}
				labirinto.getArtefattoArray()[i].setCambio(agent);
					
			}
		}
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


	public int[] getPosPuntoFermo() {
		return posPuntoFermo;
	}


	public void setPosPuntoFermo(int[] posPuntoFermo) {
		this.posPuntoFermo = posPuntoFermo;
	}


	public String getNameArt() {
		return nameArt;
	}

}
