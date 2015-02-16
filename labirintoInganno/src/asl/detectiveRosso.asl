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

+direzione(D)[source(percept)] 
	: true
		<- controllo(D);
			!nextStep.
			
+!checkForArtefacts
	: true
		<- cercaArtefatto;
			!leggiArtefatto.			
+!nextStep
	: direzioneNonPercorribile  
		<- .print("Detective Rosso sceglie nuova direzione"); selezionaDirezione.
		
+!nextStep
	: direzionePercorribile
		<- .print("Detective Rossso Avanza"); !checkForArtefacts.

+fineGioco
		<- true. 			
	

			
+!leggiArtefatto
	: artefattoTrovato
		<- analizzaArtefatto.

+!leggiArtefatto
	: not artefattoTrovato
		<- selezionaDirezione.

		
+artefattoScoperto(N,C,T,V,K)
	: artefattoScoperto(N,C,T,V,K) & not artefattoRegistrato(N,_,_,_,_) & K=1
		<- -+artefattoRegistrato(N,C,T,V,K);
		.send(velocistaRosso,tell,artefattoRegistrato(N,C,T,V,1));
		selezionaDirezione.
		
+artefattoScoperto(N,C,T,V,K) 
	:artefattoScoperto(N,C,T,V,K)  & not artefattoRegistrato(N,_,_,_,_) & K=0
		<- -+artefattoRegistrato(N,C,T,V,0);
		selezionaDirezione.
		
+artefattoScoperto(N,C,T,V,K)
	: artefattoRegistrato(N,_,_,_,_)
		<- selezionaDirezione.
		
+artefatto(N)
	: artefattoRegistrato(N,C,T,V,K) & V == 3
		<- cambiaArtefatto(N,C,T);
		analizzaArtefatto.

+artefatto(N)
	: artefattoRegistrato(N,C,T,V,K) & V < 3
		<- R = V+1; -+artefattoRegistrato(N,C,T,R,K);
		.send(velocistaRosso,untell,artefattoRegistrato(N,C,T,R,K)).		

