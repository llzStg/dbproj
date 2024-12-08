// components/LogoutButton.js
import React from "react";
import { useNavigate } from "react-router-dom";
import { logout } from "../api/authService";

const LogoutButton = ({ setAuth }) => {
  const navigate = useNavigate();

  const handleLogout = async () => {
    console.log("Logout button clicked"); // 调试日志
    try {
      await logout(); // 调用后端 /logout API
      sessionStorage.clear()
      setAuth(false); // 更新前端登录状态
      navigate("/login"); // 跳转到登录页面
    } catch (error) {
      console.error("Logout failed:", error);
      alert("Failed to logout. Please try again.");
    }
  };

  return (
    <button onClick={handleLogout}>Logout</button>
  );
};

export default LogoutButton;
