import React, { useState } from "react";
import axios from "axios";
import "../css/StartOrder.css";

const StartOrder = () => {
  const [clientUsername, setClientUsername] = useState(""); // 输入的客户用户名
  const [message, setMessage] = useState(""); // 显示成功或错误信息

  // 提交表单的处理函数
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        "http://localhost:8080/api/orders/start",
        null, // POST 请求没有请求体，这里设置为 null
        {
          params: { clientUsername },
          withCredentials: true, // 确保请求携带 credentials
        }
      );
      setMessage(response.data); // 显示成功信息
    } catch (err) {
      // 处理错误信息
      if (err.response && err.response.data) {
        setMessage(err.response.data);
      } else {
        setMessage("An unexpected error occurred.");
      }
    }
  };

  return (
    <div className="start-order-container">
      <h2>Start Order</h2>
      <form onSubmit={handleSubmit}>
        {/* 输入框 */}
        <label>Client Username:</label>
        <input
          type="text"
          value={clientUsername}
          onChange={(e) => setClientUsername(e.target.value)}
          required
        />

        {/* 提交按钮 */}
        <button type="submit">Start Order</button>
      </form>

      {/* 显示成功或错误信息 */}
      {message && <p className="message">{message}</p>}
    </div>
  );
};

export default StartOrder;
