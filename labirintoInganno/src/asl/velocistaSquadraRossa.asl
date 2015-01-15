// Agent velocistaSquadraRossa in project labirintoInganno

/* Initial beliefs and rules */
//posizione(0,0).
/* Initial goals */

!getStartPosition.

/* Plans */

+!getStartPosition : not startTrovato <- trovaEntrata; .print("startTrovato"); !trovaUscita.
+!trovaUscita : startTrovato <- selezionaDirezione; -startTrovato; !trovaUscita.
+!trovaUscita : not fineGioco <- selezionaDirezione; !trovaUscita.



