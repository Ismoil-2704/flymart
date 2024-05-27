package com.example.flymart.service;

import com.example.flymart.entity.Place;
import com.example.flymart.entity.Region;
import com.example.flymart.entity.enums.ServerCode;
import com.example.flymart.exeptions.DataExistsExceptions;
import com.example.flymart.exeptions.DataNotFoundExceptions;
import com.example.flymart.model.ReqPlaceCreate;
import com.example.flymart.model.ReqPlaceUpdate;
import com.example.flymart.repository.PlaceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepo placeRepo;

    public void create(ReqPlaceCreate placeCreate) throws DataExistsExceptions {
        Optional<Place> optional = placeRepo.findByName(placeCreate.getName());
        if (optional.isPresent()) {
            throw new DataExistsExceptions(ServerCode.PLACE_EXIST.message);
        }
        Place place = new Place();
        placeToSave(place, placeCreate.getName());
    }

    public void update(ReqPlaceUpdate placeUpdate) throws DataNotFoundExceptions {
        Optional<Place> optional = placeRepo.findById(placeUpdate.getId());
        if (optional.isEmpty()) {
            throw new DataNotFoundExceptions(ServerCode.PLACE_NOT_FOUND.message);
        }
        Place place = optional.get();
        placeToSave(place, placeUpdate.getName());
    }

    public void delete(Long id) throws DataNotFoundExceptions {
        Optional<Place> optional = placeRepo.findById(id);
        if (optional.isEmpty()) {
            throw new DataNotFoundExceptions(ServerCode.PLACE_NOT_FOUND.message);
        }
        placeRepo.delete(optional.get());
    }

    private void placeToSave(Place place, String name) {
        place.setName(name);
        placeRepo.save(place);
    }

    public Object list() {
        return placeRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Object getOne(Long id) throws DataNotFoundExceptions {
        Optional<Place> optional = placeRepo.findById(id);
        if (optional.isEmpty()){
            throw new DataNotFoundExceptions(ServerCode.PLACE_NOT_FOUND.message);
        }
        return optional.get();
    }
}
