package com.jpmc.midascore.service;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Service
public class BalanceService {

    @Autowired
    private UserRepository userRepository;

    public Balance fetchUserBalance(long userId) {
        Optional<UserRecord> user = userRepository.findById(userId);
        Balance userBalance;
        if (user.isPresent()) {
            userBalance = new Balance(user.get().getBalance());
        } else {
            userBalance = new Balance(0);
        }
        return userBalance;
    }
}
