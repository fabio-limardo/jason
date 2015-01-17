// Agent velocistaSquadraRossa in project labirintoInganno

/* Initial beliefs and rules */
posizione(X,Y).

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

+direzione(D) 
	: true
		<- controllo(D);
			!nextStep.

+posizione(X,Y) 
	: true
		<- -+posizione(X,Y).

+!nextStep
	: direzioneNonPercorribile
		<- .print("Non è possibile proseguire per la direzione desiderata"); selezionaDirezione.
		
+!nextStep
	: direzionePercorribile
		<- .print("Avanziamo"); selezionaDirezione.
+!nextStep
	: fineGioco
		<- .print("Complimenti, hai vinto").





//+!trovaUscita : not fineGioco <- selezionaDirezione; !trovaUscita.



