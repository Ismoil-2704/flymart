package com.example.flymart.service;

import com.example.flymart.entity.Place;
import com.example.flymart.entity.Product;
import com.example.flymart.entity.Request;
import com.example.flymart.entity.User;
import com.example.flymart.entity.enums.ServerCode;
import com.example.flymart.exeptions.DataExistsExceptions;
import com.example.flymart.exeptions.DataNotFoundExceptions;
import com.example.flymart.model.ReqRequestCreate;
import com.example.flymart.model.ReqRequestUpdate;
import com.example.flymart.repository.PlaceRepo;
import com.example.flymart.repository.ProductRepo;
import com.example.flymart.repository.RequestRepo;
import com.example.flymart.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepo requestRepo;
    private final PlaceRepo placeRepo;
    private final UserRepo userRepo;
    private final ProductRepo productRepo;

    public void create(ReqRequestCreate requestCreate) throws DataExistsExceptions, DataNotFoundExceptions {
        Optional<Request> optionalRequest = requestRepo.findByCode(requestCreate.getCode());
        if (optionalRequest.isPresent()){
            throw new DataExistsExceptions(ServerCode.REQUEST_EXIST_CODE.message);
        }
        requestToSave(requestCreate.getProductCode(), requestCreate.getPlaceId(), requestCreate.getUserId(), new Request(), requestCreate.getRequest(), requestCreate.getCode());
    }

    public void update(ReqRequestUpdate requestUpdate) throws DataExistsExceptions, DataNotFoundExceptions {
        Optional<Request> optionalRequest = requestRepo.findById(requestUpdate.getId());
        if (optionalRequest.isEmpty()){
            throw new DataExistsExceptions(ServerCode.REQUEST_NOT_FOUND.message);
        }
        requestToSave(requestUpdate.getProductCode(), requestUpdate.getPlaceId(), requestUpdate.getUserId(), new Request(), requestUpdate.getRequest(), requestUpdate.getCode());
    }

    private void requestToSave(String productCode, Long placeId, Long userId, Request request, String request1, String code) throws DataNotFoundExceptions {
        Product product = productRepo.findByCode(productCode).orElseThrow(() -> new DataNotFoundExceptions(ServerCode.PRODUCT_NOT_FOUND.message));
        Place place = placeRepo.findById(placeId).orElseThrow(() -> new DataNotFoundExceptions(ServerCode.PLACE_NOT_FOUND.message));
        User user = userRepo.findById(userId).orElseThrow(() -> new DataNotFoundExceptions(ServerCode.USER_NOT_FOUND.message));
        request.setRequest(request1);
        request.setCode(code);
        request.setPlace_id(place.getId());
        request.setUserId(user.getId());
        request.setProductId(product.getId());
        requestRepo.save(request);
    }

    public Object list(){
        return requestRepo.findAll();
    }

    public Object getOne(Long id) throws DataNotFoundExceptions {
        Optional<Request> optional = requestRepo.findById(id);
        if (optional.isEmpty()){
            throw new DataNotFoundExceptions(ServerCode.REQUEST_NOT_FOUND.message);
        }
        return optional.get();
    }
}
