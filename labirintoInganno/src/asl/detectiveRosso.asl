// Agent detectiveSquadraRossa in project labirintoInganno

/* Initial beliefs and rules */

/* Initial goals */

!cercaArtefatti.

/* Plans */

+!cercaArtefatti 
	: not startTrovato
		<- trovaEntrata ; 
			!cercaArtefatti.

+!cercaArtefatti
	: startTrovato
		<-selezionaDirezione.

+direzione(D)[source(percept)] 
	: true
		<- controllo(D);
			!nextStep.
			
+!nextStep
	: direzioneNonPercorribile  
		<- .print("Non è possibile proseguire per la direzione desiderata"); selezionaDirezione.
		
+!nextStep
	: direzionePercorribile
		<- .print("Avanziamo"); !checkForArtefacts.

+!nextStep
	: fineGioco
		<- true. 			
	
+!checkForArtefacts
	: true
		<- cercaArteffati;
			!leggiArtefatto.

+!leggiArtefatto
	: artefattoTrovato
		<- analizzaArtefatto; selezionaDirezione.

+!leggiArtefatto
	: not artefattoTrovato
		<- selezionaDirezione.
		
+artefatto(N)
	: artefattoScoperto(N,C,T,V) & V == 3
		<- cambiaArtefatto(N,C,T); -artefatto(N) ; V = 1.
		
+artefatto(N)
	: artefattoScoperto(N,C,T,V) & V < 3
		<- -artefatto(N) ; V = V+1 .
		