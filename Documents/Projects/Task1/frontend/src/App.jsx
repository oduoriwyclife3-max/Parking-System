import React, { useState } from 'react';
import Login from './components/Login';
import ParkingDashboard from './components/ParkingDashboard';

export default function App() {
    const [isAuthenticated, setIsAuthenticated] = useState(!!localStorage.getItem('jwt_token'));

    if (!isAuthenticated) {
        return <Login onLoginSuccess={() => setIsAuthenticated(true)} />;
    }

    return <ParkingDashboard />;
}