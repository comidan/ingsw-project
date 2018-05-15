# Analisi protocollo di scambio pacchetti messaggio/dati tra client e server in un’architettura client server

Server e client effettueranno uno scambio di messaggi attraverso il formato JSON. In particolare, ciascun messaggio è caratterizzato da *tre* elementi principali (elencati di seguito nell'ordine in cui appaiono in ogni messaggio): il _tipo di messaggio_, il _tipo di azione_ e il _contenuto dell'azione_. Esiste anche un quarto elemento opzionale: il _nome dell'azione_. 

In particolare, un generico messaggio avrà la seguente struttura: 
```json{
  "tipo di messaggio": {
    "tipo di azione" : {
        "[nome dell'azione]" : {
      "contenuto dell'azione"
      }
    }
  }
} 
```

Con "tipo di messaggio" si intende sinteticamente il genere di contenuto del messaggio: ad esempio, la parola chiave _action_ indicherà che il messaggio contiene un'azione, mentre invece la parola chiave "response" indica che si tratta della risposta a un messaggio inviato. 
Con "tipo di azione" si entra ulteriormente nel dettaglio: si indica infatti quale genere di azione, fra quelle disponibili, si intende compiere. Dunque la parola "login" indicherà la richiesta di registrazione, la parola "settings" indicherà il settaggio di alcune impostazioni di gioco, la parola "choice" indicherà un'azione di scelta fra varie opzioni. 
Fanno parte del "contenuto dell'azione" tutte le informazioni aggiuntive necessarie per portare a termine l'azione; ad esempio, in un messaggio di login questa sezione conterrà le credenziali dell'utente, mentre in un messaggio di scelta indicherà la scelta effettuata fra le opzioni disponibili, quali per esempio le coordinate a cui si intende posizionare un dado sulla Window. 
Il nome dell'azione, nel caso di un'azione specifica e identificata univocamente, indica il nome (che costituisce una breve descrizione dell'azione) attraverso il quale è possibile risalire all'azione stessa.

La struttura dei messaggi può essere visualizzata attraverso il seguente albero in riferimento al protocollo applicativo lato client

<p align="center">
  <img src="https://image.ibb.co/ff201J/Command_JSONTree_Client.jpg">
</p>

Qui invece si denota l'albero del protocollo applicativo lato server
<p align="center">
  <img src="https://image.ibb.co/iFKf1J/Command_JSONTree_Server.jpg">
</p>

Lo scambio di messaggi nell'architettura astratta di rete è possibile visuallizzarla in una maniera più relativa all'infrastuttura di rete nel seguente grafico dove è possibile vedere lo scambio di pacchetti dati contententi messaggi in JSON scambiati in questo caso durante l'interazione login tra un client ed il server.
<p align="center">
  <img src="https://preview.ibb.co/hZEXVd/Network_Topology_Sagrada.png">
</p>

# Analisi interazioni client server attraverso scambio di messaggi in formato JSON
Il server avviandosi creerà un servizio basato su TCP-IP in ascolto su una porta generata attraverso un protocollo di port discovery, al connettersi un certo client, oltre ad avviare il protocollo Heartbeat lato client al server via UDP, invierà un messaggio sotto forma di JSON contenete i dati per il login e ricevendo dal server una risposta, come da esempio.
Questa richiesta di login contiene le credenziali del giocatore che si connette al server. Il server risponde con una conferma che segnala che il login è andato a buon fine.

#### Client request
```
  "action": {
    "login" : {
      "username" : "username",
      "authentication" : "auth"
    }
  }
```
#### Server response :
```
  "response": {
    "login" : {
      "valid_response" : "response",
      "metadata" : "metadata"
    }
  }
```

Successivamente, il client manda un messaggio in cui sono indicate le proprie scelte per quanto riguardano l'interfaccia di utilizzo (ovvero la scelta fra CLI e GUI) e per quanto riguardano le modalità di invio dati (ovvero la scelta fra socket e RMI). Anche la ricezione di questa richiesta viene confermata dal server.
#### Client request : 
```
  "action": {
    "settings" : {
      "interface" : "gui",
      "protocol" : "rmi"
    }
  }
```
#### Server response :
```
  "response": {
    "settings" : {
      "valid_response" : "response",
      "metadata" : "metadata"
    }
  }
```
Il server poi inoltra al client gli ID delle due _Window Card_ proposte: il client manda al server un messaggio che indica quale delle due _Window Card_ intenda utilizzare e se della carta scelta intenda utilizzare il fronte o il retro. 
Dopo la scelta delle Window, il server invia al client l'ID della _Private Objective Card_ a lui assegnata. 

#### Inoltro id della window al client:
```
  "response": {
    "choice" : {
      "windows" : [
        {
        "window_id_1" : "id_1",
        "window_side_1" : "side_1"},
        {
        "window_id_2" : "id_2",
        "window_side_2" : "side_2"
        }
      ]
    }
  }
```

#### Scelta iniziale window:
```
  "action": {
    "choice" : {
      "window" : {
        "window_id" : "id",
        "window_side" : "side"
      }
    }
  }

```

Durante il gioco verrà effettuato uno scambio di messaggi non dissimile dai precedenti, permettendo un'elevata modularità e dinamicità nella comunicazione tra Server e i vari Client, nonché tra le classi interne di un dato processo in esecuzione. Qui si riporta di seguito un esempio per ciascuna delle tipologie rilevanti di messaggio.

#### Posizionamento di un dado nella vetrata :
```
  "action": {
    "choice" : {
      "move_dice" : {
        "dice_id" : "dice_id",
        "source" : "source",
        "position" : {
          "x" : "x",
          "y" : "y"
        }
      }
    }
  }
```

#### Inoltro id dei dadi generati dal model :
```
  "response": {
    "choice" : {
      "move_dice" : {
        "destination" : "destination",
        "dice" : [
          {
          "dice_id_1" : "dice_id_1",
          "value" : "value",
          "color" : "color"
          },
          {
          "dice_id_1" : "dice_id_1",
          "value" : "value",
          "color" : "color"
          },
          ...
        ]
      }
    }
  }
```

#### Acquisto di una toolcard :
```
  "action": {
    "choice" : {
      "buy_toolcard" : {
        "toolcard_id" : "toolcard_id"
      }
    }
  }
```

#### Risposta del server per l'acquisto di una toolcard :
```
  "response": {
    "choice" : {
      "buy_toolcard" : {
        "toolcard_id" : "toolcard_id",
        "purchasable" : "purchasable"
      }
    }
  }
```

#### Utilizzo toolcard :
```
  "action": {
    "choice" : {
      "toolcard" : {
        "toolcard_id" : "toolcard_id",
        "data" : {
          "position" : {
            "x" : "x",
            "y" : "y"
          },
          "other_data" : "other_data"
          ...
          ...
          ...
        }
      }
    }
  }
```

#### Risposta Server riguardo l'utilizzo di una toolcard :
```
  "response": {
    "choice" : {
      "toolcard" : {
        "toolcard_id" : "toolcard_id",
        "used" : "used"
      }
    }
  }
```