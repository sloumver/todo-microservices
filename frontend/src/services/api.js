import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
});

api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    const userId = localStorage.getItem('userId');
    if (userId) {
      config.headers['X-User-Id'] = userId;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export const authService = {
  login: (username, password) => {
    return api.post('/auth/login', { username, password });
  },
  
  register: (username, email, password) => {
    return api.post('/auth/register', { username, email, password });
  },
};

export const todoService = {
  getTodos: () => {
    return api.get('/todos');
  },
  
  createTodo: (todo) => {
    return api.post('/todos', todo);
  },
  
  updateTodo: (id, todo) => {
    return api.put(`/todos/${id}`, todo);
  },
  
  deleteTodo: (id) => {
    return api.delete(`/todos/${id}`);
  },
};

export default api;