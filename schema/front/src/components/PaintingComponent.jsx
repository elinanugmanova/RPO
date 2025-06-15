import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import BackendService from '../services/BackendService';

const PaintingComponent = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [name, setName] = useState('');
    const [year, setYear] = useState('');
    const [artistId, setArtistId] = useState('');
    const [museumId, setMuseumId] = useState('');
    const [artists, setArtists] = useState([]);
    const [museums, setMuseums] = useState([]);
    const [error, setError] = useState('');

    useEffect(() => {
        // Загрузка списка художников и музеев
        Promise.all([
            BackendService.retrieveAllArtists(0, 100),
            BackendService.retrieveAllMuseums(0, 100)
        ])
        .then(([artistsResp, museumsResp]) => {
            setArtists(artistsResp.data.content);
            setMuseums(museumsResp.data.content);
        })
        .catch(() => setError('Ошибка загрузки данных'));

        if (id !== 'new') {
            BackendService.retrievePainting(id)
                .then((resp) => {
                    setName(resp.data.name);
                    setYear(resp.data.year);
                    setArtistId(resp.data.artist?.id || '');
                    setMuseumId(resp.data.museum?.id || '');
                })
                .catch(() => navigate('/paintings'));
        }
    }, [id, navigate]);

    const handleSubmit = (e) => {
        e.preventDefault();
        if (!name.trim()) {
            setError('Название картины обязательно');
            return;
        }
        if (!year) {
            setError('Год создания обязателен');
            return;
        }

        const painting = {
            name,
            year: parseInt(year),
            artist: artistId ? { id: artistId } : null,
            museum: museumId ? { id: museumId } : null
        };

        const request = id === 'new'
            ? BackendService.createPainting(painting)
            : BackendService.updatePainting(id, painting);

        request
            .then(() => navigate('/paintings'))
            .catch((err) => setError(err.response?.data?.message || 'Ошибка сохранения'));
    };

    return (
        <div className="m-4">
            <h3>{id === 'new' ? 'Добавить картину' : 'Редактировать картину'}</h3>
            {error && <div className="alert alert-danger">{error}</div>}
            <form onSubmit={handleSubmit}>
                <div className="form-group mb-3">
                    <label>Название картины:</label>
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
                    <label>Год создания:</label>
                    <input
                        type="number"
                        className="form-control"
                        value={year}
                        onChange={(e) => {
                            setYear(e.target.value);
                            setError('');
                        }}
                        required
                    />
                </div>
                <div className="form-group mb-3">
                    <label>Художник:</label>
                    <select
                        className="form-control"
                        value={artistId}
                        onChange={(e) => setArtistId(e.target.value)}
                    >
                        <option value="">-- Выберите художника --</option>
                        {artists.map(artist => (
                            <option key={artist.id} value={artist.id}>
                                {artist.name}
                            </option>
                        ))}
                    </select>
                </div>
                <div className="form-group mb-3">
                    <label>Музей:</label>
                    <select
                        className="form-control"
                        value={museumId}
                        onChange={(e) => setMuseumId(e.target.value)}
                    >
                        <option value="">-- Выберите музей --</option>
                        {museums.map(museum => (
                            <option key={museum.id} value={museum.id}>
                                {museum.name}
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
                        onClick={() => navigate('/paintings')}
                    >
                        Отмена
                    </button>
                </div>
            </form>
        </div>
    );
};

export default PaintingComponent;