import React, { useState } from 'react';
import { loginUser } from '../api';
import axios from 'axios';

export default function Login({ onLoginSuccess }) {
    const [isRegistering, setIsRegistering] = useState(false);
    const [form, setForm] = useState({ username: '', password: '' });

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if (isRegistering) {
                // Register new user
                await axios.post('http://localhost:9090/api/v1/auth/register', form);
                alert('Registration successful! Please log in.');
                setIsRegistering(false);
            } else {
                // Login
                const res = await loginUser(form);
                localStorage.setItem('jwt_token', res.data.token);
                onLoginSuccess();
            }
        } catch (err) {
            alert(isRegistering ? 'Registration failed. Username might be taken.' : 'Invalid username or password');
        }
    };

    return (
        <div style={{ padding: '2rem' }}>
            <h2>{isRegistering ? 'Register New Account' : 'System Login'}</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <input 
                        type="text" 
                        placeholder="Username" 
                        value={form.username}
                        onChange={e => setForm({...form, username: e.target.value})} 
                    />
                </div>
                <div style={{ marginTop: '10px' }}>
                    <input 
                        type="password" 
                        placeholder="Password" 
                        value={form.password}
                        onChange={e => setForm({...form, password: e.target.value})} 
                    />
                </div>
                <button style={{ marginTop: '10px' }} type="submit">
                    {isRegistering ? 'Register' : 'Login'}
                </button>
            </form>
            <p style={{ marginTop: '15px', cursor: 'pointer', color: 'blue' }} onClick={() => setIsRegistering(!isRegistering)}>
                {isRegistering ? 'Already have an account? Login here' : "Don't have an account? Register here"}
            </p>
        </div>
    );
}