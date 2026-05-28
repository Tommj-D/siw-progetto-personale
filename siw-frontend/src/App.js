import React, { useState, useEffect } from 'react';

function App() {
  const [books, setBooks] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [carrello, setCarrello] = useState([]);

  useEffect(() => {
    fetch('http://localhost:8080/api/books')
      .then(response => response.json())
      .then(data => setBooks(data))
      .catch(error => console.error("Errore di connessione al backend:", error));
  }, []);

  const filteredBooks = books.filter(book => {
    const termine = searchTerm.toLowerCase();
    return book.titolo.toLowerCase().includes(termine) || 
           book.autore.toLowerCase().includes(termine);
  });

  const aggiungiAlCarrello = (libro) => {
    const libroEsistente = carrello.find(item => item.id === libro.id);
    if (libroEsistente) {
      setCarrello(carrello.map(item => 
        item.id === libro.id ? { ...item, quantita: item.quantita + 1 } : item
      ));
    } else {
      setCarrello([...carrello, { ...libro, quantita: 1 }]);
    }
  };

  const rimuoviDalCarrello = (idDaRimuovere) => {
    // Filtriamo l'array: teniamo solo i libri che HANNO UN ID DIVERSO da quello cliccato
    const nuovoCarrello = carrello.filter(item => item.id !== idDaRimuovere);
    // Aggiorniamo lo stato di React
    setCarrello(nuovoCarrello);
  };

  const calcolaTotale = () => {
    return carrello.reduce((totale, item) => totale + (item.prezzo * item.quantita), 0).toFixed(2);
  };

  const procediAllOrdine = () => {
    // Trasformiamo il carrello nel formato esatto che si aspetta il tuo OrdineRestController
    const payload = {
      dettagli: carrello.map(item => ({ libro: { id: item.id }, quantita: item.quantita }))
    };

    // Spediamo il pacchetto a Spring Boot!
    fetch('http://localhost:8080/api/orders/checkout', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    })
    .then(res => res.json())
    .then(data => {
      alert(`Ordine completato con successo! ID Ordine: ${data.id}`);
      setCarrello([]); // Svuotiamo il carrello dopo l'acquisto
    })
    .catch(err => console.error("Errore durante l'ordine:", err));
  };

  return (
    <div className="container py-5 bg-light min-vh-100">
      <div className="text-center mb-5">
        <h1 className="display-4 fw-bold">La Vetrina di Booktique 📚</h1>
        <p className="lead text-muted">Area Clienti (Sviluppata in React)</p>
      </div>

      <div className="row">
        
        {/* COLONNA SINISTRA: VETRINA */}
        <div className="col-md-8">
          <input 
            type="text" 
            className="form-control form-control-lg shadow-sm border-0 mb-4" 
            placeholder="🔍 Cerca per titolo o autore..." 
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)} 
          />

          {books.length === 0 ? (
            <div className="alert alert-info shadow-sm">Caricamento in corso...</div>
          ) : filteredBooks.length === 0 ? (
            <div className="alert alert-warning shadow-sm">Nessun libro trovato per "{searchTerm}".</div>
          ) : (
            <div className="row row-cols-1 row-cols-md-2 g-4">
              {filteredBooks.map(book => (
                <div className="col" key={book.id}>
                  <div className="card h-100 shadow-sm border-0">
                    <div className="bg-secondary text-white text-center py-4 rounded-top" style={{ opacity: 0.7 }}>
                      <span className="fs-1">📖</span>
                    </div>
                    <div className="card-body d-flex flex-column">
                      <h5 className="card-title fw-bold">{book.titolo}</h5>
                      <p className="card-text text-muted mb-3">Autore: {book.autore}</p>
                      <div className="mt-auto d-flex justify-content-between align-items-center">
                        <span className="fs-5 fw-bold text-success">{book.prezzo} €</span>
                        <button className="btn btn-primary" onClick={() => aggiungiAlCarrello(book)}>
                          Aggiungi al carrello
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>

        {/* COLONNA DESTRA: IL CARRELLO */}
        <div className="col-md-4">
          <div className="card shadow border-0 sticky-top" style={{ top: '20px' }}>
            <div className="card-header bg-dark text-white fw-bold">
              🛒 Il tuo Carrello
            </div>
            <div className="card-body">
              {carrello.length === 0 ? (
                <p className="text-muted text-center m-0 py-3">Il carrello è ancora vuoto.</p>
              ) : (
                <>
                  <ul className="list-group list-group-flush mb-3">
                    {carrello.map((item, index) => (
                      <li key={index} className="list-group-item d-flex justify-content-between align-items-center px-0">
                        <div>
                          <h6 className="my-0">{item.titolo}</h6>
                          <small className="text-muted">{item.prezzo} € x {item.quantita}</small>
                        </div>
                        
                        <div className="d-flex align-items-center gap-2">
                          <span className="badge bg-primary rounded-pill">{item.quantita}</span>
                          <button 
                            className="btn btn-sm btn-outline-danger py-0 px-2"
                            onClick={() => rimuoviDalCarrello(item.id)}
                            title="Rimuovi dal carrello"
                          >
                            ×
                          </button>
                        </div>

                      </li>
                    ))}
                  </ul>
                  <hr/>
                  <div className="d-flex justify-content-between align-items-center mb-3">
                    <h5 className="m-0 fw-bold">Totale:</h5>
                    <h5 className="m-0 fw-bold text-success">{calcolaTotale()} €</h5>
                  </div>
                  <button className="btn btn-success w-100 fw-bold" onClick={procediAllOrdine}>
                    Procedi all'Ordine
                  </button>
                </>
              )}
            </div>
          </div>
        </div>

      </div>
    </div>
  );
}

export default App;