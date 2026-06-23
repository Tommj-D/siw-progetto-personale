const { useState, useEffect } = React;

function CatalogoLibri() {
  const [books, setBooks] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [vediTutti, setVediTutti] = useState(false);
  const [loading, setLoading] = useState(true);

  const csrfMeta = document.querySelector('meta[name="_csrf"]');
  const csrfToken = csrfMeta ? csrfMeta.content : "";
  
  const mostraCarrello = document.querySelector('a[href="/cart"]') !== null;

  useEffect(function() {
    if (searchTerm.trim() === "") {
      const endpoint = vediTutti ? '/api/libri' : '/api/libri/homepage';
      fetch(endpoint)
        .then(function(res) { return res.json(); })
        .then(function(data) {
          setBooks(data);
          setLoading(false);
        })
        .catch(function(err) { console.error("Errore caricamento libri:", err); });
    } else {
      const timeoutId = setTimeout(function() {
        fetch('/api/libri/search?query=' + encodeURIComponent(searchTerm))
          .then(function(res) { return res.json(); })
          .then(function(data) {
            setBooks(data);
            setLoading(false);
          })
          .catch(function(err) { console.error("Errore ricerca:", err); });
      }, 300);
      return function() { clearTimeout(timeoutId); };
    }
  }, [searchTerm, vediTutti]);

  const titolo = React.createElement('h2', { className: 'fw-bold m-0' },
    vediTutti ? 'Catalogo Completo' : 'I Nostri Best Sellers');

  const toggleBtn = React.createElement('button', {
    className: 'btn btn-outline-dark btn-sm',
    onClick: function() { setVediTutti(!vediTutti); }
  }, vediTutti ? 'Vedi Best Sellers' : 'Vedi Catalogo Completo');

  const header = React.createElement('div',
    { className: 'd-flex justify-content-between align-items-center mb-4' },
    titolo, toggleBtn);

  const inputRicerca = React.createElement('input', {
    type: 'text',
    className: 'form-control form-control-lg shadow-sm border-0 mb-4',
    placeholder: 'Cerca per titolo o autore...',
    value: searchTerm,
    onChange: function(e) { setSearchTerm(e.target.value); }
  });

  if (loading) {
    return React.createElement('div', null, header, inputRicerca,
      React.createElement('p', { className: 'text-muted' }, 'Caricamento...'));
  }

  if (books.length === 0) {
    return React.createElement('div', null, header, inputRicerca,
      React.createElement('div', { className: 'alert alert-info text-center' },
        'Nessun libro trovato.'));
  }

  const cards = books.map(function(libro) {
    const copertina = (libro.copertina && libro.copertina.trim() !== "")
      ? React.createElement('div', {
          className: 'text-center bg-dark',
          style: { height: '380px', overflow: 'hidden' }
        },
          React.createElement('img', {
            src: libro.copertina,
            className: 'img-fluid w-100 h-100',
            style: { objectFit: 'contain' },
            alt: 'Copertina'
          })
        )
      : React.createElement('div', {
          className: 'bg-secondary text-white text-center py-5 d-flex align-items-center justify-content-center',
          style: { height: '380px' }
        },
          React.createElement('span', { className: 'display-3' },
            React.createElement('i', { className: 'bi bi-book' }))
        );

    const aggiungiForm = React.createElement('form', {
      action: '/cart/add/' + libro.id,
      method: 'post',
      className: 'mt-2'
    },
      React.createElement('input', {
        type: 'hidden',
        name: '_csrf',
        value: csrfToken
      }),
      React.createElement('button', {
        type: 'submit',
        className: 'btn btn-primary btn-sm w-100'
      }, '+ Carrello')
    );

    const corpo = React.createElement('div', { className: 'card-body d-flex flex-column' },
      React.createElement('h5', { className: 'card-title fw-bold mb-1 text-truncate' }, libro.titolo),
      React.createElement('p', { className: 'card-text text-muted small mb-3' }, 'di ' + libro.autore),
      React.createElement('div', { className: 'mt-auto d-flex justify-content-between align-items-center' },
        React.createElement('span', { className: 'fs-5 fw-bold text-success' }, libro.prezzo + ' €'),
        React.createElement('a', {
          href: '/libri/' + libro.id,
          className: 'btn btn-outline-primary btn-sm'
        }, 'Dettagli')
      ),
      mostraCarrello ? aggiungiForm : null
    );

    const card = React.createElement('div', { className: 'card h-100 shadow-sm border-0' },
      copertina, corpo);

    return React.createElement('div', { className: 'col', key: libro.id }, card);
  });

  const grid = React.createElement('div',
    { className: 'row row-cols-1 row-cols-md-3 row-cols-lg-4 g-4' }, cards);

  return React.createElement('div', null, header, inputRicerca, grid);
}

const container = document.getElementById('catalogo-react');
const root = ReactDOM.createRoot(container);
root.render(React.createElement(CatalogoLibri));