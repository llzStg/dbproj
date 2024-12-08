package com.lz3258.final_jdbc.service;

import com.lz3258.final_jdbc.dto.ItemPiecesDTO;
import com.lz3258.final_jdbc.dto.ItemWithLocation;
import com.lz3258.final_jdbc.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    public List<OrderDTO> getAllOrders();
    public boolean addItemToOrder(Integer orderId, Integer itemId);

    public boolean isStaff(String username);
    public boolean isValidClient(String clientUserName);
    public List<ItemWithLocation> findItemsWithLocations(int orderId);

    public boolean isUserStaff(String username);

    public boolean isClientValid(String clientUsername);

    public int createOrder(String supervisor, String client);

    public boolean addItemToOrder(int orderId, int itemId);

    public List<ItemPiecesDTO> getItemsByOrderId(Integer orderID);
}
