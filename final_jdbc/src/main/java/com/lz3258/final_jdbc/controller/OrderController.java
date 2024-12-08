package com.lz3258.final_jdbc.controller;

import com.lz3258.final_jdbc.dto.AddItemRequest;
import com.lz3258.final_jdbc.dto.ItemPiecesDTO;
import com.lz3258.final_jdbc.dto.ItemWithLocation;
import com.lz3258.final_jdbc.dto.OrderDTO;
import com.lz3258.final_jdbc.service.OrderService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @GetMapping("/all")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @Resource
    private OrderService orderService;
    //
    @PostMapping("/{orderId}/addItem")
    public ResponseEntity<String> addItemToOrder(@PathVariable Integer orderId, @RequestBody AddItemRequest addItemRequest) {
//        Integer orderId = Integer.parseInt(httpSession.getAttribute("OrderId").toString());

        boolean success = orderService.addItemToOrder(orderId, addItemRequest.getItemId());
        if (success) {
            return ResponseEntity.ok("Item added to order successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add item to order.");
        }
    }

    @PostMapping("/start")
    public ResponseEntity<String> startOrder(@RequestParam String clientUsername, HttpSession session) {
        String userName = (String) session.getAttribute("user");
        String roleID = (String) session.getAttribute("roleID");
        if (!"1".equalsIgnoreCase(roleID)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Permission denied: Not a staff member");
        }

        if (!orderService.isValidClient(clientUsername)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid client username");
        }

        //创建order
        Integer orderId = orderService.createOrder(userName, clientUsername);
        if (orderId != null) {
            //将orderID放入session
            session.setAttribute("currentOrderId", orderId);
            return ResponseEntity.ok("Order started successfully. Order ID: " + orderId);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to start order");
        }
    }

    @GetMapping("/{orderId}/items")
    public ResponseEntity<List<ItemPiecesDTO>> findOrderItems(@PathVariable Integer orderId) {
        List<ItemPiecesDTO> items = orderService.getItemsByOrderId(orderId);
        if (items.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        }
        return ResponseEntity.ok(items);
    }

//    @PostMapping("/{orderId}/add-item")
//    public ResponseEntity<String> addItemToOrder(@PathVariable int orderId, @RequestParam int itemId, HttpSession session) {
//        // 验证当前用户是否为工作人员
//        String currentUser = (String) session.getAttribute("user");
//        if (!orderService.isUserStaff(currentUser)) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Permission denied: Not a staff member");
//        }
//
//        // 添加物品到订单
//        boolean success = orderService.addItemToOrder(orderId, itemId);
//        if (success) {
//            return ResponseEntity.ok("Item added to order successfully");
//        }
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add item to order");
//    }


}
