import { Navigate, Route, Routes } from 'react-router-dom';
import Dashboard from './pages/Dashboard.jsx';
import Login from './pages/Login.jsx';

export default function App() {
  return (
    <Routes>
      <Route path="/" element={<Login />} />
      <Route path="/login" element={<Login />} />
      <Route
        path="/employee-dashboard"
        element={(
          <Dashboard
            title="Employee Dashboard"
            description="You are signed in to the employee workspace."
          />
        )}
      />
      <Route
        path="/cashier-dashboard"
        element={(
          <Dashboard
            title="Cashier Dashboard"
            description="You are signed in to the cashier workspace."
          />
        )}
      />
      <Route
        path="/relationship-manager-dashboard"
        element={(
          <Dashboard
            title="Relationship Manager Dashboard"
            description="You are signed in to the relationship manager workspace."
          />
        )}
      />
      <Route
        path="/branch-manager-dashboard"
        element={(
          <Dashboard
            title="Branch Manager Dashboard"
            description="You are signed in to the branch manager workspace."
          />
        )}
      />
      <Route
        path="/admin-dashboard"
        element={(
          <Dashboard
            title="Admin Dashboard"
            description="You are signed in to the admin workspace."
          />
        )}
      />
      <Route
        path="/customer-dashboard"
        element={(
          <Dashboard
            title="Customer Dashboard"
            description="You are signed in to the customer workspace."
          />
        )}
      />
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
}
