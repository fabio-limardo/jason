package env;

import java.util.logging.Logger;

import jason.asSyntax.Literal;
import jason.asSyntax.Structure;
import jason.environment.Environment;
import jason.environment.grid.Location;
import action.*;

public class Labirinto extends Environment {

	public static final Literal trovaEntrata = Literal.parseLiteral("trovaEntrata");
	public static final Literal debug = Literal.parseLiteral("maPercheNonFunzionaMaiNiente");
	public static final Literal selezionaDirezione = Literal.parseLiteral("selezionaDirezione");
	public static final Literal controllo = Literal.parseLiteral("controllo");
	public static final Literal start = Literal.parseLiteral("startTrovato");
	public static final Literal artefatct = Literal.parseLiteral("artefattoTrovato");
	public static final Literal end = Literal.parseLiteral("fineGioco");
	public static final Literal anotherChoice = Literal.parseLiteral("direzioneNonPercorribile");
	public static final Literal gotIt = Literal.parseLiteral("direzionePercorribile");
	public static final Literal cercaArtefatto = Literal.parseLiteral("cercaArtefatto");
	public static final Literal apriArtefatto = Literal.parseLiteral("apriArtefatto");
	public static final Literal analizzaArtefatto = Literal.parseLiteral("analizzaArtefatto");



	public static final String posizione = "posizione(X,Y)";
	public static final String direzione = "direzione(D)";
	public static final String artefatto = "artefatto(name,correctness)";

	static Logger logger = Logger.getLogger(Labirinto.class.getName());
	LabirintoModel labirintModel;
	VelRosso velRosso;
	DetRosso detRosso;
	VelBlu velBlu;
	DetBlu detBlu;



	public void init(String[] args) { 
		labirintModel = new LabirintoModel();
		velRosso = new VelRosso(labirintModel);
		detRosso = new DetRosso(labirintModel);
		velBlu = new VelBlu(labirintModel);
		detBlu = new DetBlu(labirintModel);

	}

	/**
	 * Aggiorna i percepts degli agenti in base allo stato attuale del Modello
	 * (LabirintoModel)
	 */
	private void updatePercepts(String agent){
		log("Updating Percepts velocistaRosso");
		Action model = new Action(labirintModel);

		switch(agent){
		case "velocistaRosso":
			model = velRosso;
			break;
		case "detectiveRosso":
			model = detRosso;
			break;
		case "velocistaBlu":
			model = velBlu;
			break;
		case "detectiveBlu":
			model = detBlu;
			break;
		}
		clearPercepts(agent);
		//removePercept(agent,Literal.parseLiteral("posizione(X,Y)"));

		if (model.isAtStart()){
			addPercept(agent,start);
			addPercept(agent,Literal.parseLiteral(posizione.replace("X,Y" ,""+  model.getPosition()[0] + "," + model.getPosition()[1] + "")));
			model.setAtStart(false);
		}
		if(model.isCheckNextPosition()){
			addPercept(agent,Literal.parseLiteral(direzione.replace("D", model.getMove() )));
			model.setCheckNextPosition(false);
		}
		if(model.isMakeAnotherChoice() && !model.getCella(model.getPosition()[0], model.getPosition()[1]).isUscita()){
			addPercept(agent,anotherChoice);
			model.setMakeAnotherChoice(false);
		}		
		if(model.isFindArtefacts())
			addPercept(agent,artefatct);

		if(model.getCella(model.getPosition()[0], model.getPosition()[1]).isUscita())
			addPercept(agent,end);

		if(model.isGotIt() && !model.getCella(model.getPosition()[0], model.getPosition()[1]).isUscita()){
			addPercept(agent,gotIt);
			addPercept(agent,Literal.parseLiteral(posizione.replace("X,Y" ,""+  model.getPosition()[0] + "," + model.getPosition()[1] + "")));
			model.setGotIt(false);
		}
		
		if(model.isFineGioco()){
			addPercept(agent,Literal.parseLiteral("fineGioco") );
		}
		
		if(model.isCorrectWay()){
			addPercept(agent,Literal.parseLiteral(artefatto.replace("name,correctness" ,
					""+  model.getTable()[model.getPosition()[0]][model.getPosition()[1]].getArtefatto().getName() + "," +"1")));
			model.setCorrectWay(false);
		}
		
		if(model.isWrongWay()){
			addPercept(agent,Literal.parseLiteral(artefatto.replace("name,correctness" ,
					""+  model.getTable()[model.getPosition()[0]][model.getPosition()[1]].getArtefatto().getName() + "," +"0")));
			model.setWrongWay(false);
		}

	}

	/**
	 * E' possibile eseguire azioni sull'ambiente.
	 * Il valore booleano di ritorno indica la buona riuscita o meno dell'operazione
	 */
	
	public boolean executeAction(String ag, Structure action) {
		System.out.println("[" + ag + "] doing: " + action);
		boolean result = false;
		Action model = new Action(labirintModel);
		 
		switch(ag){
		case "velocistaRosso":
			model = velRosso;
			break;
		case "detectiveRosso":
			model = detRosso;
			break;
		case "velocistaBlu":
			model = velBlu;
			break;
		case "detectiveBlu":
			model = detBlu;
			break;
		}

		if(action.equals(trovaEntrata)){
			result = model.trovaEntrata();
		}else if(action.equals(selezionaDirezione)){
			result = model.selezionaDirezione();
		}else if (action.getFunctor().equals("controllo")) {
			String l = action.getTerm(0).toString();
			result = model.controllo(l);
		}else if(action.equals(cercaArtefatto)){
			result = model.cercaArtefatto();
		}else if(action.equals(apriArtefatto)){
			result = model.apriArtefatto();
		}else if(action.getFunctor().equals("puntoFermo")){
			String arg0 = action.getTerm(0).toString();
			String arg1 = action.getTerm(1).toString();
			result = model.puntoFermo(arg0, arg1); 
		}else if(action.equals(analizzaArtefatto)){
			result = model.analizzaArtefatto();
		}else if(action.getFunctor().equals("cambiaArtefatto")){
			String arg0 = action.getTerm(0).toString();
			String arg1 = action.getTerm(1).toString();
			String arg2 = action.getTerm(2).toString();
			result = model.cambiaArtefatto(arg0, arg1,arg2);
		}
		else {
			logger.info(ag +": Failed to execute action " + action);
		}

		if (result) 
			updatePercepts(ag);
		return result;

	}

	public void log(String aMessage){
		System.out.println(aMessage);
	}
}
