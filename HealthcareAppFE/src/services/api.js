import axios from "axios";

//const api = axios.create({
//  baseURL: "http://localhost:8081",
//});
const API_BASE_URL = "https://healthcare-system-backend-t79z.onrender.com";

// Attach JWT automatically
api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

export default api;