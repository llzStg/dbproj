import React, { createContext, useContext, useState } from "react";

// 创建 SessionContext
const SessionContext = createContext();

// 提供者组件
export const SessionProvider = ({ children }) => {
  const [sessionData, setSessionData] = useState(null);

  // 定义一个方法更新 session 数据
  const updateSessionData = (data) => {
    setSessionData(data);
  };

  return (
    <SessionContext.Provider value={{ sessionData, updateSessionData }}>
      {children}
    </SessionContext.Provider>
  );
};

// 自定义 Hook，用于访问 Context
export const useSession = () => useContext(SessionContext);
