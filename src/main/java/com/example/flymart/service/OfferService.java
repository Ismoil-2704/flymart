package com.example.flymart.service;

import com.example.flymart.entity.Offer;
import com.example.flymart.entity.Place;
import com.example.flymart.entity.Product;
import com.example.flymart.entity.User;
import com.example.flymart.entity.enums.ServerCode;
import com.example.flymart.exeptions.DataNotFoundExceptions;
import com.example.flymart.model.ReqOfferCreate;
import com.example.flymart.repository.OfferRepo;
import com.example.flymart.repository.PlaceRepo;
import com.example.flymart.repository.ProductRepo;
import com.example.flymart.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepo offerRepo;
    private final UserRepo userRep;
    private final ProductRepo productRepo;
    private final PlaceRepo placeRepo;
    private final AuthService authService;

    public void create(ReqOfferCreate offerCreate) throws DataNotFoundExceptions {
        if (offerRepo.findByCode(offerCreate.getCode()).isPresent()){
            throw new DataNotFoundExceptions(ServerCode.OFFER_NOT_FOUND.message);
        }
        Product product = productRepo.findByCode(offerCreate.getProductCode()).orElseThrow(() -> new DataNotFoundExceptions(ServerCode.PRODUCT_NOT_FOUND.message));
        Place place = placeRepo.findById(offerCreate.getPlaceId()).orElseThrow(() -> new DataNotFoundExceptions(ServerCode.PLACE_NOT_FOUND.message));
        User user = userRep.findById(offerCreate.getUserId()).orElseThrow(() -> new DataNotFoundExceptions(ServerCode.USER_NOT_FOUND.message));
        Offer offer = new Offer();
        offer.setCode(offerCreate.getCode());
        offer.setOffer(offerCreate.getOffer());
        offer.setProductCode(product.getCode());
        offer.setUserId(user.getId());
        offer.setPlaceId(place.getId());
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

    public Object getUserOffers(){
        try {
            User user = authService.currentUser();
            return offerRepo.findByUserId(user.getId());
        } catch (DataNotFoundExceptions e) {
            throw new RuntimeException(e);
        }
    }
}
