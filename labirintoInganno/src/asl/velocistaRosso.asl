// Agent velocistaSquadraRossa in project labirintoInganno

/* Initial beliefs and rules */

/* Initial goals */

!getStartPosition.

/* Plans */

+!getStartPosition 
	: not startTrovato
		<- trovaEntrata ; .print("startTrovato"); 
			!trovaUscita.

+!trovaUscita 
	: startTrovato 
		<- selezionaDirezione.

+direzione(D)[source(percept)] 
	: true
		<- controllo(D);
			!nextStep.

//+posizione(X,Y)[source(percept)]
//	: true
//		<- -+posizione(X,Y).

+!checkForArtefacts
	: true
		<- cercaArtefatto;
			!leggiArtefatto.

+artefatto(N,C)[source(percept)]
	:  artefattoRegistrato(N,C,T,V) & T == 1
		<- puntoFermo(N,C); .send(detectiveRosso,tell,artefatto(N));
		 selezionaDirezione.
		
+artefatto(N,C)[source(percept)]
	:  artefattoRegistrato(N,C,T,V) & T == 0
		<-selezionaDirezione.
		
+artefatto(N,C)[source(percept)]
	:not  artefattoRegistrato(N,_,_,_)
		<- selezionaDirezione.
		
+artefattoModificato(N,C,T,V)[source(detectiveRosso)]
	: artefattoRegistrato(N,_,_,_)
		<- -+artefattoRegistrato(N,C,T,V); //aggiunto
		.send(detectiveRosso,untell,artefatto(N)).

+artefattoModificato(N,C,T,V)[source(detectiveRosso)]
	: not artefattoRegistrato(N,_,_,_)
		<- .print("Ehm....qualcosa non funziona").
						
+!leggiArtefatto
	: artefattoTrovato
		<- apriArtefatto.

+!leggiArtefatto
	: not artefattoTrovato
		<- selezionaDirezione.

+!nextStep
	: direzioneNonPercorribile  & not fineGioco
		<- .print("Non è possibile proseguire per la direzione desiderata"); 
		selezionaDirezione.
		
+!nextStep
	: direzionePercorribile & not fineGioco
		<- .print("Avanziamo");
		 	!checkForArtefacts.
+!nextStep 
	: direzioneNonPercorribile  &  fineGioco
		<- .print("Gioco concluso").
		
+!nextStep
	: direzionePercorribile &  fineGioco
		<- .print("Gioco concluso").
			
+fineGioco
		<- .print("Velocista Rosso ha vinto").
		
