package com.lz3258.final_jdbc.service.impl;

import com.lz3258.final_jdbc.dao.OrderDao;
import com.lz3258.final_jdbc.dto.ItemPiecesDTO;
import com.lz3258.final_jdbc.dto.ItemWithLocation;
import com.lz3258.final_jdbc.dto.OrderDTO;
import com.lz3258.final_jdbc.entity.Ordered;
import com.lz3258.final_jdbc.service.OrderService;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Ordered> orders = orderDao.getAllOrders();
        return orders.stream().map(order -> new OrderDTO(
                order.getOrderID(),
                order.getOrderDate(),
                order.getOrderNotes(),
                order.getSupervisor(),
                order.getClient()
        )).collect(Collectors.toList());
    }

    @Override
    public boolean addItemToOrder(Integer orderId, Integer itemId) {
        return orderDao.addItemToOrder(orderId, itemId);
    }

    @Override
    public boolean isStaff(String username) {
        return orderDao.isUserInRole(username, "1"); // "1" 是 Staff 的 roleID
    }

    @Override
    public boolean isValidClient(String clientUserName) {
        return orderDao.isUserInRole(clientUserName, "3");
    }

    @Override
    public List<ItemWithLocation> findItemsWithLocations(int orderId) {
        return orderDao.findItemsWithLocations(orderId);
    }

    @Override
    public boolean isUserStaff(String username) {
        return orderDao.isUserRole(username, "staff");
    }

    @Override
    public boolean isClientValid(String clientUsername) {
        return orderDao.isClientValid(clientUsername);
    }

    @Override
    public int createOrder(String supervisor, String client) {
        return orderDao.createOrder(supervisor, client);
    }

    @Override
    public boolean addItemToOrder(int orderId, int itemId) {
        return orderDao.addItemToOrder(orderId, itemId);
    }

    @Override
    public List<ItemPiecesDTO> getItemsByOrderId(Integer orderID) {
        return orderDao.findItemsByOrderId(orderID);
    }
}
