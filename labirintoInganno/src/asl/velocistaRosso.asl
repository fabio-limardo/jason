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
	:  artefattoRegistrato(N,C,T,V,K) & T == 1
		<- puntoFermo(N,C); .send(detectiveRosso,tell,artefatto(N));
		 selezionaDirezione.
		
+artefatto(N,C)[source(percept)]
	:  artefattoRegistrato(N,C,T,V,K) & T == 0
		<-selezionaDirezione.
		
+artefatto(N,C)[source(percept)]
	:not  artefattoRegistrato(N,C,T,V,K)
		<- selezionaDirezione.
		
-artefattoRegistrato(N,C,T,V,K)[source(detectiveRosso)]
	: artefattoRegistrato(N,_,_,_,_)
		<- -+artefattoRegistrato(N,C,T,V,K); //aggiunto
		.send(detectiveRosso,untell,artefatto(N)).
				
+!leggiArtefatto
	: artefattoTrovato
		<- apriArtefatto.

+!leggiArtefatto
	: not artefattoTrovato
		<- selezionaDirezione.

+!nextStep
	: direzioneNonPercorribile  
		<- .print("Non � possibile proseguire per la direzione desiderata"); selezionaDirezione.
		
+!nextStep
	: direzionePercorribile
		<- .print("Avanziamo");
		 	!checkForArtefacts
		 	//selezionaDirezione
		.
+!nextStep
	: fineGioco
		<- .print("Complimenti, hai vinto").
		




