package com.example.flymart.service;

import com.example.flymart.entity.Region;
import com.example.flymart.entity.enums.ServerCode;
import com.example.flymart.exeptions.DataNotFoundExceptions;
import com.example.flymart.repository.RegionRepo;
import com.example.flymart.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarrierService {
    private final RegionRepo regionRepo;
    private final UserRepo userRepo;

    public Object getCarriersForRegion(String regionName) throws DataNotFoundExceptions {
        Optional<Region> optionalRegion = regionRepo.findByName(regionName);
        if (optionalRegion.isEmpty()){
            throw new DataNotFoundExceptions(ServerCode.REGION_NOT_FOUND.message);
        }
        return userRepo.findByRegionAndRole(optionalRegion.get().getId(),"CARRIER").orElseThrow();
    }
}
