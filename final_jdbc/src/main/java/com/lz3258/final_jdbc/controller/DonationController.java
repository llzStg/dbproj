package com.lz3258.final_jdbc.controller;

import com.lz3258.final_jdbc.dto.DonationRequest;
import com.lz3258.final_jdbc.service.DonationService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/donation")
@AllArgsConstructor
public class DonationController {
    private DonationService donationService;

    @PostMapping("/acceptDonation")
    public ResponseEntity<String> acceptDonation(@RequestParam String donorId, @RequestBody DonationRequest donationRequest, HttpSession session) {
        // 验证当前用户是否为员工
        String roleID = (String) session.getAttribute("roleID"); // "1"是staff
        System.out.println(roleID);
        if (!"1".equalsIgnoreCase(roleID)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Permission denied: Not a staff member");
        }

        // 验证捐赠者是否已注册
        if (!donationService.isValidDonor(donorId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Donor is not registered");
        }

        // 保存捐赠数据
        boolean success = donationService.saveDonation(donorId, donationRequest);
        if (success) {
            return ResponseEntity.ok("Donation accepted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to accept donation");
        }
    }
}

