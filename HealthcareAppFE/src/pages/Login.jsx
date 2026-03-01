import { useState } from "react";
import axios from "axios";

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post(
        "http://localhost:8080/api/auth/login",
        { email, password }
      );

      const token = response.data.token;
      const role = response.data.role;

      localStorage.setItem("token", token);
      localStorage.setItem("role", role);

      alert("Login Successful!");

    } catch (error) {
      alert("Login Failed");
      console.error(error);
    }
  };

  return (
    <div style={{ padding: "50px" }}>
      <h2>Login</h2>
      <form onSubmit={handleLogin}>
        <div>
          <input
            type="email"
            placeholder="Enter Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>
        <br />
        <div>
          <input
            type="password"
            placeholder="Enter Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <br />
        <button type="submit">Login</button>
      </form>
    </div>
  );
}

export default Login;