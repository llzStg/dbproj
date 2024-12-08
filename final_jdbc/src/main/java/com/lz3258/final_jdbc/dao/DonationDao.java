package com.lz3258.final_jdbc.dao;

import com.lz3258.final_jdbc.dto.DonationRequest;
import com.lz3258.final_jdbc.dto.PieceRequest;
import com.lz3258.final_jdbc.entity.Item;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
@AllArgsConstructor
public class DonationDao {
    private JdbcTemplate jdbcTemplate;

    public boolean isValidDonor(String donorId) {
        String sql = """
                SELECT COUNT(*)\s
                FROM Person p
                JOIN Act a ON p.userName = a.userName
                JOIN Role r ON a.roleID = r.roleID
                WHERE p.userName = ? AND r.roleID = '4'    
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, donorId);
        return count != null && count > 0;
    }



    public boolean saveDonation(String donorId, DonationRequest donationRequest) {

        try {
            ensureCategoryExists(donationRequest.getMainCategory(), donationRequest.getSubCategory(), donationRequest.getCategoryNotes());

            // 插入 Item 表数据
            String itemSql = "INSERT INTO Item (iDescription, photo, color, isNew, hasPieces, material, mainCategory, subCategory) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(itemSql,
                    donationRequest.getDescription(),
                    donationRequest.getPhoto(),
                    donationRequest.getColor(),
                    donationRequest.isNew(),
                    donationRequest.isHasPieces(),
                    donationRequest.getMaterial(),
                    donationRequest.getMainCategory(),
                    donationRequest.getSubCategory()
            );

            // 获取自动生成的 ItemID
            String getItemIdSql = "SELECT LAST_INSERT_ID()";
            Integer itemId = jdbcTemplate.queryForObject(getItemIdSql, Integer.class);

            // 插入 Piece 表数据
            String pieceSql = "INSERT INTO Piece (ItemID, pieceNum, pDescription, length, width, height, roomNum, shelfNum, pNotes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            for (PieceRequest piece : donationRequest.getPieces()) {
                jdbcTemplate.update(pieceSql,
                        itemId,
                        piece.getPieceNum(),
                        piece.getDescription(),
                        piece.getLength(),
                        piece.getWidth(),
                        piece.getHeight(),
                        piece.getRoomNum(),
                        piece.getShelfNum(),
                        piece.getNotes()
                );
            }

            String donatedBySql = "INSERT INTO DonatedBy (ItemID, userName, donateDate) VALUES (?, ?, ?)";
            jdbcTemplate.update(donatedBySql, itemId, donorId, donationRequest.getDonateDate());

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 如果item的category不存在，则需要插入新的分类
     * @param mainCategory
     * @param subCategory
     * @param catNotes
     */
    private void ensureCategoryExists(String mainCategory, String subCategory, String catNotes) {
        String checkCategorySql = "SELECT COUNT(*) FROM Category WHERE mainCategory = ? AND subCategory = ?";
        Integer count = jdbcTemplate.queryForObject(checkCategorySql, Integer.class, mainCategory, subCategory);

        if (count == null || count == 0) {
            // 分类不存在，插入新的分类
            String insertCategorySql = "INSERT INTO Category (mainCategory, subCategory, catNotes) VALUES (?, ?, ?)";
            jdbcTemplate.update(insertCategorySql, mainCategory, subCategory, catNotes);
        }
    }


}
