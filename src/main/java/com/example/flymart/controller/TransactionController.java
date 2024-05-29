package com.example.flymart.controller;

import com.example.flymart.entity.enums.ServerCode;
import com.example.flymart.exeptions.DataExistsExceptions;
import com.example.flymart.exeptions.DataNotFoundExceptions;
import com.example.flymart.model.ReqTransaction;
import com.example.flymart.payload.ApiResponseModel;
import com.example.flymart.service.TransactionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/create")
    public HttpEntity<?> create(@Valid @RequestBody ReqTransaction reqTransaction){
        try {
            transactionService.create(reqTransaction);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponseModel(ServerCode.TRANSACTION_CREATE.message,new Object()));
        } catch (DataNotFoundExceptions e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseModel(new Object(),e.getMessage()));
        } catch (DataExistsExceptions e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new ApiResponseModel(new Object(),e.getMessage()));
        }
    }

    @PostMapping("/evaluate/{code}/{score}")
    public HttpEntity<?> evaluateTransaction(@PathVariable(name = "code") String transactionCode, @PathVariable(name = "score") int score){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponseModel(transactionService.evaluateTransaction(transactionCode,score),new Object()));
        } catch (DataNotFoundExceptions e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseModel(new Object(),e.getMessage()));
        }
    }

    @GetMapping("/user/{id}")
    public HttpEntity<?> getByUser(@NotNull(message = "user id not be null!") @PathVariable(name = "id") Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponseModel(transactionService.getUserTransactions(id),new Object()));
        } catch (DataNotFoundExceptions e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseModel(new Object(),e.getMessage()));
        }
    }
}
