import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import BackendService from '../services/BackendService';

const CountryComponent = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [name, setName] = useState('');
    const [error, setError] = useState('');

    useEffect(() => {
        if (id !== 'new') {
            BackendService.retrieveCountry(id)
                .then((resp) => setName(resp.data.name))
                .catch(() => navigate('/countries'));
        }
    }, [id, navigate]);

    const handleSubmit = (e) => {
        e.preventDefault();
        if (!name.trim()) {
            setError('Название страны обязательно');
            return;
        }

        const country = { name };
        const request = id === 'new'
            ? BackendService.createCountry(country)
            : BackendService.updateCountry(id, country);

        request
            .then(() => navigate('/countries'))
            .catch((err) => setError(err.response?.data?.message || 'Ошибка сохранения'));
    };

    return (
        <div className="m-4">
            <h3>{id === 'new' ? 'Добавить страну' : 'Редактировать страну'}</h3>
            {error && <div className="alert alert-danger">{error}</div>}
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>Название страны:</label>
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
                <div className="mt-3">
                    <button type="submit" className="btn btn-primary me-2">
                        Сохранить
                    </button>
                    <button
                        type="button"
                        className="btn btn-secondary"
                        onClick={() => navigate('/countries')}
                    >
                        Отмена
                    </button>
                </div>
            </form>
        </div>
    );
};

export default CountryComponent;