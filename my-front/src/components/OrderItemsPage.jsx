import React, { useState } from "react";
import axios from "axios";
// import "./OrderItemsPage.css";

const OrderItemsPage = () => {
  const [orderId, setOrderId] = useState(""); // 用户输入的 Order ID
  const [results, setResults] = useState([]); // 搜索结果
  const [currentPage, setCurrentPage] = useState(1); // 当前页码
  const [itemsPerPage] = useState(10); // 每页展示的条目数
  const [error, setError] = useState(""); // 错误信息

  const handleSearch = async () => {
    setError(""); // 清空错误信息
    try {
      const response = await axios.get(`http://localhost:8080/api/orders/${orderId}/items`, {
        withCredentials: true, //确保请求携带Cookie
      });
      setResults(response.data);
      setCurrentPage(1); // 搜索后重置到第一页
    } catch (err) {
      setResults([]);
      setError("No results found for the given Order ID.");
    }
  };

  // 分页逻辑
  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentResults = results.slice(indexOfFirstItem, indexOfLastItem);

  const paginate = (pageNumber) => setCurrentPage(pageNumber);

  return (
    <div className="order-items-container">
      <h2>Order Items</h2>
      <div className="search-bar">
        <input
          type="text"
          placeholder="Enter Order ID"
          value={orderId}
          onChange={(e) => setOrderId(e.target.value)}
        />
        <button onClick={handleSearch}>Search</button>
      </div>
      {error && <p className="error">{error}</p>}
      <div className="results">
        {currentResults.length > 0 && (
          <table>
            <thead>
              <tr>
                <th>Item ID</th>
                <th>Piece Num</th>
                <th>Room Num</th>
                <th>Shelf Num</th>
                <th>Shelf</th>
                <th>Shelf Description</th>
              </tr>
            </thead>
            <tbody>
              {currentResults.map((item, index) => (
                <tr key={index}>
                  <td>{item.itemID}</td>
                  <td>{item.pieceNum}</td>
                  <td>{item.roomNum}</td>
                  <td>{item.shelfNum}</td>
                  <td>{item.shelf}</td>
                  <td>{item.shelfDescription}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
        {results.length === 0 && !error && <p>No results to display.</p>}
      </div>
      {results.length > itemsPerPage && (
        <div className="pagination">
          {Array.from(
            { length: Math.ceil(results.length / itemsPerPage) },
            (_, index) => (
              <button
                key={index}
                onClick={() => paginate(index + 1)}
                className={currentPage === index + 1 ? "active" : ""}
              >
                {index + 1}
              </button>
            )
          )}
        </div>
      )}
    </div>
  );
};

export default OrderItemsPage;
