package com.example.flymart.service;

import com.example.flymart.entity.Offer;
import com.example.flymart.entity.Request;
import com.example.flymart.entity.enums.ServerCode;
import com.example.flymart.exeptions.DataNotFoundExceptions;
import com.example.flymart.model.ReqOfferCreate;
import com.example.flymart.model.ReqRequestCreate;
import com.example.flymart.repository.RequestRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepo requestRepo;

    public void create(ReqRequestCreate offerCreate){
        Request request = new Request(offerCreate.getRequest());
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
