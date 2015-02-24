// Agent detectiveBlu in project labirintoInganno

/* Initial beliefs and rules */

/* Initial goals */

!getStartPosition.

/* Plans */

+!getStartPosition 
	: not startTrovato
		<- trovaEntrata; 
			!cercaArtefatti.

+!cercaArtefatti
	: startTrovato
		<-selezionaDirezione.
		
//+posizione(X,Y)[source(percept)]
//	: true
//		<- -+posizione(X,Y).

+direzione(D)[source(percept)] 
	: true
		<- controllo(D);
			!nextStep.
			
+!checkForArtefacts
	: true
		<- cercaArtefatto;
			!leggiArtefatto.			
+!nextStep 
	: direzioneNonPercorribile  & not fineGioco
		<- .print("Detective Blu sceglie nuova direzione"); selezionaDirezione.
		
+!nextStep
	: direzionePercorribile & not fineGioco
		<- .print("Detective Blu Avanza"); !checkForArtefacts.
		
+!nextStep 
	: direzioneNonPercorribile  &  fineGioco
		<- .print("Gioco concluso").
		
+!nextStep
	: direzionePercorribile &  fineGioco
		<- .print("Gioco concluso").

+fineGioco
		<- true. 			
	

			
+!leggiArtefatto
	: artefattoTrovato
		<- analizzaArtefatto.

+!leggiArtefatto
	: not artefattoTrovato
		<- selezionaDirezione.
/**
 * Se è la prima volta che incontra l'artefatto lo registra e lo invia al Velocista
 */
		
+artefattoScoperto(N,C,T,V)
	: not artefattoRegistrato(N,C,T,_)
		<- +artefattoRegistrato(N,C,T,V);
		.send(velocistaBlu,tell,artefattoRegistrato(N,C,T,V));
		selezionaDirezione.
		
//+artefattoScoperto(N,C,T,V,K) 
//	:not artefattoRegistrato(N,_,_,_,_) 
//		<- -+artefattoRegistrato(N,C,T,V,0);
//		selezionaDirezione.

/*
 * Se l'artefatto è già stato Registrato, lo aggiorno con le nuove caratteristiche, tranne per
 * il numero di volte utilizzato che valore di volte utilizzato che viene mantenuto dall'agente.
 * 
 * */
 		
+artefattoScoperto(N,C,T,V)
	: artefattoRegistrato(N,_,_,Z)
		<- -+artefattoRegistrato(N,C,T,Z);
		selezionaDirezione.
		
+artefatto(N)
	: artefattoRegistrato(N,C,T,V) & V == 3
		<- cambiaArtefatto(N,C,T). //Abbiamo tolto analizzaArtefatto.
									

+artefatto(N)
	: artefattoRegistrato(N,C,T,V) & V < 3
		<- R = V+1; -+artefattoRegistrato(N,C,T,R);
		.send(velocistaBlu,tell,artefattoModificato(N,C,T,R)).		

+artefatto(N)
	: artefattoRegistrato(N,C,T,V) & V > 3
 	<- R = 1; -+artefattoRegistrato(N,C,T,R);
		.send(velocistaBlu,tell,artefattoModificato(N,C,T,R)).		
