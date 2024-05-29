package com.example.flymart.controller;

import com.example.flymart.entity.enums.ServerCode;
import com.example.flymart.exeptions.DataExistsExceptions;
import com.example.flymart.exeptions.DataNotFoundExceptions;
import com.example.flymart.model.ReqOfferCreate;
import com.example.flymart.model.ReqRequestCreate;
import com.example.flymart.model.ReqRequestUpdate;
import com.example.flymart.payload.ApiResponseModel;
import com.example.flymart.service.RequestService;
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
@RequestMapping("/api/request")
public class RequestController {
    private final RequestService requestService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('REQUEST_CREATE')")
    public HttpEntity<?> create(@Valid @RequestBody ReqRequestCreate requestCreate){
        try {
            requestService.create(requestCreate);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponseModel(ServerCode.REQUEST_CREATE.message,new Object()));
        } catch (DataExistsExceptions e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new ApiResponseModel(new Object(),e.getMessage()));
        } catch (DataNotFoundExceptions e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseModel(new Object(),e.getMessage()));
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('REQUEST_UPDATE')")
    public HttpEntity<?> update(@Valid @RequestBody ReqRequestUpdate reqRequestUpdate){
        try {
            requestService.update(reqRequestUpdate);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponseModel(ServerCode.REQUEST_UPDATE.message,new Object()));
        } catch (DataExistsExceptions e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new ApiResponseModel(new Object(),e.getMessage()));
        } catch (DataNotFoundExceptions e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseModel(new Object(),e.getMessage()));
        }
    }

    @GetMapping
    @PreAuthorize("hasAuthority('REQUEST_READ')")
    public HttpEntity<?> list(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponseModel(requestService.list(),new Object()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('REQUEST_READ')")
    public HttpEntity<?> getOne(@NotNull(message = "id not be null!") @PathVariable(name = "id") Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponseModel(requestService.getOne(id),new Object()));
        } catch (DataNotFoundExceptions e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseModel(new Object(),e.getMessage()));
        }
    }
}
