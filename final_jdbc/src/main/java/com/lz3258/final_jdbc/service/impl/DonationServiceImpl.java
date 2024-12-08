package com.lz3258.final_jdbc.service.impl;

import com.lz3258.final_jdbc.dao.DonationDao;
import com.lz3258.final_jdbc.dto.DonationRequest;
import com.lz3258.final_jdbc.service.DonationService;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DonationServiceImpl implements DonationService {

    @Resource
    private DonationDao donationDao;

    @Override
    public boolean isValidDonor(String donorID) {
        return donationDao.isValidDonor(donorID);
    }

    @Override
    public boolean saveDonation(String donorId, DonationRequest donationRequest) {

        return donationDao.saveDonation(donorId, donationRequest);
    }
}
