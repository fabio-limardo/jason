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

+posizione(X,Y)[source(percept)]
	: true
		<- -+posizione(X,Y).

+!checkForArtefacts
	: true
		<- cercaArtefatto;
			!leggiArtefatto.

+artefatto(N,C)[source(percept)]
	:  artefattoRegistrato(N,C,T,V,K) & T == 1
		<- puntoFermo(N,C); .send(detectiveRosso,tell,artefatto(N));
		 -artefatto(N,C);selezionaDirezione.
		
+artefatto(N,C)[source(percept)]
	:  artefattoRegistrato(N,C,T,V,K) & T == 0
		<-selezionaDirezione.
		
+artefatto(N,C)[source(percept)]
	:not  artefattoRegistrato(N,C,T,V,K)
		<- puntoFermo(N,C); selezionaDirezione.
		
+!leggiArtefatto
	: artefattoTrovato
		<- apriArtefatto.

+!leggiArtefatto
	: not artefattoTrovato
		<- selezionaDirezione.

+!nextStep
	: direzioneNonPercorribile  
		<- .print("Non è possibile proseguire per la direzione desiderata"); selezionaDirezione(D).
		
+!nextStep
	: direzionePercorribile
		<- .print("Avanziamo");
		 	!checkForArtefacts
		.
+!nextStep
	: fineGioco
		<- .print("Complimenti, hai vinto").
		




