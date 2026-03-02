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
      console.error("Error fetching appointments", error);
    }
  };

  const handleBooking = async (e) => {
    e.preventDefault();

    try {
      await api.post("/api/appointments", {
        doctorId: Number(doctorId),
        appointmentDate: appointmentDate,
      });

      alert("Appointment booked successfully!");

      setDoctorId("");
      setAppointmentDate("");

      fetchAppointments(); // refresh list

    } catch (error) {
      console.error("Booking failed", error);
      alert("Booking failed");
    }
  };

  const handleCancel = async (id) => {
    try {
      await api.patch(`/api/appointments/${id}/cancel`);
      fetchAppointments();
    } catch (error) {
      console.error("Cancel failed", error);
    }
  };

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
    navigate("/login");
  };

  return (
    <div style={{ padding: "50px" }}>
      <h2>Citizen Dashboard</h2>

      <button onClick={handleLogout}>Logout</button>

      <hr />

      <h3>Book Appointment</h3>

      <form onSubmit={handleBooking}>
        <div>
          <input
            type="number"
            placeholder="Enter Doctor ID"
            value={doctorId}
            onChange={(e) => setDoctorId(e.target.value)}
            required
          />
        </div>

        <br />

        <div>
          <input
            type="datetime-local"
            value={appointmentDate}
            onChange={(e) => setAppointmentDate(e.target.value)}
            required
          />
        </div>

        <br />

        <button type="submit">Book Appointment</button>
      </form>

      <hr />

      <h3>My Appointments</h3>

      {appointments.length === 0 ? (
        <p>No appointments found.</p>
      ) : (
        <ul>
          {appointments.map((appointment) => (
            <li key={appointment.id} style={{ marginBottom: "15px" }}>
              <strong>Doctor:</strong> {appointment.doctorName} <br />
              <strong>Specialization:</strong> {appointment.specialization} <br />
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
                <>
                  <br />
                  <button onClick={() => handleCancel(appointment.id)}>
                    Cancel Appointment
                  </button>
                </>
              )}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default CitizenDashboard;