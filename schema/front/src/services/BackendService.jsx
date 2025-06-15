import axios from 'axios'
import {alertActions, store} from "../utils/Rdx";

import Utils from "../utils/Utils";

const API_URL = 'http://localhost:8081/api/v1'
const AUTH_URL = 'http://localhost:8081/auth'


function showError(msg)
{
    store.dispatch(alertActions.error(msg))
}

axios.interceptors.request.use(
    config => {
        store.dispatch(alertActions.clear())
        let token = Utils.getToken();
        if (token)
            config.headers.Authorization = token;
        return config;
    },
    error => {
        showError(error.message)
        return Promise.reject(error);
    })

axios.interceptors.response.use(undefined,
    error => {
        if (error.response && error.response.status && [401, 403].indexOf(error.response.status) !== -1)
            showError("Ошибка авторизации")
        else if (error.response && error.response.data && error.response.data.message)
            showError(error.response.data.message)
        else
            showError(error.message)
        return Promise.reject(error);
    })

class BackendService {

    login(login, password) {
        return axios.post(`${AUTH_URL}/login`, {login, password})
    }

    logout() {
        return axios.get(`${AUTH_URL}/logout`, { headers : {Authorization : Utils.getToken()}})
    }

    /* Countries */

    retrieveAllCountries(page, limit) {
        return axios.get(`${API_URL}/countries?page=${page}&limit=${limit}`);
    }

    retrieveCountry(id) {
        return axios.get(`${API_URL}/countries/${id}`);
    }

    createCountry(country) {
        return axios.post(`${API_URL}/countries/`, country);
    }

    updateCountry(country) {
        return axios.put(`${API_URL}/countries/${country.id}`, country);
    }

    deleteCountries(countries) {
        return axios.post(`${API_URL}/countries/deletecountries`, countries);
    }

    /* Artists */

    retrieveAllArtists(page, limit) {
        return axios.get(`${API_URL}/artists?page=${page}&limit=${limit}`);
    }

    retrieveArtists(id) {
        return axios.get(`${API_URL}/artists/${id}`);
    }

    createArtists(artist) {
        return axios.post(`${API_URL}/artists/`, artist);
    }

    updateArtists(artist) {
        return axios.put(`${API_URL}/artists/${artist.id}`, artist);
    }

    deleteArtists(artists) {
        return axios.post(`${API_URL}/artists/deleteartists`, artists);
    }

    /* Museums */

    retrieveAllMuseums(page, limit) {
        return axios.get(`${API_URL}/museums?page=${page}&limit=${limit}`);
    }

    retrieveMuseum(id) {
        return axios.get(`${API_URL}/museums/${id}`);
    }

    createMuseum(museum) {
        return axios.post(`${API_URL}/museums/`, museum);
    }

    updateMuseum(id, museum) {
        return axios.put(`${API_URL}/museums/${id}`, museum);
    }

    deleteMuseums(museums) {
        return axios.post(`${API_URL}/museums/deletemuseums`, museums);
    }

    /* Paintings */

    retrieveAllPaintings(page, limit) {
        return axios.get(`${API_URL}/paintings?page=${page}&limit=${limit}`);
    }

    retrievePainting(id) {
        return axios.get(`${API_URL}/paintings/${id}`);
    }

    createPainting(painting) {
        return axios.post(`${API_URL}/paintings/`, painting);
    }

    updatePainting(id, painting) {
        return axios.put(`${API_URL}/paintings/${id}`, painting);
    }

    deletePaintings(paintings) {
        return axios.post(`${API_URL}/paintings/deletepaintings`, paintings);
    }

    /* Users */

    retrieveAllUsers(page, limit) {
        return axios.get(`${API_URL}/users?page=${page}&limit=${limit}`);
    }

    retrieveUser(id) {
        return axios.get(`${API_URL}/users/${id}`);
    }

    createUser(user) {
        return axios.post(`${API_URL}/users/`, user);
    }

    updateUser(id, user) {
        return axios.put(`${API_URL}/users/${id}`, user);
    }

    deleteUsers(users) {
        return axios.post(`${API_URL}/users/deleteusers`, users);
    }
}

export default new BackendService()