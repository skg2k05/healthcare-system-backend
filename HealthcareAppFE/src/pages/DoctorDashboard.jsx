import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

function getStatusClass(status) {
  if (status === "BOOKED") return "status-booked";
  if (status === "COMPLETED") return "status-completed";
  return "status-cancelled";
}

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
    <div className="page">
      <div className="row">
        <h2>Doctor Dashboard</h2>
        <button onClick={handleLogout} className="danger">Logout</button>
      </div>

      <div className="panel">
        <h3>My Appointments (Page {page + 1})</h3>

        {appointments.length === 0 ? (
          <p className="muted">No appointments found.</p>
        ) : (
          <div className="card-list">
            {appointments.map((appointment) => (
              <div key={appointment.id} className="card">
                <strong>Patient:</strong> {appointment.patientEmail} <br />
                <strong>Specialization:</strong> {appointment.specialization} <br />
                <strong>Status:</strong> <span className={getStatusClass(appointment.status)}>{appointment.status}</span>

                {appointment.status === "BOOKED" && (
                  <div className="actions">
                    <button onClick={() => handleStatusUpdate(appointment.id, "COMPLETED")} className="primary">
                      Mark Completed
                    </button>
                    <button onClick={() => handleStatusUpdate(appointment.id, "CANCELLED")} className="ghost">
                      Cancel
                    </button>
                  </div>
                )}
              </div>
            ))}
          </div>
        )}

        <div className="actions" style={{ marginTop: 16 }}>
          <button onClick={() => setPage(page - 1)} disabled={page === 0}>
            Previous
          </button>
          <button onClick={() => setPage(page + 1)} disabled={page + 1 >= totalPages}>
            Next
          </button>
        </div>
      </div>
    </div>
  );
}

export default DoctorDashboard;
