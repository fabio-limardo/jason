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
		<- controllo(D);!checkForArtefacts;
			!nextStep.

+posizione(X,Y)[source(percept)]
	: true
		<- -+posizione(X,Y).

+!checkForArtefacts
	: true
		<- cercaArtefatto;
			!leggiArtefatto.

+artefatto(N,C)
	: artefattoAnalizzato(N,T) & T == 1
		<- puntoFermo(N,C); .send(detectiveRosso,tell,artefatto(N));
		 -artefatto(N,C);selezionaDirezione.
		
+artefatto(N,C)
	: artefattoAnalizzato(N,T) & T == 0
		<- -artefatto(N,C); selezionaDirezione.
		
+artefatto(N,C)
	:not artefattoAnalizzato(N,T)
		<- puntoFermo(N,C); selezionaDirezione.
		
+!leggiArtefatto
	: artefattoTrovato
		<- apriArtefatto.

+!leggiArtefatto
	: not artefattoTrovato
		<- selezionaDirezione.

+!nextStep
	: direzioneNonPercorribile  
		<- .print("Non è possibile proseguire per la direzione desiderata"); selezionaDirezione.
		
+!nextStep
	: direzionePercorribile
		<- .print("Avanziamo");
		// !checkForArtefacts
		.
+!nextStep
	: fineGioco
		<- .print("Complimenti, hai vinto").
		




