import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import BackendService from '../services/BackendService';

const ArtistComponent = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [name, setName] = useState('');
    const [century, setCentury] = useState('');
    const [countryId, setCountryId] = useState('');
    const [countries, setCountries] = useState([]);
    const [error, setError] = useState('');

    useEffect(() => {
        BackendService.retrieveAllCountries(0, 100)
            .then(resp => setCountries(resp.data.content))
            .catch(() => setError('Ошибка загрузки списка стран'));

        if (id !== 'new') {
            BackendService.retrieveArtist(id)
                .then((resp) => {
                    setName(resp.data.name);
                    setCentury(resp.data.century);
                    setCountryId(resp.data.country?.id || '');
                })
                .catch(() => navigate('/artists'));
        }
    }, [id, navigate]);

    const handleSubmit = (e) => {
        e.preventDefault();
        if (!name.trim()) {
            setError('Имя художника обязательно');
            return;
        }
        if (!century.trim()) {
            setError('Век обязателен');
            return;
        }

        const artist = {
            name,
            century,
            country: countryId ? { id: countryId } : null
        };

        const request = id === 'new'
            ? BackendService.createArtist(artist)
            : BackendService.updateArtist(id, artist);

        request
            .then(() => navigate('/artists'))
            .catch((err) => setError(err.response?.data?.message || 'Ошибка сохранения'));
    };

    return (
        <div className="m-4">
            <h3>{id === 'new' ? 'Добавить художника' : 'Редактировать художника'}</h3>
            {error && <div className="alert alert-danger">{error}</div>}
            <form onSubmit={handleSubmit}>
                <div className="form-group mb-3">
                    <label>Имя художника:</label>
                    <input
                        type="text"
                        className="form-control"
                        value={name}
                        onChange={(e) => {
                            setName(e.target.value);
                            setError('');
                        }}
                        required
                    />
                </div>
                <div className="form-group mb-3">
                    <label>Век:</label>
                    <input
                        type="text"
                        className="form-control"
                        value={century}
                        onChange={(e) => {
                            setCentury(e.target.value);
                            setError('');
                        }}
                        required
                    />
                </div>
                <div className="form-group mb-3">
                    <label>Страна:</label>
                    <select
                        className="form-control"
                        value={countryId}
                        onChange={(e) => setCountryId(e.target.value)}
                    >
                        <option value="">-- Выберите страну --</option>
                        {countries.map(country => (
                            <option key={country.id} value={country.id}>
                                {country.name}
                            </option>
                        ))}
                    </select>
                </div>
                <div className="mt-3">
                    <button type="submit" className="btn btn-primary me-2">
                        Сохранить
                    </button>
                    <button
                        type="button"
                        className="btn btn-secondary"
                        onClick={() => navigate('/artists')}
                    >
                        Отмена
                    </button>
                </div>
            </form>
        </div>
    );
};

export default ArtistComponent;