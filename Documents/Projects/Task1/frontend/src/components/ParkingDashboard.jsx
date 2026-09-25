import React, { useEffect, useState } from 'react';
import { getParkingSlots, enterVehicle, exitVehicle } from '../api';

export default function ParkingDashboard() {
    const [slots, setSlots] = useState([]);
    const [regNumber, setRegNumber] = useState('');

    const loadSlots = async () => {
        try {
            const res = await getParkingSlots();
            setSlots(res.data);
        } catch (err) {
            console.error("Error loading slots", err);
        }
    };

    useEffect(() => { loadSlots(); }, []);

    const handleEntry = async () => {
        try {
            await enterVehicle({ registrationNumber: regNumber });
            alert("Vehicle Entry Successful");
            setRegNumber('');
            loadSlots();
        } catch {
            alert("Parking full or error occurred");
        }
    };

    const handleExit = async () => {
        try {
            const res = await exitVehicle(regNumber);
            alert(`Exit Successful. Fee: ${res.data.fee}`);
            setRegNumber('');
            loadSlots();
        } catch {
            alert("Vehicle Not Found");
        }
    };

    return (
        <div style={{ padding: '2rem' }}>
            <h2>Automated Parking Dashboard</h2>
            <div style={{ margin: '20px 0' }}>
                <input placeholder="Vehicle Reg No" value={regNumber} onChange={e => setRegNumber(e.target.value)} />
                <button onClick={handleEntry} style={{ marginLeft: '10px' }}>Vehicle Entry</button>
                <button onClick={handleExit} style={{ marginLeft: '10px' }}>Vehicle Exit & Pay</button>
            </div>
            <h3>Available Slots</h3>
            <ul>
                {slots.map(slot => <li key={slot.id}>Slot {slot.number}: {slot.status}</li>)}
            </ul>
        </div>
    );
}