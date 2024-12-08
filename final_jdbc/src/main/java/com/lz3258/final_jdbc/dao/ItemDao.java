package com.lz3258.final_jdbc.dao;

import com.lz3258.final_jdbc.dto.ItemDTO;
import com.lz3258.final_jdbc.dto.PieceLocationDTO;
import com.lz3258.final_jdbc.entity.Piece;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class ItemDao {
    private JdbcTemplate jdbcTemplate;

    public List<ItemDTO> findAvailableItems(String mainCategory, String subCategory) {
        StringBuilder sql = new StringBuilder("SELECT * FROM Item i WHERE NOT EXISTS (SELECT 1 FROM ItemIn ii WHERE i.ItemID = ii.ItemID AND ii.found = true)");
        List<Object> params = new ArrayList<>(); // 动态参数列表
        if (mainCategory != null && !mainCategory.isEmpty()) {
            sql.append(" AND i.mainCategory = ?");
            params.add(mainCategory);
        }
        if (subCategory != null && !subCategory.isEmpty()) {
            sql.append(" AND i.subCategory = ?");
            params.add(subCategory);
        }

        return jdbcTemplate.query(sql.toString(), params.toArray(), (rs, rowNum) -> new ItemDTO(
                rs.getInt("ItemID"),
                rs.getString("iDescription"),
                rs.getString("photo"),
                rs.getString("color"),
                rs.getBoolean("isNew"),
                rs.getBoolean("hasPieces"),
                rs.getString("material"),
                rs.getString("mainCategory"),
                rs.getString("subCategory")
        ));
    }

    /**
     * 查询置顶itemID 的所有piece以及其location
     * @param itemID
     * @return
     */
    public List<PieceLocationDTO> findLocationByItemId(Integer itemID) {
        String sql = """
                 SELECT i.itemID, i.iDescription, i.photo, i.color, i.isNew, i.hasPieces, i.material, i.mainCategory, i.subCategory,
                 p.pieceNum, p.pDescription, p.length, p.width, p.height,l.roomNum, l.shelfNum, l.shelf, l.shelfDescription, p.pNotes
                 FROM Item i
                 JOIN Piece p ON i.itemID = p.itemID
                 JOIN Location l ON p.roomNum = l.roomNum AND p.shelfNum = l.shelfNum
                 WHERE i.ItemID = ?
                """;

        return jdbcTemplate.query(sql, new Object[]{itemID}, (rs, rowNum) -> {
            PieceLocationDTO pieceLocation = new PieceLocationDTO();
            pieceLocation.setItemID(rs.getInt("itemID"));
            pieceLocation.setIDescription(rs.getString("iDescription"));
            pieceLocation.setPhoto(rs.getString("photo"));
            pieceLocation.setColor(rs.getString("color"));
            pieceLocation.setNew(rs.getBoolean("isNew"));
            pieceLocation.setHasPieces(rs.getBoolean("hasPieces"));
            pieceLocation.setMaterial(rs.getString("material"));
            pieceLocation.setMainCategory(rs.getString("mainCategory"));
            pieceLocation.setSubCategory(rs.getString("subCategory"));

            pieceLocation.setPieceNum(rs.getInt("pieceNum"));
            pieceLocation.setRoomNum(rs.getInt("roomNum"));
            pieceLocation.setShelfNum(rs.getInt("shelfNum"));
            pieceLocation.setShelf(rs.getString("shelf"));
            pieceLocation.setShelfDescription(rs.getString("shelfDescription"));
            return pieceLocation;
        });
    }
}
