import axios from "axios";

const configuredBaseUrl = import.meta.env.VITE_API_BASE_URL;

const defaultBaseUrl =
  import.meta.env.DEV
    ? "http://localhost:8081"
    : "https://healthcare-system-backend-t79z.onrender.com";

const api = axios.create({
  baseURL: configuredBaseUrl || defaultBaseUrl,
  timeout: 15000,
});

// Attach JWT automatically
api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

export default api;
