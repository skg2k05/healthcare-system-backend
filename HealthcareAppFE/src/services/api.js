import axios from "axios";

const configuredBaseUrl = import.meta.env.VITE_API_BASE_URL;
const configuredTimeout = Number(import.meta.env.VITE_API_TIMEOUT_MS);

const defaultBaseUrl =
  import.meta.env.DEV
    ? "http://localhost:8081"
    : "https://healthcare-system-backend-t79z.onrender.com";

const api = axios.create({
  baseURL: configuredBaseUrl || defaultBaseUrl,
  timeout: Number.isFinite(configuredTimeout) && configuredTimeout > 0
    ? configuredTimeout
    : 60000,
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
