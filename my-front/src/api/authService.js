import axios from "axios";

const API_BASE_URL = "http://localhost:8080/api/auth"; // Java 后端的基础路径

export const login = async (userName, password) => {
  return axios.post(`${API_BASE_URL}/login`, null, {
    params: { userName, password},
    withCredentials: true, //确保请求携带Cookie
  });
};

export const register = async (person, password) => {
  return axios.post(`${API_BASE_URL}/register`, { ...person }, {
    params: { password },
    withCredentials: true, //确保请求携带Cookie
  });
};

export const logout = async () => {
  return axios.post(`${API_BASE_URL}/logout`,{
    withCredentials: true, //确保请求携带Cookie
  });
};

