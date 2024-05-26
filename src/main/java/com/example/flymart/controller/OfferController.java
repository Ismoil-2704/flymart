package com.example.flymart.controller;

import com.example.flymart.entity.enums.ServerCode;
import com.example.flymart.exeptions.DataNotFoundExceptions;
import com.example.flymart.model.ReqOfferCreate;
import com.example.flymart.payload.ApiResponseModel;
import com.example.flymart.service.OfferService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/offer")
public class OfferController {
    private final OfferService offerService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('OFFER_CREATE')")
    public HttpEntity<?> create(@Valid @RequestBody ReqOfferCreate offerCreate){
        offerService.create(offerCreate);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponseModel(ServerCode.OFFER_CREATE.message,new Object()));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('OFFER_READ')")
    public HttpEntity<?> list(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponseModel(offerService.list(),new Object()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('OFFER_READ')")
    public HttpEntity<?> getOne(@NotNull(message = "id not be null!") @PathVariable(name = "id") Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponseModel(offerService.getOne(id),new Object()));
        } catch (DataNotFoundExceptions e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseModel(new Object(),e.getMessage()));
        }
    }
}
