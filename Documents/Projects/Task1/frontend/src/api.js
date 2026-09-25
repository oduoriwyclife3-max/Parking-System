import axios from 'axios';

const API = axios.create({ baseURL: 'http://localhost:9090/api/v1' });

API.interceptors.request.use((config) => {
    const token = localStorage.getItem('jwt_token');
    if (token) config.headers.Authorization = `Bearer ${token}`;
    return config;
});

export const loginUser = (credentials) => API.post('/auth/login', credentials);
export const getParkingSlots = () => API.get('/parking/slots');
export const enterVehicle = (data) => API.post('/parking/entry', data);
export const exitVehicle = (regNo) => API.post(`/parking/exit/${regNo}`);