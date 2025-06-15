import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import BackendService from '../services/BackendService';

const UserComponent = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [login, setLogin] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [sait, setSait] = useState('');
    const [error, setError] = useState('');
    const [isNewPassword, setIsNewPassword] = useState(false);

    useEffect(() => {
        if (id !== 'new') {
            BackendService.retrieveUser(id)
                .then((resp) => {
                    setLogin(resp.data.login);
                    setEmail(resp.data.email);
                    setSait(resp.data.sait || '');
                    // Пароль не загружается и не отображается
                })
                .catch(() => navigate('/users'));
        }
    }, [id, navigate]);

    const handleSubmit = (e) => {
        e.preventDefault();
        if (!login.trim()) {
            setError('Логин обязателен');
            return;
        }
        if (!email.trim()) {
            setError('Email обязателен');
            return;
        }
        if (id === 'new' && !password.trim()) {
            setError('Пароль обязателен для нового пользователя');
            return;
        }

        const user = {
            login,
            email,
            sait
        };

        // Добавляем пароль только если он был изменен или это новый пользователь
        if (isNewPassword || id === 'new') {
            user.password = password;
        }

        const request = id === 'new'
            ? BackendService.createUser(user)
            : BackendService.updateUser(id, user);

        request
            .then(() => navigate('/users'))
            .catch((err) => setError(err.response?.data?.message || 'Ошибка сохранения'));
    };

    return (
        <div className="m-4">
            <h3>{id === 'new' ? 'Добавить пользователя' : 'Редактировать пользователя'}</h3>
            {error && <div className="alert alert-danger">{error}</div>}
            <form onSubmit={handleSubmit}>
                <div className="form-group mb-3">
                    <label>Логин:</label>
                    <input
                        type="text"
                        className="form-control"
                        value={login}
                        onChange={(e) => {
                            setLogin(e.target.value);
                            setError('');
                        }}
                        required
                    />
                </div>
                <div className="form-group mb-3">
                    <label>Email:</label>
                    <input
                        type="email"
                        className="form-control"
                        value={email}
                        onChange={(e) => {
                            setEmail(e.target.value);
                            setError('');
                        }}
                        required
                    />
                </div>
                <div className="form-group mb-3">
                    <label>Сайт:</label>
                    <input
                        type="text"
                        className="form-control"
                        value={sait}
                        onChange={(e) => {
                            setSait(e.target.value);
                            setError('');
                        }}
                    />
                </div>
                {id === 'new' ? (
                    <div className="form-group mb-3">
                        <label>Пароль:</label>
                        <input
                            type="password"
                            className="form-control"
                            value={password}
                            onChange={(e) => {
                                setPassword(e.target.value);
                                setError('');
                            }}
                            required
                        />
                    </div>
                ) : (
                    <div className="form-group mb-3">
                        <label>Новый пароль:</label>
                        <input
                            type="password"
                            className="form-control"
                            placeholder="Оставьте пустым, чтобы не менять"
                            value={password}
                            onChange={(e) => {
                                setPassword(e.target.value);
                                setIsNewPassword(!!e.target.value);
                                setError('');
                            }}
                        />
                    </div>
                )}
                <div className="mt-3">
                    <button type="submit" className="btn btn-primary me-2">
                        Сохранить
                    </button>
                    <button
                        type="button"
                        className="btn btn-secondary"
                        onClick={() => navigate('/users')}
                    >
                        Отмена
                    </button>
                </div>
            </form>
        </div>
    );
};

export default UserComponent;