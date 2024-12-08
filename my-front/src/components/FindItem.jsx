import React, { useState } from "react";
import axios from "axios";
import "../css/FindItem.css"; // 添加样式文件

const FindItem = () => {
  const [itemID, setItemID] = useState("");
  const [results, setResults] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage] = useState(10); // 每页展示的条目数
  const [error, setError] = useState("");

  const handleSearch = async () => {
    setError(""); // 清空错误
    try {
      const response = await axios.get(`http://localhost:8080/api/items/${itemID}/pieces`);
      setResults(response.data);
      setCurrentPage(1); // 每次搜索重置到第一页
    } catch (err) {
      setResults([]);
      setError("No results found for the given itemID.");
    }
  };

  // 分页逻辑
  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentResults = results.slice(indexOfFirstItem, indexOfLastItem);

  const paginate = (pageNumber) => setCurrentPage(pageNumber);

  return (
    <div className="find-item-container">
      <h2>Find Item</h2>
      <div className="search-bar">
        <input
          type="text"
          placeholder="Enter Item ID"
          value={itemID}
          onChange={(e) => setItemID(e.target.value)}
        />
        <button onClick={handleSearch}>Search</button>
      </div>
      {error && <p className="error">{error}</p>}
      <div className="results">
        {currentResults.length > 0 && (
          <table>
            <thead>
              <tr>
                <th>itemID</th>
                <th>iDescription</th>
                <th>photo</th>
                <th>color</th>
                <th>isNew</th>
                <th>hasPieces</th>
                <th>material</th>
                <th>mainCategory</th>
                <th>subCategory</th>

                <th>Piece Num</th>
                <th>Room Num</th>
                <th>Shelf Num</th>
                <th>Shelf</th>
                <th>Shelf Description</th>
              </tr>
            </thead>
            <tbody>
              {currentResults.map((piece, index) => (
                <tr key={index}>
                  <td>{piece.itemID}</td>
                  <td>{piece.idescription}</td>
                  <td>{piece.photo}</td>
                  <td>{piece.color}</td>
                  <td>{piece.new ? "true" : "false"}</td>
                  <td>{piece.hasPieces ? "true" : "false"}</td>
                  <td>{piece.material}</td>
                  <td>{piece.mainCategory}</td>
                  <td>{piece.subCategory}</td>

                  <td>{piece.pieceNum}</td>
                  <td>{piece.roomNum}</td>
                  <td>{piece.shelfNum}</td>
                  <td>{piece.shelf}</td>
                  <td>{piece.shelfDescription}</td>
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

export default FindItem;
