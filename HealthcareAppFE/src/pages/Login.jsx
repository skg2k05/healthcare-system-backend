import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

function Login() {
  const navigate = useNavigate();
  const buildTag = import.meta.env.VITE_APP_BUILD_TAG || "local";
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const response = await api.post("/api/auth/login", { email, password });
      const { token, role } = response.data;

      localStorage.setItem("token", token);
      localStorage.setItem("role", role);

      if (role === "CITIZEN") navigate("/citizen-dashboard");
      if (role === "DOCTOR") navigate("/doctor-dashboard");
    } catch (error) {
      alert("Login failed. Please check email/password.");
      console.error(error);
    }
  };

  return (
    <div className="page">
      <div className="panel auth-box">
        <h2>Healthcare Login</h2>
        <p className="muted">Sign in as Citizen or Doctor.</p>

        <form onSubmit={handleLogin}>
          <label>
            Email
            <input
              type="email"
              placeholder="Enter email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </label>

          <label>
            Password
            <input
              type="password"
              placeholder="Enter password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </label>

          <div className="actions">
            <button type="submit" className="primary">Login</button>
          </div>
        </form>

        <p className="muted" style={{ marginTop: 12, fontSize: 12 }}>Build: {buildTag}</p>
      </div>
    </div>
  );
}

export default Login;
