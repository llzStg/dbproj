import React, { createContext, useState, useEffect } from 'react';
import axios from 'axios';

const getSessionStatus = async () => {
  try {
    const response = await axios.get('/api/session/status', {
      withCredentials: true // 确保携带Cookie
    });
    return response.data;
  } catch (error) {
    console.error('Failed to fetch session status:', error);
    return null;
  }
};


export const SessionContext = createContext(null);

export const SessionProvider = ({ children }) => {
  const [sessionStatus, setSessionStatus] = useState(null);

  useEffect(() => {
    const fetchSessionStatus = async () => {
      const status = await getSessionStatus();
      setSessionStatus(status);
    };

    fetchSessionStatus();
  }, []);

  return (
    <SessionContext.Provider value={sessionStatus}>
      {children}
    </SessionContext.Provider>
  );
};