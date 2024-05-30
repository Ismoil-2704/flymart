package com.example.flymart.service;

import com.example.flymart.repository.TransactionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticService {
    private final TransactionRepo transactionRepo;
    public Object getProductTransactionCounts() {
        return transactionRepo.findProductTransactionCounts();
    }
}
