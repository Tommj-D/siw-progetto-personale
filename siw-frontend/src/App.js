import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Link, useParams, useNavigate } from 'react-router-dom';

// DETTAGLI LIBRO (UC2) 
function DettagliLibro({ aggiungiAlCarrello }) {
  const { id } = useParams();
  const [libro, setLibro] = useState(null);

  useEffect(() => {
    fetch(`/api/libri/${id}`)
      .then(res => res.json())
      .then(data => setLibro(data))
      .catch(err => console.error("Errore:", err));
  }, [id]);

  if (!libro) return <div className="alert alert-info shadow-sm">Caricamento dettagli...</div>;

  return (
    <div className="card shadow-sm border-0 p-4">
      <div className="row">
        {/* Colonna Sinistra: Immagine Copertina */}
        <div className="col-md-4 mb-4 mb-md-0">
          {libro.copertina ? (
            <img src={libro.copertina} alt={libro.titolo} className="img-fluid rounded shadow" />
          ) : (
            <div className="bg-secondary text-white d-flex align-items-center justify-content-center rounded shadow" style={{ height: '350px', opacity: 0.5 }}>
              <span className="display-1"></span>
            </div>
          )}
        </div>
        
        {/* Colonna Destra: Informazioni Libro */}
        <div className="col-md-8 d-flex flex-column">
          <div className="d-flex justify-content-between align-items-start mb-3">
            <h2 className="fw-bold m-0">{libro.titolo}</h2>
            <span className="badge bg-success fs-5">{libro.prezzo} €</span>
          </div>
          <h4 className="text-muted mb-4">Autore: {libro.autore}</h4>
          <p className="lead flex-grow-1">{libro.descrizione || "Nessuna descrizione disponibile per questo libro."}</p>
          
          <div className="d-flex gap-3 mt-4">
            <Link to="/" className="btn btn-outline-secondary btn-lg">Catalogo</Link>
            <button className="btn btn-primary btn-lg flex-grow-1" onClick={() => aggiungiAlCarrello(libro)}>
              Aggiungi al carrello
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

// STORICO ORDINI (UC9)
function StoricoOrdini() {
  const [ordini, setOrdini] = useState([]);

  useEffect(() => {
    fetch('/api/orders', {
      credentials: 'include' 
    })
      .then(res => res.json())
      .then(data => setOrdini(data))
      .catch(err => console.error("Errore ordini:", err));
  }, []);

  return (
    <div className="card shadow-sm border-0 p-4">
      <h2 className="fw-bold mb-4">I Miei Ordini</h2>
      {ordini.length === 0 ? (
        <div className="alert alert-warning">Non hai ancora effettuato ordini.</div>
      ) : (
        <ul className="list-group">
          {ordini.map(ordine => (
            <li key={ordine.id} className="list-group-item mb-4 border rounded shadow-sm p-4">
              <div className="d-flex justify-content-between border-bottom pb-2 mb-3">
                <h4 className="mb-0 text-primary fw-bold">Ordine #{ordine.id}</h4>
                <h4 className="fw-bold text-success m-0">{ordine.totale} €</h4>
              </div>
              <p className="mb-3 text-muted">Effettuato il: {ordine.dataOrdine || "Data non registrata"}</p>
              
              <h6 className="fw-bold mb-3 text-uppercase text-secondary">Riepilogo Articoli:</h6>
              <ul className="list-group list-group-flush">
                {ordine.dettagli && ordine.dettagli.map((dettaglio, index) => (
                  <li key={index} className="list-group-item d-flex justify-content-between align-items-center bg-light rounded mb-2 border-0">
                    <div>
                      <span className="fw-bold fs-5">{dettaglio.libro ? dettaglio.libro.titolo : "Libro non trovato"}</span>
                    </div>
                    <span className="badge bg-secondary rounded-pill fs-6">
                      {dettaglio.quantita} pz. x {dettaglio.prezzoAcquisto} €
                    </span>
                  </li>
                ))}
              </ul>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

// PRINCIPALE: APP
function AppContent() {
  const [books, setBooks] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [carrello, setCarrello] = useState([]);
  const [isAuthenticated, setIsAuthenticated] = useState(false); 
const [isAdmin, setIsAdmin] = useState(false);
const navigate = useNavigate();

useEffect(() => {
    fetch('/api/auth/status', {
      credentials: 'include' // Fondamentale per inviare il cookie a Spring Boot
    })
    .then(res => {
      if (res.ok) {
        setIsAuthenticated(true); // Se risponde OK, sei loggata!
        return res.json();
      } else {
        setIsAuthenticated(false); // Altrimenti, sei una visitatrice
        return null;
      }
    })
    .then(data => {
      if (data) {
        setIsAdmin(data.ruolo === 'ADMIN');
      } else {
        setIsAdmin(false);
      }
    })
    .catch(err => {
      setIsAuthenticated(false);
      setIsAdmin(false);
    });
  }, []);

  useEffect(() => {
  fetch('/api/libri')
    .then(res => res.json())
    .then(data => setBooks(data))
    .catch(err => console.error("Errore caricamento libri:", err));
  }, []);

  const filteredBooks = books.filter(book => {
    const termine = searchTerm.toLowerCase();
    return book.titolo.toLowerCase().includes(termine) || book.autore.toLowerCase().includes(termine);
  });

  const aggiungiAlCarrello = (libro) => {
    const libroEsistente = carrello.find(item => item.id === libro.id);
    if (libroEsistente) setCarrello(carrello.map(item => item.id === libro.id ? { ...item, quantita: item.quantita + 1 } : item));
    else setCarrello([...carrello, { ...libro, quantita: 1 }]);
  };

  const rimuoviDalCarrello = (idDaRimuovere) => {
    setCarrello(carrello.filter(item => item.id !== idDaRimuovere));
  };

  const calcolaTotale = () => {
    return carrello.reduce((tot, item) => tot + (item.prezzo * item.quantita), 0).toFixed(2);
  };

  const procediAllOrdine = () => {
    const payload = {
      dettagli: carrello.map(item => ({ libro: { id: item.id }, quantita: item.quantita }))
    };

    fetch('/api/orders/checkout', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      credentials: 'include',
      body: JSON.stringify(payload)
    })
    .then(res => res.json())
    .then(data => {
      alert(`Ordine completato con successo! ID Ordine: ${data.id}`);
      setCarrello([]); 
    })
    .catch(err => alert("Errore durante l'ordine. Assicurati di essere loggato."));
  };

  const handleLogout = () => {
  fetch('/logout', {
    method: 'POST',
    credentials: 'include'
  })
  .then(() => {
    setIsAuthenticated(false);
    setIsAdmin(false);
    navigate('/');
  })
  .catch(err => console.error("Errore durante il logout:", err));
  };

  return (
      <div className="container py-5 bg-light min-vh-100">
        
        {/* --- INTESTAZIONE E NAVBAR --- */}
        <div className="text-center mb-5">
          <Link to="/" style={{ textDecoration: 'none', color: 'inherit' }}>
            <h1 className="display-4 fw-bold">Vetrina</h1>
          </Link>
          
          <div className="mt-3 d-flex justify-content-center align-items-center gap-3">
            <Link to="/" className="btn btn-outline-dark">Catalogo</Link>
            <Link to="/ordini" className="btn btn-outline-primary">I Miei Ordini</Link>
            
          <div className="d-flex align-items-center me-3">
            {isAdmin && (
              <a href="/admin/libri" className="btn btn-warning btn-sm me-2">
                <i className="bi bi-gear"></i> Area Admin
              </a>
            )}

            {!isAuthenticated && (
              <a href="/login" className="btn btn-primary btn-sm me-2">
                Accedi
              </a>
            )}

            {isAuthenticated && (
              <button onClick={handleLogout} className="btn btn-danger btn-sm">
                   Esci
              </button>
            )}

        </div>

            <div className="d-flex align-items-center me-3">
            {!isAuthenticated && (
              <a href="/login" className="btn btn-primary btn-sm me-2">
                Accedi
              </a>
            )}

            {isAuthenticated && (
              <button onClick={handleLogout} className="btn btn-danger btn-sm">
                   Esci
              </button>
            )}

        </div>
            
          </div>
        </div>

        {/* --- CORPO DELLA PAGINA --- */}
        <div className="row">
          <div className="col-md-8">
            <Routes>
              
              <Route path="/" element={
                <>
                  <input type="text" className="form-control form-control-lg shadow-sm border-0 mb-4" placeholder="Cerca per titolo o autore..." value={searchTerm} onChange={(e) => setSearchTerm(e.target.value)} />
                  <div className="row row-cols-1 row-cols-md-2 g-4">
                    {filteredBooks.map(book => (
                      <div className="col" key={book.id}>
                        <div className="card h-100 shadow-sm border-0">
                          
                          {book.copertina ? (
                            <img src={book.copertina} className="card-img-top p-3" alt={book.titolo} style={{ height: '350px', objectFit: 'contain', backgroundColor: '#f8f9fa' }} />
                          ) : (
                            <div className="bg-secondary text-white d-flex align-items-center justify-content-center rounded-top" style={{ height: '350px', opacity: 0.7 }}>
                              <span className="fs-1"></span>
                            </div>
                          )}

                          <div className="card-body d-flex flex-column">
                            <h5 className="card-title fw-bold">{book.titolo}</h5>
                            <p className="card-text text-muted mb-3">Autore: {book.autore}</p>
                            <div className="mt-auto mb-3"><span className="fs-5 fw-bold text-success">{book.prezzo} €</span></div>
                            <div className="d-flex gap-2">
                              <Link to={`/libro/${book.id}`} className="btn btn-outline-primary w-50">Dettagli</Link>
                              <button className="btn btn-primary w-50" onClick={() => aggiungiAlCarrello(book)}>+ Carrello</button>
                            </div>
                          </div>
                        </div>
                      </div>
                    ))}
                  </div>
                </>
              } />

              <Route path="/libro/:id" element={<DettagliLibro aggiungiAlCarrello={aggiungiAlCarrello} />} />
              <Route path="/ordini" element={<StoricoOrdini />} />
            
            </Routes>
          </div>

          {/* --- CARRELLO LATERALE --- */}
          <div className="col-md-4">
            <div className="card shadow border-0 sticky-top" style={{ top: '20px' }}>
              <div className="card-header bg-dark text-white fw-bold">Il tuo Carrello</div>
              <div className="card-body">
                {carrello.length === 0 ? <p className="text-muted text-center m-0 py-3">Vuoto.</p> : (
                  <>
                    <ul className="list-group list-group-flush mb-3">
                      {carrello.map((item, index) => (
                        <li key={index} className="list-group-item d-flex justify-content-between align-items-center px-0">
                          <div><h6 className="my-0">{item.titolo}</h6><small className="text-muted">{item.prezzo} € x {item.quantita}</small></div>
                          <button className="btn btn-sm btn-outline-danger py-0 px-2" onClick={() => rimuoviDalCarrello(item.id)}>×</button>
                        </li>
                      ))}
                    </ul>
                    <hr/>
                    <div className="d-flex justify-content-between align-items-center mb-3">
                      <h5 className="m-0 fw-bold">Totale:</h5>
                      <h5 className="m-0 fw-bold text-success">{calcolaTotale()} €</h5>
                    </div>
                    <button className="btn btn-success w-100 fw-bold" onClick={procediAllOrdine}>Procedi all'Ordine</button>
                  </>
                )}
              </div>
            </div>
          </div>

        </div>
      </div>
  );
}

function App() {
  return (
    <Router>
      <AppContent />
    </Router>
  );
}

export default App;