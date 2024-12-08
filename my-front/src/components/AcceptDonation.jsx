// AcceptDonation.js
import React, { useState } from "react";
import axios from "axios";
// import "./AcceptDonation.css";

const AcceptDonation = () => {
  const [donorId, setDonorId] = useState(""); // 捐赠者ID
  const [donationRequest, setDonationRequest] = useState({
    description: "",
    photo: "",
    color: "",
    isNew: false,
    hasPieces: false,
    material: "",
    mainCategory: "",
    subCategory: "",
    categoryNotes: "",
    donateDate: "",
    pieces: [
      {
        pieceNum: "",
        description: "",
        length: "",
        width: "",
        height: "",
        roomNum: "",
        shelfNum: "",
        notes: "",
      },
    ], // 默认一条分件信息
  });
  const [message, setMessage] = useState("");

  // 更新表单字段
  const handleChange = (e) => {
    const { name, value } = e.target;
    setDonationRequest({ ...donationRequest, [name]: value });
  };

  // 更新分件信息
  const handlePieceChange = (index, name, value) => {
    const updatedPieces = [...donationRequest.pieces];
    updatedPieces[index][name] = value;
    setDonationRequest({ ...donationRequest, pieces: updatedPieces });
  };

  // 添加分件信息
  const addPiece = () => {
    setDonationRequest({
      ...donationRequest,
      pieces: [
        ...donationRequest.pieces,
        {
          pieceNum: "",
          description: "",
          length: "",
          width: "",
          height: "",
          roomNum: "",
          shelfNum: "",
          notes: "",
        },
      ],
    });
  };

  // 提交表单
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        "http://localhost:8080/api/donation/acceptDonation",
        donationRequest,
        { params: { donorId },
        withCredentials: true, //确保请求携带Cookie
    }
      );
      console.log(response);
      setMessage(response.data); // 显示成功信息
    } catch (err) {
      // 检查错误响应是否包含 data
      if (err.response && err.response.data) {
        const { status, error, path } = err.response.data;
        setMessage(`Error ${status}: ${error} (Path: ${path})`);
      } else {
        setMessage("An unexpected error occurred");
      }
    }
  };

  return (
    <div className="accept-donation-container">
      <h2>Accept Donation</h2>
      <form onSubmit={handleSubmit}>
        {/* 捐赠者ID */}
        <label>Donor ID:</label>
        <input
          type="text"
          value={donorId}
          onChange={(e) => setDonorId(e.target.value)}
          required
        />

        {/* 捐赠信息 */}
        <label>Description:</label>
        <input
          type="text"
          name="description"
          value={donationRequest.description}
          onChange={handleChange}
          required
        />

        <label>Photo URL:</label>
        <input
          type="text"
          name="photo"
          value={donationRequest.photo}
          onChange={handleChange}
        />

        <label>Color:</label>
        <input
          type="text"
          name="color"
          value={donationRequest.color}
          onChange={handleChange}
        />

        <label>Is New:</label>
        <input
          type="checkbox"
          name="isNew"
          checked={donationRequest.isNew}
          onChange={(e) =>
            setDonationRequest({ ...donationRequest, isNew: e.target.checked })
          }
        />

        <label>Has Pieces:</label>
        <input
          type="checkbox"
          name="hasPieces"
          checked={donationRequest.hasPieces}
          onChange={(e) =>
            setDonationRequest({
              ...donationRequest,
              hasPieces: e.target.checked,
            })
          }
        />

        <label>Material:</label>
        <input
          type="text"
          name="material"
          value={donationRequest.material}
          onChange={handleChange}
        />

        <label>Main Category:</label>
        <input
          type="text"
          name="mainCategory"
          value={donationRequest.mainCategory}
          onChange={handleChange}
        />

        <label>Sub Category:</label>
        <input
          type="text"
          name="subCategory"
          value={donationRequest.subCategory}
          onChange={handleChange}
        />

        <label>Category Notes:</label>
        <textarea
          name="categoryNotes"
          value={donationRequest.categoryNotes}
          onChange={handleChange}
        />

        <label>Donate Date:</label>
        <input
          type="date"
          name="donateDate"
          value={donationRequest.donateDate}
          onChange={handleChange}
          required
        />

        {/* 分件信息 */}
        <h3>Pieces</h3>
        {donationRequest.pieces.map((piece, index) => (
          <div key={index} className="piece-input">
            <h4>Piece {index + 1}</h4>

            <label>Piece Num:</label>
            <input
              type="number"
              value={piece.pieceNum}
              onChange={(e) =>
                handlePieceChange(index, "pieceNum", e.target.value)
              }
            />

            <label>Description:</label>
            <input
              type="text"
              value={piece.description}
              onChange={(e) =>
                handlePieceChange(index, "description", e.target.value)
              }
            />

            <label>Length:</label>
            <input
              type="number"
              value={piece.length}
              onChange={(e) =>
                handlePieceChange(index, "length", e.target.value)
              }
            />

            <label>Width:</label>
            <input
              type="number"
              value={piece.width}
              onChange={(e) =>
                handlePieceChange(index, "width", e.target.value)
              }
            />

            <label>Height:</label>
            <input
              type="number"
              value={piece.height}
              onChange={(e) =>
                handlePieceChange(index, "height", e.target.value)
              }
            />

            <label>Room Num:</label>
            <input
              type="number"
              value={piece.roomNum}
              onChange={(e) =>
                handlePieceChange(index, "roomNum", e.target.value)
              }
            />

            <label>Shelf Num:</label>
            <input
              type="number"
              value={piece.shelfNum}
              onChange={(e) =>
                handlePieceChange(index, "shelfNum", e.target.value)
              }
            />

            <label>Notes:</label>
            <textarea
              value={piece.notes}
              onChange={(e) =>
                handlePieceChange(index, "notes", e.target.value)
              }
            />
          </div>
        ))}
        <button type="button" onClick={addPiece}>
          Add Piece
        </button>

        {/* 提交按钮 */}
        <button type="submit">Submit Donation</button>
      </form>

      {/* 消息显示 */}
      {message && <p className="message">{message}</p>}
    </div>
  );
};

export default AcceptDonation;
