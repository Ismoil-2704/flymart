package com.example.flymart.controller;

import com.example.flymart.entity.enums.ServerCode;
import com.example.flymart.exeptions.DataExistsExceptions;
import com.example.flymart.exeptions.DataNotFoundExceptions;
import com.example.flymart.model.ReqPlaceCreate;
import com.example.flymart.model.ReqPlaceUpdate;
import com.example.flymart.model.ReqRegionCreate;
import com.example.flymart.model.ReqRegionUpdate;
import com.example.flymart.payload.ApiResponseModel;
import com.example.flymart.service.PlaceService;
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
@RequestMapping("/api/place")
public class PlaceController {
    private final PlaceService placeService;

    @PreAuthorize("hasAuthority('PLACE_CREATE')")
    @PostMapping("/create")
    public HttpEntity<?> create(@Valid @RequestBody ReqPlaceCreate placeCreate){
        try {
            placeService.create(placeCreate);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponseModel(ServerCode.PLACE_CREATE.message,new Object()));
        } catch (DataExistsExceptions e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new ApiResponseModel(new Object(),e.getMessage()));
        }
    }
    @PreAuthorize("hasAuthority('PLACE_EDITE')")
    @PutMapping("/update")
    public HttpEntity<?> update(@Valid @RequestBody ReqPlaceUpdate placeUpdate){
        try {
            placeService.update(placeUpdate);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponseModel(ServerCode.PLACE_UPDATE.message,new Object()));
        } catch (DataNotFoundExceptions e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseModel(new Object(),e.getMessage()));
        }
    }

    @PreAuthorize("hasAuthority('PLACE_READ')")
    @GetMapping
    public HttpEntity<?> list(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponseModel(placeService.list(),new Object()));
    }

    @PreAuthorize("hasAuthority('PLACE_READ')")
    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@NotNull(message = "id not be null!") @PathVariable(name = "id") Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponseModel(placeService.getOne(id),new Object()));
        } catch (DataNotFoundExceptions e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseModel(new Object(),e.getMessage()));
        }
    }

    @PreAuthorize("hasAuthority('PLACE_DELETE')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@NotNull(message = "Id not be null!") @PathVariable(name = "id") Long id){
        try {
            placeService.delete(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponseModel(ServerCode.PLACE_DELETE.message,new Object()));
        } catch (DataNotFoundExceptions e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseModel(new Object(),e.getMessage()));
        }
    }
}
