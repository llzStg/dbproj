import React, { useState, useEffect } from "react";
import axios from "axios";

const Register = () => {
  const [roles, setRoles] = useState([]); // 角色列表
  const [formData, setFormData] = useState({
    userName: "",
    password: "",
    fname: "",
    lname: "",
    email: "",
    roleID: "", // 选择的角色 ID
    phones: [""], // 电话号码列表
  });

  // 加载角色列表
  useEffect(() => {
    axios.get("http://localhost:8080/api/roles").then((response) => {
      setRoles(response.data);
    });
  }, []);

  // 更新表单字段
  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handlePhoneChange = (index, value) => {
    const updatedPhones = [...formData.phones];
    updatedPhones[index] = value;
    setFormData({ ...formData, phones: updatedPhones });
  };

  const addPhoneField = () => {
    setFormData({ ...formData, phones: [...formData.phones, ""] });
  };

  // 提交表单
  const handleSubmit = (e) => {
    e.preventDefault();
    axios
      .post("http://localhost:8080/api/auth/register", formData)
      .then((response) => {
        alert(response.data);
      })
      .catch((error) => {
        alert(error.response.data);
      });
  };

  return (
    <div>
      <h2>Register</h2>
      <form onSubmit={handleSubmit}>
        <input type="text" name="userName" placeholder="Username" onChange={handleChange} required />
        <input type="password" name="password" placeholder="Password" onChange={handleChange} required />
        <input type="text" name="fname" placeholder="First Name" onChange={handleChange} required />
        <input type="text" name="lname" placeholder="Last Name" onChange={handleChange} required />
        <input type="email" name="email" placeholder="Email" onChange={handleChange} required />

        {/* 角色下拉框 */}
        <select name="roleID" onChange={handleChange} required>
          <option value="">Select Role</option>
          {roles.map((role) => (
            <option key={role.roleID} value={role.roleID}>
              {role.rdescription}
            </option>
          ))}
        </select>

        {/* 电话号码输入 */}
        <h4>Phone Numbers</h4>
        {formData.phones.map((phone, index) => (
          <div key={index}>
            <input
              type="text"
              value={phone}
              onChange={(e) => handlePhoneChange(index, e.target.value)}
              placeholder={`Phone ${index + 1}`}
              required
            />
          </div>
        ))}
        <button type="button" onClick={addPhoneField}>
          Add Phone
        </button>

        <button type="submit">Register</button>
      </form>
    </div>
  );
};

export default Register;
