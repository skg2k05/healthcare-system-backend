import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

function DoctorDashboard() {
  const navigate = useNavigate();
  const [appointments, setAppointments] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  useEffect(() => {
    fetchAppointments();
  }, [page]);

  const fetchAppointments = async () => {
    try {
      const response = await api.get(`/api/appointments/doctor/my?page=${page}&size=5`);
      setAppointments(response.data.content || []);
      setTotalPages(response.data.totalPages || 0);
    } catch (error) {
      console.error("Error fetching doctor appointments", error);
    }
  };

  const handleStatusUpdate = async (id, newStatus) => {
    try {
      await api.patch(`/api/appointments/${id}/status`, { status: newStatus });
      fetchAppointments();
    } catch (error) {
      console.error("Status update failed", error);
    }
  };

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
    navigate("/");
  };

  return (
    <div style={{ padding: "50px" }}>
      <h2>Doctor Dashboard</h2>
      <button onClick={handleLogout}>Logout</button>
      <hr />
      <h3>My Appointments (Page {page + 1})</h3>

      {appointments.length === 0 ? (
        <p>No appointments found.</p>
      ) : (
        <ul>
          {appointments.map((appointment) => (
            <li key={appointment.id} style={{ marginBottom: "20px" }}>
              <strong>Patient:</strong> {appointment.patientEmail}<br />
              <strong>Specialization:</strong> {appointment.specialization}<br />
              <strong>Status:</strong> {appointment.status}<br />
              {appointment.status === "BOOKED" && (
                <>
                  <button onClick={() => handleStatusUpdate(appointment.id, "COMPLETED")}>
                    Mark Completed
                  </button>{" "}
                  <button onClick={() => handleStatusUpdate(appointment.id, "CANCELLED")}>
                    Cancel
                  </button>
                </>
              )}
            </li>
          ))}
        </ul>
      )}

      <hr />
      <button onClick={() => setPage(page - 1)} disabled={page === 0}>Previous</button>{" "}
      <button onClick={() => setPage(page + 1)} disabled={page + 1 >= totalPages}>Next</button>
    </div>
  );
}

export default DoctorDashboard;
