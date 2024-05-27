package com.example.flymart.service;

import com.example.flymart.entity.*;
import com.example.flymart.entity.enums.ServerCode;
import com.example.flymart.exeptions.DataExistsExceptions;
import com.example.flymart.exeptions.DataNotFoundExceptions;
import com.example.flymart.model.ReqAddress;
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
    private final AddressRepo addressRepo;

    public void create(ReqTransaction reqTransaction) throws DataNotFoundExceptions, DataExistsExceptions {
        Request request = requestRepo.findByCode(reqTransaction.getRequestCode()).orElseThrow(() -> new DataNotFoundExceptions(ServerCode.OFFER_NOT_FOUND.message));
        Offer offer = offerRepo.findByCode(reqTransaction.getOfferCode()).orElseThrow(() -> new DataNotFoundExceptions(ServerCode.REQUEST_NOT_FOUND.message));
        User user = userRepo.findByUserName(reqTransaction.getCarrierName()).orElseThrow(() -> new DataNotFoundExceptions(ServerCode.USER_NOT_FOUND.message));
        transactionRepo.findByRequestCodeAndOfferCode(reqTransaction.getRequestCode(), reqTransaction.getOfferCode()).orElseThrow(() -> new DataExistsExceptions(ServerCode.TRANSACTION_EXIST.message));
        Optional<Address> fromAddress = addressRepo.findByNameAndLongitudeAndLatitude(reqTransaction.getFromAddress().getName(),
                reqTransaction.getFromAddress().getLongitude(),
                reqTransaction.getFromAddress().getLatitude());
        Optional<Address> toAddress = addressRepo.findByNameAndLongitudeAndLatitude(reqTransaction.getToAddress().getName(),
                reqTransaction.getToAddress().getLongitude(),
                reqTransaction.getToAddress().getLatitude());
        Address fromAdd = null;
        Address toAdd = null;
        if (fromAddress.isEmpty()){
            ReqAddress fromAddressCreating = reqTransaction.getFromAddress();
            Address address = new Address();
            address.setName(fromAddressCreating.getName());
            address.setLongitude(fromAddressCreating.getLongitude());
            address.setLatitude(fromAddressCreating.getLatitude());
            fromAdd = addressRepo.save(address);
        } else {
            fromAdd = fromAddress.get();
        }
        if (toAddress.isEmpty()){
            ReqAddress toAddressCreating = reqTransaction.getToAddress();
            Address address = new Address();
            address.setName(toAddressCreating.getName());
            address.setLongitude(toAddressCreating.getLongitude());
            address.setLatitude(toAddressCreating.getLatitude());
            toAdd = addressRepo.save(address);
        }else {
            toAdd = toAddress.get();
        }
        Transactions transactions = new Transactions();
        transactions.setCode(reqTransaction.getTransactionCode());
        transactions.setOfferCode(offer.getCode());
        transactions.setRequestCode(request.getCode());
        transactions.setCarrierId(user.getId());
        transactionRepo.save(transactions);
    }
}
