package view;

import model.*;
import env.*;

public class LabirintoView {
	LabirintoModel model = new LabirintoModel();
	
	public LabirintoView(Cella[][] labirinto){
		
		for(int i=0;i<23;i++){
			for(int j=0;j<41;j++){				
				if(labirinto[i][j].isAttraversabile() && labirinto[i][j].isViaCorretta() )
					System.out.print("* ");
				if(labirinto[i][j].isAttraversabile() && !labirinto[i][j].isViaCorretta())
					System.out.print("  ");
				if(!labirinto[i][j].isAttraversabile() )
					System.out.print("X ");
			}
			System.out.print("\n");
		}
	}
	
	
	

}
