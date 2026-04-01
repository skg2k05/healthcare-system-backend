import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./pages/Login";
import CitizenDashboard from "./pages/CitizenDashboard";
import DoctorDashboard from "./pages/DoctorDashboard";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/citizen-dashboard" element={<CitizenDashboard />} />
        <Route path="/doctor-dashboard" element={<DoctorDashboard />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
