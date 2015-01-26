package env;

import java.util.logging.Logger;

import jason.asSyntax.Literal;
import jason.asSyntax.Structure;
import jason.environment.Environment;
import jason.environment.grid.Location;

public class Labirinto extends Environment {
	
	public static final Literal trovaEntrata = Literal.parseLiteral("trovaEntrata");
	public static final Literal selezionaDirezione = Literal.parseLiteral("selezionaDirezione");
	public static final Literal controllo = Literal.parseLiteral("controllo");
	public static final Literal start = Literal.parseLiteral("startTrovato");
	public static final Literal artefatct = Literal.parseLiteral("artefattoTrovato");
	public static final Literal end = Literal.parseLiteral("fineGioco");
	public static final Literal anotherChoice = Literal.parseLiteral("direzioneNonPercorribile");
	public static final Literal gotIt = Literal.parseLiteral("direzionePercorribile");
	
	public static final String posizione = "posizione(X,Y)";
	public static final String direzione = "direzione(D)";
	
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
		clearPercepts("velocistaRosso");
		
		if (model.atStart){
			addPercept("velocistaRosso",start);
			addPercept("velocistaRosso",Literal.parseLiteral(posizione.replace("X,Y" ,""+  model.position[0] + "," + model.position[1] + "")));
			model.atStart = false;
		}
		if(model.checkNextPosition){
			addPercept("velocistaRosso",Literal.parseLiteral(direzione.replace("D", model.move )));
			model.checkNextPosition = false;
		}
		if(model.makeAnotherChoice){
			addPercept("velocistaRosso",anotherChoice);
			model.makeAnotherChoice = false;
		}
		
		if(model.getCella(model.position[0], model.position[1]).getArtefatto() != null)
			addPercept("velocistaRosso",artefatct);
		
		if(model.getCella(model.position[0], model.position[1]).isUscita())
			addPercept("velocistaRosso",end);
						
		if(model.gotIt){
			addPercept("velocistaRosso",gotIt);
			addPercept("velocistaRosso",Literal.parseLiteral(posizione.replace("X,Y" ,""+  model.position[0] + "," + model.position[1] + "")));
			model.gotIt = false;
		}
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
		}else if (action.getFunctor().equals("controllo")) {
			String l = action.getTerm(0).toString();
			result = model.controllo(l);
		}
//		} else if (action.equals(moveRight)) {
//			result = model.updateModel()
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
