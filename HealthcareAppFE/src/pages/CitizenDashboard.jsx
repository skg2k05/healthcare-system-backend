import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

function formatAppointmentDate(rawDate) {
  if (!rawDate) return "-";
  const normalized = rawDate.includes("T") ? rawDate : rawDate.replace(" ", "T");
  const parsed = new Date(normalized);
  return Number.isNaN(parsed.getTime()) ? rawDate : parsed.toLocaleString();
}

function getStatusClass(status) {
  if (status === "BOOKED") return "status-booked";
  if (status === "COMPLETED") return "status-completed";
  return "status-cancelled";
}

function CitizenDashboard() {
  const navigate = useNavigate();
  const [appointments, setAppointments] = useState([]);
  const [doctors, setDoctors] = useState([]);

  const [doctorId, setDoctorId] = useState("");
  const [appointmentDate, setAppointmentDate] = useState("");

  useEffect(() => {
    fetchAppointments();
    fetchDoctors();
  }, []);

  const fetchAppointments = async () => {
    try {
      const response = await api.get("/api/appointments/my");
      setAppointments(response.data);
    } catch (error) {
      console.error("Error fetching appointments:", error);
    }
  };

  const fetchDoctors = async () => {
    try {
      const response = await api.get("/api/doctors");
      setDoctors(response.data || []);
    } catch (error) {
      console.error("Error fetching doctors:", error);
    }
  };

  const handleBooking = async (e) => {
    e.preventDefault();

    const formattedDate = appointmentDate.includes(":") && appointmentDate.split(":").length === 2
      ? `${appointmentDate}:00`
      : appointmentDate;

    try {
      await api.post("/api/appointments", {
        doctorId: Number(doctorId),
        appointmentDate: formattedDate,
      });

      alert("Appointment booked successfully!");
      setDoctorId("");
      setAppointmentDate("");
      fetchAppointments();
    } catch (error) {
      if (error.response) {
        alert(`Booking failed: ${error.response.data.message || "Server Error"}`);
      } else {
        alert("Booking failed: Could not connect to server.");
      }
      console.error(error);
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
    navigate("/");
  };

  return (
    <div className="page">
      <div className="row">
        <h2>Citizen Dashboard</h2>
        <button onClick={handleLogout} className="danger">Logout</button>
      </div>

      <div className="panel">
        <h3>Book Appointment</h3>
        <form onSubmit={handleBooking}>
          <div style={{ marginBottom: "15px" }}>
            <label>Doctor ID:</label>
            <select
              value={doctorId}
              onChange={(e) => setDoctorId(e.target.value)}
              required
              style={{ width: "100%", padding: "8px", marginTop: "5px" }}
            >
              <option value="">Select a doctor</option>
              {doctors.map((doctor) => (
                <option key={doctor.id} value={doctor.id}>
                  {doctor.name} (ID: {doctor.id}) - {doctor.specialization}
                </option>
              ))}
            </select>
          </div>

          <div>
            <label htmlFor="appointmentDate">Date &amp; Time</label>
            <input
              id="appointmentDate"
              type="datetime-local"
              value={appointmentDate}
              onChange={(e) => setAppointmentDate(e.target.value)}
              min={new Date().toISOString().slice(0, 16)}
              required
            />
          </label>

          <div className="actions">
            <button type="submit" className="primary">Confirm Booking</button>
          </div>
        </form>
      </div>

      <h3 style={{ marginTop: 20 }}>My Appointments</h3>
      {appointments.length === 0 ? (
        <p className="muted">No appointments found.</p>
      ) : (
        <div className="card-list">
          {appointments.map((appointment) => (
            <div key={appointment.id} className="card">
              <strong>Doctor:</strong> {appointment.doctorName} <br />
              <strong>Specialization:</strong> {appointment.specialization} <br />
              <strong>Date:</strong> {formatAppointmentDate(appointment.appointmentDate)} <br />
              <strong>Status:</strong> <span className={getStatusClass(appointment.status)}>{appointment.status}</span>

              {appointment.status === "BOOKED" && (
                <div className="actions">
                  <button onClick={() => handleCancel(appointment.id)} className="ghost">Cancel Appointment</button>
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
