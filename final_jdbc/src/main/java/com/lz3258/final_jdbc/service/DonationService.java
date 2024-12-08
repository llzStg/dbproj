package com.lz3258.final_jdbc.service;

import com.lz3258.final_jdbc.dto.DonationRequest;

public interface DonationService {
    public boolean isValidDonor(String donorID);

    public boolean saveDonation(String donorId, DonationRequest donationRequest);


}
