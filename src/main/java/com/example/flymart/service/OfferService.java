package com.example.flymart.service;

import com.example.flymart.entity.Offer;
import com.example.flymart.entity.enums.ServerCode;
import com.example.flymart.exeptions.DataNotFoundExceptions;
import com.example.flymart.model.ReqOfferCreate;
import com.example.flymart.repository.OfferRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepo offerRepo;

    public void create(ReqOfferCreate offerCreate){
        Offer offer = new Offer(offerCreate.getOffer());
        offerRepo.save(offer);
    }

    public Object list(){
       return offerRepo.findAll();
    }

    public Object getOne(Long id) throws DataNotFoundExceptions {
        Optional<Offer> optional = offerRepo.findById(id);
        if (optional.isEmpty()){
            throw new DataNotFoundExceptions(ServerCode.OFFER_NOT_FOUND.message);
        }
        return optional.get();
    }
}
