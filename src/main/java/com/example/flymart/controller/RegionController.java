package com.example.flymart.controller;

import com.example.flymart.entity.enums.ServerCode;
import com.example.flymart.exeptions.DataExistsExceptions;
import com.example.flymart.exeptions.DataNotFoundExceptions;
import com.example.flymart.model.ReqRegionCreate;
import com.example.flymart.model.ReqRegionUpdate;
import com.example.flymart.payload.ApiResponseModel;
import com.example.flymart.service.RegionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/region")
public class RegionController {
    private final RegionService regionService;

    @PreAuthorize("hasAuthority('REGION_CREATE')")
    @PostMapping("/create")
    public HttpEntity<?> create(@Valid @RequestBody ReqRegionCreate regionCreate){
        try {
            regionService.create(regionCreate);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponseModel(ServerCode.REGION_CREATE.message,new Object()));
        } catch (DataExistsExceptions e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new ApiResponseModel(new Object(),e.getMessage()));
        }
    }
    @PreAuthorize("hasAuthority('REGION_EDITE')")
    @PutMapping("/update")
    public HttpEntity<?> update(@Valid @RequestBody ReqRegionUpdate regionUpdate){
        try {
            regionService.update(regionUpdate);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponseModel(ServerCode.REGION_UPDATE.message,new Object()));
        } catch (DataNotFoundExceptions e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseModel(new Object(),e.getMessage()));
        }
    }

    // list of regions joined to user
    @PreAuthorize("hasAuthority('REGION_READ')")
    @GetMapping
    public HttpEntity<?> list(){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponseModel(regionService.list(),new Object()));
        } catch (DataNotFoundExceptions e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseModel(new Object(),e.getMessage()));
        }
    }

    @GetMapping("/carrierByRegion/{name}")
    public HttpEntity<?> getByRegion(@NotNull(message = "id not be null!") @PathVariable(name = "name") String region_name){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponseModel(regionService.getCarriersForRegion(region_name),new Object()));
        } catch (DataNotFoundExceptions e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseModel(new Object(),e.getMessage()));
        }
    }

    @PreAuthorize("hasAuthority('REGION_DELETE')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@NotNull(message = "Id not be null!") @PathVariable(name = "id") Long id){
        try {
            regionService.delete(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponseModel(ServerCode.REGION_DELETE.message,new Object()));
        } catch (DataNotFoundExceptions e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseModel(new Object(),e.getMessage()));
        }
    }
}
