import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

function CitizenDashboard() {
  const navigate = useNavigate();
  const [appointments, setAppointments] = useState([]);

  const [doctorId, setDoctorId] = useState("");
  const [appointmentDate, setAppointmentDate] = useState("");

  useEffect(() => {
    fetchAppointments();
  }, []);

  const fetchAppointments = async () => {
    try {
      const response = await api.get("/api/appointments/my");
      setAppointments(response.data);
    } catch (error) {
      console.error("Error fetching appointments:", error);
    }
  };

  const handleBooking = async (e) => {
    e.preventDefault();

    // Formatting date to include seconds for Spring Boot LocalDateTime
    const formattedDate = appointmentDate.includes(":") && appointmentDate.split(":").length === 2
      ? `${appointmentDate}:00`
      : appointmentDate;

    const payload = {
      doctorId: Number(doctorId),
      appointmentDate: formattedDate,
    };

    try {
      const response = await api.post("/api/appointments", payload);

      if (response.status === 200 || response.status === 201) {
        alert("Appointment booked successfully!");
        setDoctorId("");
        setAppointmentDate("");
        fetchAppointments();
      }
    } catch (error) {
      if (error.response) {
        console.error("Backend Error Data:", error.response.data);
        alert(`Booking failed: ${error.response.data.message || "Server Error"}`);
      } else {
        console.error("Request Error:", error.message);
        alert("Booking failed: Could not connect to server.");
      }
    }
  };

  const handleCancel = async (id) => {
    try {
      await api.patch(`/api/appointments/${id}/cancel`);
      fetchAppointments();
    } catch (error) {
      console.error("Cancel failed:", error);
    }
  };

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
    navigate("/login");
  };

  return (
    <div style={{ padding: "50px", fontFamily: "Arial, sans-serif" }}>
      <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>
        <h2>Citizen Dashboard</h2>
        <button onClick={handleLogout} style={{ backgroundColor: "#ff4d4d", color: "white", border: "none", padding: "10px 20px", cursor: "pointer" }}>
          Logout
        </button>
      </div>

      <hr />

      <div style={{ maxWidth: "400px", background: "#f9f9f9", padding: "20px", borderRadius: "8px" }}>
        <h3>Book Appointment</h3>
        <form onSubmit={handleBooking}>
          <div style={{ marginBottom: "15px" }}>
            <label>Doctor ID:</label>
            <input
              type="number"
              placeholder="Enter Doctor ID"
              value={doctorId}
              onChange={(e) => setDoctorId(e.target.value)}
              required
              style={{ width: "100%", padding: "8px", marginTop: "5px" }}
            />
          </div>

          <div style={{ marginBottom: "15px" }}>
            <label>Date & Time:</label>
            <input
              type="datetime-local"
              value={appointmentDate}
              onChange={(e) => setAppointmentDate(e.target.value)}
              required
              style={{ width: "100%", padding: "8px", marginTop: "5px" }}
            />
          </div>

          <button type="submit" style={{ width: "100%", padding: "10px", backgroundColor: "#007bff", color: "white", border: "none", cursor: "pointer" }}>
            Confirm Booking
          </button>
        </form>
      </div>

      <hr />

      <h3>My Appointments</h3>

      {appointments.length === 0 ? (
        <p>No appointments found.</p>
      ) : (
        <div style={{ display: "grid", gap: "15px" }}>
          {appointments.map((appointment) => (
            <div key={appointment.id} style={{ border: "1px solid #ddd", padding: "15px", borderRadius: "5px" }}>
              <strong>Doctor:</strong> {appointment.doctorName} <br />
              <strong>Specialization:</strong> {appointment.specialization} <br />
              <strong>Date:</strong> {new Date(appointment.appointmentDate).toLocaleString()} <br />
              <strong>Status:</strong>{" "}
              <span
                style={{
                  color:
                    appointment.status === "BOOKED"
                      ? "blue"
                      : appointment.status === "COMPLETED"
                      ? "green"
                      : "red",
                  fontWeight: "bold",
                }}
              >
                {appointment.status}
              </span>

              {appointment.status === "BOOKED" && (
                <div style={{ marginTop: "10px" }}>
                  <button
                    onClick={() => handleCancel(appointment.id)}
                    style={{ backgroundColor: "transparent", color: "red", border: "1px solid red", cursor: "pointer", padding: "5px 10px" }}
                  >
                    Cancel Appointment
                  </button>
                </div>
              )}
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default CitizenDashboard;