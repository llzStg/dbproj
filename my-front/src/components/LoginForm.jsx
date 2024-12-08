import React, { useState } from "react";
import { useNavigate } from "react-router-dom"; // 用于跳转页面
import { login } from "../api/authService";
import axios from "axios";

const LoginForm = ({ setAuth }) => {
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate(); // 初始化 navigate

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      console.log("It is login page!")
      // 调用登录 API，登录成功后会把 session 数据保存在服务器端
      const response = await login(userName, password);
      const response2 = await axios.get(
        "http://localhost:8080/api/session/roleID",
        {
          withCredentials: true, // 确保请求携带 credentials
        }
      );

      const response3 = await axios.get(
        "http://localhost:8080/api/session/userName",
        {
          withCredentials: true, // 确保请求携带 credentials
        }
      );
      
      if (response.status === 200 && response2.status === 200 && response3.status === 200) {
        setAuth(true); // 设置用户已登录
        setError(""); // 清除错误信息
        const roleID = response2.data;
        const userName = response3.data;
      
        // 将角色和用户名存储在前端（比如存储在 sessionStorage 或 localStorage）
        sessionStorage.setItem("roleID", roleID);
        sessionStorage.setItem("userName", userName);
        console.log("This is the role after login:", sessionStorage.getItem("userName"));
       
        navigate("/home"); // 登录成功后跳转到主页
      }
    } catch (err) {
      setError("Invalid username or password"); // 设置错误信息
    }
  };

  return (
    <form onSubmit={handleLogin}>
      <h2>Login</h2>
      {/* 显示错误信息 */}
      {error && <p style={{ color: "red" }}>{error}</p>}
      <input
        type="text"
        placeholder="Username"
        value={userName}
        onChange={(e) => setUserName(e.target.value)}
        required
      />
      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        required
      />
      <button type="submit">Login</button>
    </form>
  );
};

export default LoginForm;
