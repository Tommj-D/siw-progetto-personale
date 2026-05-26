import React, { useState, useEffect } from 'react';

function App() {
  const [books, setBooks] = useState([]);

  useEffect(() => {
    fetch('http://localhost:8080/api/books')
      .then(response => response.json())
      .then(data => setBooks(data))
      .catch(error => console.error("Errore di connessione al backend:", error));
  }, []);

  return (
    <div style={{ padding: '30px', fontFamily: 'Arial, sans-serif' }}>
      <h1>La Vetrina di Booktique 📚</h1>
      <p>Benvenuto nell'area clienti sviluppata in React!</p>

      {books.length === 0 ? (
        <p>Caricamento libri in corso (o catalogo vuoto)...</p>
      ) : (
        <div style={{ display: 'flex', gap: '20px', flexWrap: 'wrap', marginTop: '20px' }}>
          {books.map(book => (
            <div key={book.id} style={{ 
              border: '1px solid #ddd', 
              padding: '20px', 
              borderRadius: '10px', 
              width: '250px',
              boxShadow: '0 4px 8px rgba(0,0,0,0.1)'
            }}>
              <h2 style={{ margin: '0 0 10px 0' }}>{book.title}</h2>
              <p><strong>Autore:</strong> {book.author}</p>
              <p><strong>Prezzo:</strong> €{book.price}</p>
              
              <button style={{ 
                backgroundColor: '#007bff', 
                color: 'white', 
                border: 'none', 
                padding: '10px 15px', 
                borderRadius: '5px', 
                cursor: 'pointer', // Il cursore cambia, ma non fa nulla se cliccato
                width: '100%'
              }}>
                Aggiungi al carrello
              </button>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default App;