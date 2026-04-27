Caso d’uso UC1 – Visualizzazione del catalogo dei libri
Attore primario: un Cliente.
1. Un Cliente vuole visualizzare il catalogo dei libri disponibili.
2. Il Cliente sceglie l’attività “Visualizzare catalogo”.
3. Il Sistema mostra l’elenco completo dei libri presenti nel catalogo, mostrando per ciascuno il titolo, l'autore, la copertina e il prezzo.
4. Il Cliente può poi proseguire nell’uso del Sistema.
Estensione 3a: Il catalogo è vuoto. Il Sistema mostra il messaggio "Nessun libro attualmente disponibile nel catalogo".

Caso d’uso UC2 – Visualizzazione dei dettagli di un libro
Attore primario: un Cliente.
1. Un Cliente vuole visualizzare i dettagli di un libro presente nel catalogo.
2. Il Sistema mostra l'elenco dei libri (caso d'uso UC1).
3. Il Cliente inserisce, selezionandolo dall'elenco, l'identificatore del libro di cui vuole visualizzare i dettagli.
4. Il Sistema mostra tutte le informazioni del libro selezionato: titolo, autore, prezzo, descrizione e disponibilità.
5. Il Cliente può poi proseguire nell’uso del Sistema.

Caso d’uso UC3 – Ricerca di libri per titolo o autore
Attore primario: un Cliente.
1. Un Cliente vuole cercare specifici libri nel catalogo.
2. Il Cliente sceglie l'attività "Ricerca libri". Il Sistema mostra una barra di ricerca.
3. Il Cliente inserisce una stringa di testo corrispondente al titolo o all'autore che desidera cercare e avvia la ricerca.
4. Il Sistema verifica la presenza di corrispondenze nel catalogo e mostra l’elenco dei libri che corrispondono ai criteri di ricerca.
5. Il Cliente può poi proseguire nell’uso del Sistema.
Estensione 4a: Nessun libro corrisponde ai criteri immessi. Il Sistema mostra il messaggio "Nessun risultato trovato per la ricerca effettuata".

Caso d’uso UC4 – Inserimento di un nuovo libro
Attore primario: un Admin.
1. Un Admin vuole inserire un nuovo libro nel catalogo.
2. L'Admin inserisce il suo indirizzo di posta elettronica e la sua password. Il Sistema verifica la correttezza dei dati immessi e autentica l'Admin.
3. L'Admin sceglie l'attività "Inserimento nuovo libro". Il Sistema mostra un modulo di inserimento dati vuoto.
4. L'Admin inserisce titolo, autore, prezzo e descrizione del nuovo libro.
5. L'Admin conferma l'inserimento del libro.
6. Il Sistema salva il nuovo libro nel catalogo.
7. L'Admin può poi proseguire nell’uso del Sistema.
Estensione 5a: L'Admin annulla l'inserimento. Il Sistema non registra il nuovo libro e riporta l'Admin al menu precedente.
Estensione 6a: L'Admin ha inserito dati non validi. Il Sistema segnala l'errore e richiede l'inserimento corretto dei dati (torna al passo 4).

Caso d’uso UC5 – Modifica di un libro esistente
Attore primario: un Admin.
1. Un Admin vuole modificare i dati di un libro esistente.
2. L'Admin inserisce il suo indirizzo di posta elettronica e la sua password. Il Sistema verifica la correttezza dei dati immessi e autentica l'Admin.
3. L'Admin sceglie l'attività "Modifica libro". Il Sistema mostra l'elenco dei libri presenti nel catalogo.
4. L'Admin inserisce, selezionandolo dall'elenco, l'identificatore del libro da modificare. Il Sistema mostra i dati del libro.
5. L'Admin modifica i dati esistenti con le nuove informazioni.
6. L'Admin conferma le modifiche.
7. Il Sistema aggiorna le informazioni del libro nel catalogo.
8. L'Admin può poi proseguire nell’uso del Sistema.
Estensione 6a: L'Admin annulla l'operazione. Il Sistema non aggiorna i dati del libro.

Caso d’uso UC6 – Cancellazione di un libro
Attore primario: un Admin.
1. Un Admin vuole eliminare un libro dal catalogo.
2. L'Admin inserisce il suo indirizzo di posta elettronica e la sua password. Il Sistema verifica la correttezza dei dati immessi e autentica l'Admin.
3. L'Admin sceglie l'attività "Cancellazione libro". Il Sistema mostra l'elenco dei libri.
4. L'Admin inserisce, selezionandolo dall'elenco, l'identificatore del libro da eliminare. Il Sistema mostra i dettagli del libro.
5. L'Admin conferma l'eliminazione del libro selezionato.
6. Il Sistema rimuove il libro dal catalogo.
7. L'Admin può poi proseguire nell’uso del Sistema.
Estensione 5a: L'Admin annulla l'eliminazione. Il Sistema non rimuove il libro.

Caso d’uso UC7 – Aggiunta di un libro al carrello
Attore primario: un Cliente.
1. Un Cliente vuole aggiungere un libro al proprio carrello acquisti.
2. Il Cliente si autentica inserendo email e password. Il Sistema verifica e autentica il Cliente. 
3. Il Cliente visualizza un libro (tramite UC1, UC2 o UC3).
4. Il Cliente sceglie l'attività "Aggiungi al carrello" per il libro visualizzato.
5. Il Sistema inserisce il libro selezionato nel carrello del Cliente e aggiorna il totale provvisorio.
6. Il Cliente ripete i passi 3-5 fino a che non ha terminato la selezione.
7. Il Cliente può poi proseguire nell’uso del Sistema.

Caso d’uso UC8 – Effettuare un ordine
Attore primario: un Cliente.
1. Un Cliente vuole effettuare un ordine dei libri presenti nel proprio carrello.
2. Il Cliente si autentica.
3. Il Cliente sceglie l'attività "Effettua ordine". Il Sistema mostra il riepilogo dei libri nel carrello e il costo totale.
4. Il Cliente conferma l'ordine.
5. Il Sistema registra l'ordine e associa i libri acquistati al Cliente.
6. Il Sistema mostra un messaggio di conferma con il numero identificativo dell'ordine.
7. Il Cliente può poi proseguire nell’uso del Sistema.
Estensione 3a: Il carrello del Cliente è vuoto. Il Sistema mostra il messaggio "Impossibile effettuare l'ordine, il carrello è vuoto".
Estensione 4a: Il Cliente annulla la procedura. Il Sistema non registra l'ordine e i libri rimangono nel carrello.

Caso d’uso UC9 – Visualizzazione degli ordini
Attore primario: un Cliente.
1. Un Cliente vuole visualizzare i propri ordini effettuati in passato.
2. Il Cliente si autentica.
3. Il Cliente sceglie l'attività "Visualizza ordini".
4. Il Sistema mostra l'elenco degli ordini effettuati dal Cliente (mostrando per ciascun ordine la data, l'identificativo e il totale speso).
5. Il Cliente può poi proseguire nell’uso del Sistema.
Estensione 4a: Il Cliente non ha effettuato ordini. Il Sistema mostra il messaggio "Nessun ordine presente nello storico".