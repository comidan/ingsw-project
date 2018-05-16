# Scelta dell'architettura

L'architettura di rete utilizzata a livello tipologico è l'architettura Client Server Three Tier.
La tipologia è mostrata nella figura sottostante.

<img src="https://preview.ibb.co/c9X6bJ/three_Tier_Diagram.jpg" alt="three_Tier_Diagram">

Tale architettura permette la suddivisione in 3 diversi livelli : 
 - client tier: si occupa solo della parte di interazione con l'utente e visualizzazione grafica
 - Middletier : corrispondente all'application layer, è il layer dove è presente il server (o i server)
 - Data tier : corrispondente al data layer, è dove è presente il database (o i database)

### Vantaggi
I vantaggi di questa architettura sono molteplici : 
 - Scalabilità : a discrezione della quantità di carico del sistema si può tranquillamente aumentare e quindi scalare il numero di server e/o di database server per garantire una migliore gestione del carico. Il bilanciamento del carico di lavoro sarà un compito gestito all'interno della LAN del middletear, il client andando a fare una query DNS sull'hostname riceverà un certo indirizzo ip a cui poi si connetterà, all'interno della rete quando arriverà il primo pacchetto SYN|ACK dell'iniziale connessione TCP verrà gestito a quale server effettuare la connessione.
 - Ridondanza : si ha sempre almeno un server, ai due livelli logici più alti, di riserva
 - Gestione guasti : in qualsiasi momento ci sia un guasto in qualche componenente di un singolo server, l'architettura interta di rete ridistribuirà il carico al meglio della nuova situazione lasciando inalterate le funzionalità del sistema di gioco, tutto in modo invisibile al client tier.
