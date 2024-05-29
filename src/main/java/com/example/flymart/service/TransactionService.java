package com.example.flymart.service;

import com.example.flymart.entity.*;
import com.example.flymart.entity.enums.ServerCode;
import com.example.flymart.exeptions.DataExistsExceptions;
import com.example.flymart.exeptions.DataNotFoundExceptions;
import com.example.flymart.model.ReqTransaction;
import com.example.flymart.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepo transactionRepo;
    private final RequestRepo requestRepo;
    private final OfferRepo offerRepo;
    private final UserRepo userRepo;
    private final PlaceRepo placeRepo;
    public void create(ReqTransaction reqTransaction) throws DataNotFoundExceptions, DataExistsExceptions {
        Request request = requestRepo.findByCode(reqTransaction.getRequestCode()).orElseThrow(() -> new DataNotFoundExceptions(ServerCode.OFFER_NOT_FOUND.message));
        Offer offer = offerRepo.findByCode(reqTransaction.getOfferCode()).orElseThrow(() -> new DataNotFoundExceptions(ServerCode.REQUEST_NOT_FOUND.message));
        User carrier = userRepo.findByUserName(reqTransaction.getCarrierName()).orElseThrow(() -> new DataNotFoundExceptions(ServerCode.USER_NOT_FOUND.message));
        User user = userRepo.findById(reqTransaction.getUserId()).orElseThrow(() -> new DataNotFoundExceptions(ServerCode.USER_NOT_FOUND.message));
        transactionRepo.findByRequestCodeAndOfferCode(reqTransaction.getRequestCode(), reqTransaction.getOfferCode()).orElseThrow(() -> new DataExistsExceptions(ServerCode.TRANSACTION_EXIST.message));
        Transactions transactions = new Transactions();
        transactions.setCode(reqTransaction.getTransactionCode());
        transactions.setOfferCode(offer.getOffer());
        transactions.setRequestCode(request.getCode());
        transactions.setCarrierId(carrier.getId());
        transactions.setUserId(user.getId());
        transactions.setPlaces(placeRepo.findAllById(reqTransaction.getPlaces()));
        transactionRepo.save(transactions);
    }

    public Object getUserTransactions(Long userId) throws DataNotFoundExceptions {
        Optional<Transactions> transactionsOptional = transactionRepo.finByUserId(userId);
        if (transactionsOptional.isEmpty()){
            throw new DataNotFoundExceptions(ServerCode.TRANSACTION_NOT_FOUND.message);
        }
        return transactionsOptional.get();
    }

    public Boolean evaluateTransaction(String transactionCode,int score) throws DataNotFoundExceptions {
        Optional<Transactions> optionalTransactions = transactionRepo.findByCode(transactionCode);
        if (optionalTransactions.isEmpty()){
            throw new DataNotFoundExceptions(ServerCode.TRANSACTION_NOT_FOUND.message);
        }
        Transactions transactions = optionalTransactions.get();
        transactions.setScore(score);
        transactionRepo.save(transactions);
        return score > 1;
    }
}
