import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Route, Routes, Navigate } from "react-router-dom";
import LoginForm from "./components/LoginForm";
import RegisterForm from "./components/RegisterForm";
import LogoutButton from "./components/LogoutButton";
import HomePage from "./components/HomePage"; // 引入登录后主页组件
import FindItem from "./components/FindItem";
import OrderItemsPage from "./components/OrderItemsPage";
import AcceptDonation from "./components/AcceptDonation";
import StartOrder from "./components/StartOrder";
import AddItemToOrder from "./components/AddItemToOrder";
import { Link } from "react-router-dom";
// import axios from "axios";

const App = () => {
  const [isAuth, setAuth] = useState(false);

  

  useEffect(() => {
    const role = sessionStorage.getItem("roleID");
    console.log("this is role:",role)
    if (role) {
      setAuth(true); // 如果有角色信息，则用户已认证
    }
  }, []);


  // const handleAcceptDonationClick = async () => {
  //   try {
  //     const response = await axios.get(
  //       "http://localhost:8080/api/session/roleID",
  //       {
  //         withCredentials: true, // 确保请求携带 credentials
  //       }
  //     );
  //     const roleId = response.data
  //     console.log(response.data)
  //     if (roleId === 1) { // 假设 '1' 表示 staff
  //       window.location.href = "/acceptDonation"; // 跳转到 Accept Donation 页面
  //     } else {
  //       window.alert("You are not authorized to accept donations.");
  //     }
  //   } catch (error) {
  //     console.error("Error while checking session:", error);
  //     window.alert("An error occurred. Please try again.");
  //   }
  // };

  return (
    <Router>
      <div>
        <h1>WelComeHome Page</h1>
        {/* 显示导航栏 */}
        <nav>
          {!isAuth && (
            <>
              <Link to="/login">Login</Link> |{" "}
              <Link to="/register">Register</Link>
            </>
          )}
          {isAuth && (
            <>
              <Link to="/home">Home</Link> |{" "}
              <Link to="/finditem">Find Item</Link> |{" "}
              <Link to="/getItemsByOrderID">Get Order Item</Link> |{" "}
              <Link to="/acceptDonation">Accept Donation</Link> |{" "}
              <Link to="/startOrder">Start Order</Link> |{" "}
              <Link to="/addItemToOrder"
              state={{ orderId: sessionStorage.getItem("orderId") }}>Add Item To Order</Link> |{" "}
              <LogoutButton setAuth={setAuth} />
            </>
          )}
        </nav>
        {/* 路由配置 */}
        <Routes>
          <Route
            path="/login"
            element={
              isAuth ? <Navigate to="/home" /> : <LoginForm setAuth={setAuth} />
            }
          />
          <Route path="/register" element={<RegisterForm />} />
          <Route
            path="/home"
            element={isAuth ? <HomePage /> : <Navigate to="/login" />}
          />
          <Route
            path="/logout"
            element={
              isAuth ? (
                <LogoutButton setAuth={setAuth} />
              ) : (
                <Navigate to="/login" />
              )
            }
          />
          <Route
            path="/finditem"
            element={isAuth ? <FindItem /> : <Navigate to="/login" />}
          />
          <Route
            path="/getItemsByOrderID"
            element={isAuth ? <OrderItemsPage /> : <Navigate to="/login" />}
          />
          <Route
            path="/acceptdonation"
            element={isAuth && sessionStorage.getItem("roleID") === "1"? <AcceptDonation /> : <Navigate to="/login" />}
          />
          <Route
            path="/startorder"
            element={isAuth && sessionStorage.getItem("roleID") === "1"? <StartOrder /> : <Navigate to="/login" />}
          />
          <Route
            path="/additemtoorder"
            element={
              isAuth ? (
                <AddItemToOrder />
              ) : (
                <Navigate to="/login" />
              )
            }
          />
          {/* 默认路由 */}
          <Route
            path="*"
            element={<Navigate to={isAuth ? "/home" : "/login"} />}
          />
        </Routes>
      </div>
    </Router>
  );
};

export default App;
