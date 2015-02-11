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
		
//+posizione(X,Y)[source(percept)]
//	: true
//		<- -+posizione(X,Y).

+direzione(D)[source(percept)] 
	: true
		<- controllo(D);
			!nextStep.
			
+!nextStep
	: direzioneNonPercorribile  
		<- .print("Detective Rosso sceglie nuova direzione"); selezionaDirezione.
		
+!nextStep
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
	: artefattoScoperto(N,C,T,V,1) & not artefattoRegistrato(N,_,_,_,_)
		<- -+artefattoRegistrato(N,C,T,V,K);
		.send(velocistaRosso,tell,artefattoRegistrato(N,C,T,V,1));
		selezionaDirezione.
		
+artefattoScoperto(N,C,T,V,K) 
	:artefattoScoperto(N,C,T,V,0)  & not artefattoRegistrato(N,_,_,_,_)
		<- -+artefattoRegistrato(N,C,T,V,0);
		selezionaDirezione.
		
+artefattoScoperto(N,C,T,V,K)
	: artefattoRegistrato(N,_,_,_,_)
		<- selezionaDirezione.
		
+artefatto(N)
	: artefattoRegistrato(N,C,T,V,K) & V == 3
		<- cambiaArtefatto(N,C,T).

/*Questo la prima volta lo fa */		
+artefatto(N)
	: artefattoRegistrato(N,C,T,V,K) & V < 3
		<- R = V+1; -+artefattoRegistrato(N,C,T,R,K);
		.send(velocistaRosso,untell,artefattoRegistrato(N,C,T,R,K)). //aggiunto
		
	