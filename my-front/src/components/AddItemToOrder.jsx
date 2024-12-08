import React, { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import axios from "axios";
import "../css/AddItemToOrder.css";

const AddItemToOrder = () => {
  const location = useLocation();
  const { orderId } = location.state || {}; // 从 location.state 中获取 orderId

  const [categories, setCategories] = useState([]); // 所有类别
  const [mainCategory, setMainCategory] = useState(""); // 当前选择的主类别
  const [subCategory, setSubCategory] = useState(""); // 当前选择的子类别
  const [items, setItems] = useState([]); // 当前显示的item
  const [orders, setOrders] = useState([]); // 所有订单列表
  const [currentPage, setCurrentPage] = useState(1); // 当前页码
  const [itemsPerPage] = useState(10); // 每页显示条目数
  const [message, setMessage] = useState(""); // 操作结果信息
  const [selectedOrderId, setSelectedOrderId] = useState(orderId || ""); // 当前选择的订单ID

  // 获取所有类别
  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const response = await axios.get("http://localhost:8080/api/categories/getCategories", {
          withCredentials: true, //确保请求携带Cookie
        });
        setCategories(response.data);
      } catch (err) {
        console.error("Failed to fetch categories:", err);
      }
    };
    fetchCategories();
  }, []);

  // 获取所有订单
  useEffect(() => {
    const fetchOrders = async () => {
      try {
        const response = await axios.get("http://localhost:8080/api/orders/all", {
          withCredentials: true, //确保请求携带Cookie
        });
        setOrders(response.data); // 假设返回的订单数据包含一个orderId字段
      } catch (err) {
        console.error("Failed to fetch orders:", err);
      }
    };
    fetchOrders();
  }, []);

  // 获取过滤后的items
  const fetchItems = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/items/available", {
        params: { mainCategory, subCategory },
        withCredentials: true, //确保请求携带Cookie
      });
      setItems(response.data);
      setCurrentPage(1); // 每次获取新数据时重置到第一页
    } catch (err) {
      console.error("Failed to fetch items:", err);
    }
  };

  // 初始化时获取所有items
  useEffect(() => {
    fetchItems();
  }, [mainCategory, subCategory]);

  // 添加item到order
  const handleAddItem = async (itemId) => {
    if (!selectedOrderId || isNaN(selectedOrderId)) {
      setMessage("Please select a valid order.");
      return;
    }

    try {
      await axios.post(`http://localhost:8080/api/orders/${selectedOrderId}/addItem`, {
        itemId,
        withCredentials: true, //确保请求携带Cookie
      });
      setMessage("Item added successfully.");
      fetchItems(); // 刷新item列表
    } catch (err) {
      setMessage("Failed to add item to order.");
      console.error("Error adding item:", err);
    }
  };

  // 分页逻辑
  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentItems = items.slice(indexOfFirstItem, indexOfLastItem);

  const paginate = (pageNumber) => setCurrentPage(pageNumber);

  return (
    <div className="add-item-to-order-container">
      <h2>Add Item to Order</h2>

      {/* 选择订单的下拉框 */}
      <div className="order-selection">
        <label>Select Order:</label>
        <select
          value={selectedOrderId}
          onChange={(e) => setSelectedOrderId(e.target.value)}
        >
          <option value="">-- Select Order --</option>
          {orders.map((order) => (
            <option key={order.orderId} value={order.orderId}>
              Order ID: {order.orderId}
            </option>
          ))}
        </select>
      </div>

      {/* 类别筛选 */}
      <div className="filter-section">
        <label>Main Category:</label>
        <select value={mainCategory} onChange={(e) => setMainCategory(e.target.value)}>
          <option value="">All</option>
          {categories
            .filter((category, index, self) => self.findIndex(c => c.mainCategory === category.mainCategory) === index) // 去重主类别
            .map((category) => (
              <option key={category.mainCategory} value={category.mainCategory}>
                {category.mainCategory}
              </option>
            ))}
        </select>

        <label>Sub Category:</label>
        <select value={subCategory} onChange={(e) => setSubCategory(e.target.value)}>
          <option value="">All</option>
          {categories
            .filter((category) => category.mainCategory === mainCategory) // 根据主类别筛选子类别
            .map((category) => (
              <option key={category.subCategory} value={category.subCategory}>
                {category.subCategory}
              </option>
            ))}
        </select>
      </div>

      {/* 显示item列表 */}
      <div className="items-section">
        <table>
          <thead>
            <tr>
              <th>Item ID</th>
              <th>Description</th>
              <th>Photo</th>
              <th>Color</th>
              <th>Material</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {currentItems.map((item) => (
              <tr key={item.itemId}>
                <td>{item.itemId}</td>
                <td>{item.description}</td>
                <td>
                  {item.photo ? <img src={item.photo} alt={item.description} /> : "No Photo"}
                </td>
                <td>{item.color}</td>
                <td>{item.material}</td>
                <td>
                  <button onClick={() => handleAddItem(item.itemId)}>Add</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        {/* 分页 */}
        {items.length > itemsPerPage && (
          <div className="pagination">
            {Array.from({ length: Math.ceil(items.length / itemsPerPage) }, (_, index) => (
              <button
                key={index}
                onClick={() => paginate(index + 1)}
                className={currentPage === index + 1 ? "active" : ""}
              >
                {index + 1}
              </button>
            ))}
          </div>
        )}
      </div>

      {/* 操作结果信息 */}
      {message && <p className="message">{message}</p>}
    </div>
  );
};

export default AddItemToOrder;
