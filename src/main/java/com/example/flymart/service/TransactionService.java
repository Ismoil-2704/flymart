package com.example.flymart.service;

import com.example.flymart.entity.Offer;
import com.example.flymart.entity.Request;
import com.example.flymart.entity.Transactions;
import com.example.flymart.entity.User;
import com.example.flymart.entity.enums.ServerCode;
import com.example.flymart.exeptions.DataExistsExceptions;
import com.example.flymart.exeptions.DataNotFoundExceptions;
import com.example.flymart.model.ReqTransaction;
import com.example.flymart.repository.OfferRepo;
import com.example.flymart.repository.RequestRepo;
import com.example.flymart.repository.TransactionRepo;
import com.example.flymart.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepo transactionRepo;
    private final RequestRepo requestRepo;
    private final OfferRepo offerRepo;
    private final UserRepo userRepo;

    public void create(ReqTransaction reqTransaction) throws DataNotFoundExceptions, DataExistsExceptions {
        Request request = requestRepo.findByCode(reqTransaction.getRequestCode()).orElseThrow(() -> new DataNotFoundExceptions(ServerCode.OFFER_NOT_FOUND.message));
        Offer offer = offerRepo.findByCode(reqTransaction.getOfferCode()).orElseThrow(() -> new DataNotFoundExceptions(ServerCode.REQUEST_NOT_FOUND.message));
        User user = userRepo.findByUserName(reqTransaction.getCarrierName()).orElseThrow(() -> new DataNotFoundExceptions(ServerCode.USER_NOT_FOUND.message));
        transactionRepo.findByRequestCodeAndOfferCode(reqTransaction.getRequestCode(), reqTransaction.getOfferCode()).orElseThrow(() -> new DataExistsExceptions(ServerCode.TRANSACTION_EXIST.message));
        Transactions transactions = new Transactions();
        transactions.setCode(reqTransaction.getTransactionCode());
        transactions.setOfferCode(offer.getCode());
        transactions.setRequestCode(request.getCode());
        transactions.setCarrierId(user.getId());
        transactionRepo.save(transactions);
    }
}
