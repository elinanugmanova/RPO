import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import BackendService from '../services/BackendService';

const MuseumComponent = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [name, setName] = useState('');
    const [location, setLocation] = useState('');
    const [error, setError] = useState('');

    useEffect(() => {
        if (id !== 'new') {
            BackendService.retrieveMuseum(id)
                .then((resp) => {
                    setName(resp.data.name);
                    setLocation(resp.data.location);
                })
                .catch(() => navigate('/museums'));
        }
    }, [id, navigate]);

    const handleSubmit = (e) => {
        e.preventDefault();
        if (!name.trim()) {
            setError('Название музея обязательно');
            return;
        }
        if (!location.trim()) {
            setError('Местоположение обязательно');
            return;
        }

        const museum = {
            name,
            location
        };

        const request = id === 'new'
            ? BackendService.createMuseum(museum)
            : BackendService.updateMuseum(id, museum);

        request
            .then(() => navigate('/museums'))
            .catch((err) => setError(err.response?.data?.message || 'Ошибка сохранения'));
    };

    return (
        <div className="m-4">
            <h3>{id === 'new' ? 'Добавить музей' : 'Редактировать музей'}</h3>
            {error && <div className="alert alert-danger">{error}</div>}
            <form onSubmit={handleSubmit}>
                <div className="form-group mb-3">
                    <label>Название музея:</label>
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
                    <label>Местоположение:</label>
                    <input
                        type="text"
                        className="form-control"
                        value={location}
                        onChange={(e) => {
                            setLocation(e.target.value);
                            setError('');
                        }}
                        required
                    />
                </div>
                <div className="mt-3">
                    <button type="submit" className="btn btn-primary me-2">
                        Сохранить
                    </button>
                    <button
                        type="button"
                        className="btn btn-secondary"
                        onClick={() => navigate('/museums')}
                    >
                        Отмена
                    </button>
                </div>
            </form>
        </div>
    );
};

export default MuseumComponent;