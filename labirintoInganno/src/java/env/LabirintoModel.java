package env;

import java.util.Random;

import view.LabirintoView;
import model.*;

public class LabirintoModel {
		private LabirintoView view; 
		private Matrice matrice = new Matrice();
		private final int dimX = 23;
		private final int dimY = 41;
		private Cella[][] table = new Cella[dimX][dimY];
		int positionX;
		int positionY;
		int [] position = new int[2];
		boolean atStart = false;
		boolean checkNextPosition = false;
		boolean makeAnotherChoice = false;
		boolean gotIt = false;
		String move;
		
		
		public LabirintoModel(){
			
		}
		
		public void initLabirintoLogico(){
			char s;
			for(int i=0;i<dimX;i++)
				for(int j=0;j<dimY;j++){
					table[i][j] = new Cella(i,j,false);
					s = matrice.getSimboloLabirinto(i,j);
					switch (s){
					case 'E': 
						table[i][j].setAttraversabile();
						table[i][j].setViaCorretta();
						table[i][j].setEntrata();
						break;
					case '*':
						table[i][j].setAttraversabile();
						table[i][j].setViaCorretta();
						break;
					case ' ':
						table[i][j].setAttraversabile();
						break;
					case 'U':
						table[i][j].setAttraversabile();
						table[i][j].setViaCorretta();
						table[i][j].setUscita();
						break;
					}
				}
			
		}
		
		public Cella getCella(int x, int y){
			try {
				return table[x][y];
			} catch (ArrayIndexOutOfBoundsException e) { 
				return null;
			}
		}
		
		public Cella[][] getTable() {
			return this.table;
		}
		
		public boolean trovaEntrata(){
			for(int i=0;i<dimX;i++)
				for(int j=0;j<dimY;j++){
					if(table[i][j].isEntrata()){
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
			
			//Cella cellaSupport = new Cella(0,0);
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
			switch (dir){
			case "moveUp": //muove sopra				
				cella = getCella(position[0]-1,position[1]);							
				break;
			case "moveRight" : //muove a destra		
				cella = getCella(position[0],position[1]+1);
				break;
			case "moveDown" : //muove in basso 
				cella = getCella(position[0]+1,position[1]);
				break;
			case "moveLeft" : //muove a sinistra
				cella = getCella(position[0],position[1]-1);
				break;
					
			}	
						
			try{
				if(cella.isAttraversabile()){
					gotIt = true; 
					position[0] = cella.getCoordinataX();
					position[1] = cella.getCoordinataY();
				}else 
					makeAnotherChoice = true;
			}catch(NullPointerException e){
					makeAnotherChoice = true;
			}
			return true;
		}

		public int[] getPosition() {
			return position;
		}

		public void setPosition(int[] position) {
			this.position = position;
		}
		
}