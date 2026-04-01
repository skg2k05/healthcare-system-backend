import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

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
      alert("Booking failed");
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
    <div style={{ padding: "50px" }}>
      <h2>Citizen Dashboard</h2>
      <button onClick={handleLogout}>Logout</button>
      <hr />

      <h3>Book Appointment</h3>
      <form onSubmit={handleBooking}>
        <div>
          <div>Doctor ID:</div>
          <select value={doctorId} onChange={(e) => setDoctorId(e.target.value)} required>
            <option value="">Select a doctor</option>
            {doctors.map((doctor) => (
              <option key={doctor.id} value={doctor.id}>
                {doctor.name} (ID: {doctor.id}) - {doctor.specialization}
              </option>
            ))}
          </select>
        </div>
        <br />
        <div>
          <div>Date & Time:</div>
          <input
            type="datetime-local"
            value={appointmentDate}
            onChange={(e) => setAppointmentDate(e.target.value)}
            required
          />
        </div>
        <br />
        <button type="submit">Confirm Booking</button>
      </form>

      <hr />
      <h3>My Appointments</h3>
      {appointments.length === 0 ? (
        <p>No appointments found.</p>
      ) : (
        <ul>
          {appointments.map((appointment) => (
            <li key={appointment.id} style={{ marginBottom: "16px" }}>
              <strong>Doctor:</strong> {appointment.doctorName}<br />
              <strong>Specialization:</strong> {appointment.specialization}<br />
              <strong>Date:</strong> {appointment.appointmentDate}<br />
              <strong>Status:</strong> {appointment.status}<br />
              {appointment.status === "BOOKED" && (
                <button onClick={() => handleCancel(appointment.id)}>Cancel Appointment</button>
              )}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default CitizenDashboard;
