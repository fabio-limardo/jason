// Agent detectiveSquadraRossa in project labirintoInganno

/* Initial beliefs and rules */

/* Initial goals */

!getStartPosition.

/* Plans */

+!getStartPosition 
	: not startTrovato
		<- trovaEntrata ; 
			!cercaArtefatti.

+!cercaArtefatti
	: startTrovato
		<-selezionaDirezione.
		
+posizione(X,Y)[source(percept)]
	: true
		<- -+posizione(X,Y).

+direzione(D)
	: true
		<- controllo(D);
			!nextStep(D).
			
+!nextStep(D)
	: direzioneNonPercorribile  
		<- .print("Detective Rosso sceglie nuova direzione"); selezionaDirezione(D).
		
+!nextStep(D)
	: direzionePercorribile
		<- .print("Detective Rossso Avanza"); !checkForArtefacts.

+fineGioco
		<- true. 			
	
+!checkForArtefacts
	: true
		<- cercaArtefatto;
			!leggiArtefatto.
			
+!leggiArtefatto
	: artefattoTrovato
		<- analizzaArtefatto.

+!leggiArtefatto
	: not artefattoTrovato
		<- selezionaDirezione.
		
+artefattoScoperto(N,C,T,V,K)
	: artefattoScoperto(N,C,T,V,1)
		<- +artefattoRegistrato(N,C,T,V,K);
		.send(velocistaRosso,tell,artefattoRegistrato(N,C,T,V,K));
		selezionaDirezione.
		
+artefattoScoperto(N,C,T,V,K)
	:artefattoScoperto(N,C,T,V,0)
		<- +artefattoRegistrato(N,C,T,V,K);
		selezionaDirezione.
		
+artefatto(N)
	: artefattoRegistrato(N,C,T,V,K) & V == 3
		<- cambiaArtefatto(N,C,T);
		R = 2; -+artefattoRegistrato(N,C,T,R,K);
		selezionaDirezione.
		
+artefatto(N)
	: artefattoRegistrato(N,C,T,V,K) & V < 3
		<- R = V+1; -+artefattoRegistrato(N,C,T,R,K);
		selezionaDirezione.
	