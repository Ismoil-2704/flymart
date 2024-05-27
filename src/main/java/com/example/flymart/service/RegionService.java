package com.example.flymart.service;

import com.example.flymart.entity.BaseEntity;
import com.example.flymart.entity.Region;
import com.example.flymart.entity.User;
import com.example.flymart.entity.enums.ServerCode;
import com.example.flymart.exeptions.DataExistsExceptions;
import com.example.flymart.exeptions.DataNotFoundExceptions;
import com.example.flymart.model.ReqRegionCreate;
import com.example.flymart.model.ReqRegionUpdate;
import com.example.flymart.repository.PlaceRepo;
import com.example.flymart.repository.RegionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepo regionRepo;
    private final PlaceRepo placeRepo;
    private final AuthService authService;

    public void create(ReqRegionCreate regionCreate) throws DataExistsExceptions {
        Optional<Region> optional = regionRepo.findByName(regionCreate.getName());
        if (optional.isPresent()){
            throw new DataExistsExceptions(ServerCode.PLACE_EXIST.message);
        }
        Region region = new Region();
        regionToSave(region,regionCreate.getName(),regionCreate.getPlaceIds());
    }

    public void update(ReqRegionUpdate regionUpdate) throws DataNotFoundExceptions {
        Optional<Region> optional = regionRepo.findById(regionUpdate.getId());
        if (optional.isEmpty()){
            throw new DataNotFoundExceptions(ServerCode.PLACE_NOT_FOUND.message);
        }
        Region region = optional.get();
        regionToSave(region,regionUpdate.getName(),regionUpdate.getPlaceIds());
    }

    public void delete(Long id) throws DataNotFoundExceptions {
        Optional<Region> optional = regionRepo.findById(id);
        if (optional.isEmpty()){
            throw new DataNotFoundExceptions(ServerCode.REGION_NOT_FOUND.message);
        }
        regionRepo.delete(optional.get());
    }

    private void regionToSave(Region region, String name, Set<Long> placeIds){
        region.setName(name);
        region.setPlaces(placeRepo.findAllById(placeIds));
        regionRepo.save(region);
    }

    public Object list() throws DataNotFoundExceptions {
        User user = authService.currentUser();
        List<Region> regions = regionRepo.findAllById(user.getRegions().stream().map(Region::getId).toList());
        regions.sort(Comparator.comparing(BaseEntity::getId));
        return regions;
    }
}
