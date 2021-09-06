# Ecommerce
Il progetto consiste nello sviluppo di un’applicazione web inerente a uno store ecommerce
basato sul modello “consumer to consumer”: la piattaforma implementata gestisce
l’interazione tra utenti privati al fine di consentire le operazioni di acquisti e vendite di
prodotti. La pandemia da Covid 19 ha favorito la crescita della digitalizzazione di tutti i
servizi per i quali esiste una forte necessità, pertanto questa tesi si propone di rivisitare a
scopo didattico le funzionalità basilari offerte dai siti di asta online in modo da offrire al
venditore un supporto automatico in grado di suggerire la migliore combinazione di offerte
ricevute per quantitativo di prodotto. L’obiettivo perseguito è dunque l’ottimizzazione dei
profitti commerciali del venditore tramite un’intelligenza artificiale.
Il problema che tale algoritmo deve risolvere è noto in letteratura come “Il problema dello
zaino” (Knapsack problem): lo zaino rappresenta metaforicamente un contenitore la cui
capienza corrisponde al numero totale di unità in vendita relative al prodotto in questione;
ogni oggetto inserito nello zaino corrisponde a un’offerta il cui peso nello zaino coincide
con il numero delle unità richieste mentre il valore corrisponde al prezzo offerto per la
singola unità del prodotto e moltiplicato per il numero di unità richieste.
L’algoritmo deve quindi “riempire” lo zaino inserendovi la combinazione di offerte la cui
somma sia massima rispetto a tutte le altre possibili combinazioni.
La programmazione dinamica offre algoritmi capaci di risolvere questo problema in
maniera ottima ma la complessità computazionale è alquanto onerosa e ciò orienta la
trattazione verso scelte euristiche che permettano di approssimare un risultato quasi ottimo
in tempi molto più brevi. In particolare è stato impiegato un algoritmo genetico che mutua
la sua implementazione dai criteri biologici responsabili dell’evoluzione di una popolazione
iniziale e casuale in cui ogni individuo è rappresentato da una possibile soluzione. Il
progetto intende dimostrare efficacia ed efficienza dell’approccio seguito.

L’applicativo gira su un virtual private server aruba, attivo 24h, ed è accessibile tramite il
seguente url:
http://racommerce.ns0.it/

L’algoritmo genetico proposto, implementato mediante tecnica di selezione artificiale
“Truncate Selection”, criterio di crossover uniforme e mutazione flip bit, ha calcolato
correttamente in ogni casistica la soluzione ottima con un tempo di latenza mediamente
inferiore ai 4 secondi, relativamente ad istanze di al massimo 10 ordini da valutare.
Poiché G (numero massimo di iterazioni) ed S (dimensione della popolazione) sono
costanti, non influenzano la complessità asintotica complessiva dell’algoritmo, che quindi è
pari a O(N). Si è dimostrato pertanto come l’algoritmo proposto riduca la complessità del
KP da esponenziale a lineare, permettendo di trovare soluzioni approssimativamente
ottimali per un problema NP.

L'elaborato completo relativo alla documentazione del progetto svolto è consultabile al seguente link:
https://drive.google.com/file/d/1A4D77yy4XgOeZEwlizdVmWalEZBKMp5p/view?usp=sharing
