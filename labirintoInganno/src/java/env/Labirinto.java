package env;

import java.util.logging.Logger;

import jason.asSyntax.Literal;
import jason.asSyntax.Structure;
import jason.environment.Environment;
import jason.environment.grid.Location;

public class Labirinto extends Environment {
	
	public static final Literal trovaEntrata = Literal.parseLiteral("trovaEntrata");
	public static final Literal selezionaDirezione = Literal.parseLiteral("selezionaDirezione");
	public static final Literal start = Literal.parseLiteral("startTrovato");
	public static final Literal artefatct = Literal.parseLiteral("artefattoTrovato");
	public static final Literal end = Literal.parseLiteral("fineGioco");
	
	
	public static final String posizione = "posizione(X,Y)";
	
	static Logger logger = Logger.getLogger(Labirinto.class.getName());
	LabirintoModel model;
	
	
	public void init(String[] args) { 
		model = new LabirintoModel();
		model.initLabirintoLogico();
		updatePercepts();
	}

	/**
	 * Aggiorna i percepts degli agenti in base allo stato attuale del Modello
	 * (LabirintoModel)
	 */
	private void updatePercepts(){
		log("Updating Percepts");
		clearPercepts();
		
		if (model.atStart){
			addPercept("velocistaRosso",start);
			model.atStart = false;
		}
		//if(model.checkNextPosition){
			addPercept("velocistaRosso",Literal.parseLiteral(posizione.replace("X,Y" ,""+  model.position[0] + "," + model.position[1] + "")));
			///}
				
		if(model.getCella(model.position[0], model.position[1]).getArtefatto() != null)
			addPercept("velocistaRosso",artefatct);
		
		if(model.getCella(model.position[0], model.position[1]).isUscita())
			addPercept("velocistaRosso",end);

	}
	
	/**
	 * E' possibile eseguire azioni sull'ambiente.
	 * Il valore booleano di ritorno indica la buona riuscita o meno dell'operazione
	 */
	public boolean executeAction(String ag, Structure action) {
		System.out.println("[" + ag + "] doing: " + action);
		boolean result = false;
		
		if(action.equals(trovaEntrata)){
			result = model.trovaEntrata();
		}else if(action.equals(selezionaDirezione)){
			result = model.selezionaDirezione();
		}
//		}else if (action.equals(moveUp)) { 
//			result = model.updateModel();
//		} else if (action.equals(moveRight)) {
//			result = model.updateModel();
//		}else if (action.equals(moveDown)) {
//			result = model.updateModel();
//		}else if (action.equals(moveLeft)) {
//			result = model.updateModel();
//		}
			/*
			 * E via discorrendo
			 */
			else {
				logger.info("Failed to execute action " + action);
			}
			// only if action completed successfully, update agents' percepts
			if (result) 
				updatePercepts();
			return result;
	}
	
	public void log(String aMessage){
		System.out.println(aMessage);
	}
}
