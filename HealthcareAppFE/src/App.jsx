import { Routes, Route, Navigate } from "react-router-dom";
import Login from "./pages/Login";
import Register from "./pages/Register";
import CitizenDashboard from "./pages/CitizenDashboard";
import DoctorDashboard from "./pages/DoctorDashboard";

function App() {
  const token = localStorage.getItem("token");

  return (
    <Routes>
      <Route path="/" element={<Navigate to="/login" />} />
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />

      <Route
        path="/citizen"
        element={token ? <CitizenDashboard /> : <Navigate to="/login" />}
      />

      <Route
        path="/doctor"
        element={token ? <DoctorDashboard /> : <Navigate to="/login" />}
      />
    </Routes>
  );
}

export default App;