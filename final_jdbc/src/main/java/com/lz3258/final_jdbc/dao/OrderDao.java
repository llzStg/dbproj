package com.lz3258.final_jdbc.dao;

import com.lz3258.final_jdbc.dto.ItemPiecesDTO;
import com.lz3258.final_jdbc.dto.ItemWithLocation;
import com.lz3258.final_jdbc.entity.Location;
import com.lz3258.final_jdbc.entity.Ordered;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
@AllArgsConstructor
public class OrderDao {
    private JdbcTemplate jdbcTemplate;

    public List<Ordered> getAllOrders() {
        String sql = "SELECT * FROM Ordered";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Ordered(
                rs.getInt("orderID"),
                rs.getDate("orderDate"),
                rs.getString("orderNotes"),
                rs.getString("supervisor"),
                rs.getString("client")
        ));
    }

    public boolean addItemToOrder(Integer orderId, Integer itemId) {
        String sql = "INSERT INTO ItemIn (ItemID, orderID, found) VALUES (?, ?, true)";

        try {
            jdbcTemplate.update(sql, itemId, orderId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean isUserInRole(String userName, String roleID) {
        String sql = """
                SELECT COUNT(*) 
                FROM Act a
                JOIN Role r ON a.roleID = r.roleID
                WHERE a.userName = ? AND r.roleID = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userName, roleID);
        return count != null && count > 0;
    }



    public List<ItemPiecesDTO> findItemsByOrderId(Integer orderID) {
        String sql = """
                SELECT iin.ItemID, p.pieceNum, p.roomNum, p.shelfNum, l.shelf, l.shelfDescription
                FROM ItemIn iin
                JOIN Piece p ON iin.ItemID = p.ItemID
                JOIN Location l ON p.roomNum = l.roomNum AND p.shelfNum = l.shelfNum
                WHERE iin.orderID = ?
                """;
        return jdbcTemplate.query(sql, new Object[]{orderID}, (rs, rowNum) -> {
            ItemPiecesDTO item = new ItemPiecesDTO();
            item.setItemID(rs.getInt("ItemID"));
            item.setPieceNum(rs.getInt("pieceNum"));
            item.setRoomNum(rs.getInt("roomNum"));
            item.setShelfNum(rs.getInt("shelfNum"));
            item.setShelf(rs.getString("shelf"));
            item.setShelfDescription(rs.getString("shelfDescription"));
            return item;
        });

    }

    public List<ItemWithLocation> findItemsWithLocations(Integer orderId) {
        String sql = """
            SELECT i.ItemID, i.iDescription, i.color, i.isNew, i.hasPieces, i.material, 
                   p.roomNum, p.shelfNum, l.shelf, l.shelfDescription
            FROM Item i
            JOIN ItemIn ii ON i.ItemID = ii.ItemID
            JOIN Piece p ON i.ItemID = p.ItemID
            JOIN Location l ON p.roomNum = l.roomNum AND p.shelfNum = l.shelfNum
            WHERE ii.orderID = ?
        """;

        Map<Integer, ItemWithLocation> itemMap = new HashMap<>();
        jdbcTemplate.query(sql, new Object[]{orderId}, (rs -> {
            int itemId = rs.getInt("ItemID");

            itemMap.putIfAbsent(itemId, new ItemWithLocation());

            ItemWithLocation item = itemMap.get(itemId);
            if (item.getItemId() == 0) {
                item.setItemId(itemId);
                item.setDescription(rs.getString("iDescription"));
                item.setColor(rs.getString("color"));
                item.setNew(rs.getBoolean("isNew"));
                item.setHasPieces(rs.getBoolean("hasPieces"));
                item.setMaterial(rs.getString("material"));
                item.setLocations(new ArrayList<>());
            }

            // add locations
            Location location = new Location();
            location.setRoomNum(rs.getInt("roomNum"));
            location.setShelfNum(rs.getInt("shelfNum"));
            location.setShelf(rs.getString("shelf"));
            location.setShelfDescription(rs.getString("shelfDescription"));

        }));
        return new ArrayList<>(itemMap.values());
    }


    /**
     * validate if a user exists
     * @param clientUserName
     * @return
     */
    public boolean isClientValid(String clientUserName) {
        String sql = "SELECT COUNT(*) FROM Person WHERE userName = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{clientUserName}, Integer.class);
        return count != null && count > 0;
    }

    public boolean isUserRole(String username, String roleName) {
        String sql = """
                SELECT COUNT(*)\s
                FROM Act a\s
                JOIN Role r ON a.roleID = r.roleID\s
                WHERE a.userName = ? AND r.rDescription = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{username, roleName}, Integer.class);
        return count != null && count > 0;
    }

    public Integer  createOrder(String supervisor, String client) {
        String sql = "INSERT INTO Ordered (orderDate, orderNotes, supervisor, client) VALUES (CURDATE(), NULL, ?, ?)";

        try {
            jdbcTemplate.update(sql, supervisor, client);

            // 获取自动生成的 orderID
            String getOrderIdSql = "SELECT LAST_INSERT_ID()";

            return jdbcTemplate.queryForObject(getOrderIdSql, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }



    }

    public boolean addItemToOrder(int orderId, int itemId) {
        String insertSql = "INSERT INTO ItemIn (ItemID, orderID, found) VALUES (?, ?, FALSE)";
        int rows = jdbcTemplate.update(insertSql, itemId, orderId);
        String updateSql = "UPDATE Item SET isNew = FALSE WHERE ItemID = ?";
        jdbcTemplate.update(updateSql, itemId);
        return rows > 0;
    }
}
