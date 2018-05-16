### Analisi scelta DBMS
La scelta del Database Management System non è una scelta tanto banale quanto può sembrare. In questo progetto è stato scelto l'utilizzo di utilizzare MySQL, quindi più precisamente verrà utilizzato un Rlational Database Manangement System.

### Perchè MySQL?

#### Sicurezza
MySQL utilizza una sicurezza distribuita su più livelli andando a sfruttare il già esistente protocollo che ne fa da garante, SSL. MySQL supportandolo permette quindi una comunicazione sicura e criptata attraverso certificati e cifratura con chiavi RSA da 2048 bit.

#### Affidabilità
MySQL ha un'ottima gestione degli errori interni dovuti a un qualche tipo di corruzione del database riuscendo a fare eventuali operazioni di ripristino necessarie

#### Performance
Una propriamente ben configurata istanza di MySQL è molto veloce per questi motivi :
 - Covering index support
 - Adaptive hash index
 - Buffer pool implementation
 - Adaptive checkpointing
 - Binary log replication

<br/>  

### Analisi struttura concettuale database
L'utilizzo di un database per la memorizzazione di vari tipi di dati, dagli utenti ai loro salvataggi alle loro partite e molto altro è subito sembrato la scelta più ovvia per questo progetto.

In questo documento verrà esposto il modello relazionale scelto per la struttura interna del database e la consistenza dei dati in esso memorizzato.

L'UML nell'immagine sottostante indica come il database è implementato concettualmente

<img src="https://preview.ibb.co/fn1qLd/UML_Database_Sagrada.png" alt="UML_Database_Sagrada">


### Spiegazione modello concettuale
Si può notare la presenza di relazioni M:N, questo comporterà la creazioni di ulteriori tabelle per poter creare una corrispondenza ed una coerenza tra i dati in esso memorizzati.
Andando più in dettaglio si può analizzare le scelte pensate per il modello concettuale qui esposto.<br/>  
La tabella User conterrà 3 campi :
 - username (alfanumerico e chiave primaria)
 - password (memorizzando solo il suo hash generato da MD5)
 - date(data d'iscrizione al sistema di gioco).<br/>  

La chiave primaria username sarà parte della chiave composta presente nella tabella generata dalla relazione M:N tra User e Match.<br/>  
Match conterrà anch'esso tre campi :
 - date(data della partita)
 - PlayersNumber(numero giocatori presenti)
 - ID (identificativo partita e chiave primaria)<br/>  

Anche in questo caso la chiave primaria, questa volta di Match ovvero ID, sarà parte della chiave composta della tabella creata dalla relazione M:N tra Match e User.<br/>  
Questa tabella generata dalla relazione M:N corrisponderà al salvataggio/record di una partita per singolo giocatore ed essa conterrà :
 - il punteggio raggiunto
 - la data di accesso alla partita/sala d'attesa
 - l'ID della vetrata scelta
 - la rappresentazione sottoforma di BLOB (Binary Large Object) dello stato della vetrata a fine partita.<br/>  

Inoltre verranno memorizzate le informazioni sull'utilizzo e l'assegnamento di carte obbiettivo e strumento attraverso due ulteriori relazione M:N tra la tabella generata dalla relazione tra le due precedenti entità (Match e User).

### Ambiente d'utilizzo di MySQL
Il (R)DBMS MySQL verrà utilizzato attraverso la piattaforma phpMyAdmin su un server remoto in rete che permetterà una avanzata seppur contemporaneamente semplice gestione del database.
#### Problemi riscontrati e soluzioni
La porta utilizzata per il server in cui sarà presente MySQL dovrebbe essere la standard 3306 in linea teorica (porta registrata a MySQL da IANA), ma per ragioni dovute ad un packet filtering presente all'interno della rete del Politecnico a livello di trasporto, non è possibile utilizzare porte destinazione diverse dalla 80 e dalla 443, ve ne è una dimostrazione nell'immagine sotto riportata relativa ad un packet capturing effettuato attraverso il software Wireshark dove escludendo qualsiasi pacchetto con porta destinazione nell'header corrispondente a 80. Si può vedere come gli unici altri pacchetti presenti sono pacchetti con porte destinazione uguale a 443.

<img src="https://preview.ibb.co/dwFjwJ/only_Port80_Poli.png" alt="only_Port80_Poli"><br/>  

La soluzione proposta è quindi di attenersi all'utilizzo di un MySQL server remoto su porta 80 o 443.
