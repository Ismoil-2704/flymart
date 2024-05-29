package com.example.flymart.controller;

import com.example.flymart.entity.enums.ServerCode;
import com.example.flymart.exeptions.DataExistsExceptions;
import com.example.flymart.exeptions.DataNotFoundExceptions;
import com.example.flymart.model.ReqProductCreate;
import com.example.flymart.payload.ApiResponseModel;
import com.example.flymart.service.ProductService;
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
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/create")
    public HttpEntity<?> create(@Valid @RequestBody ReqProductCreate productCreate){
        try {
            productService.create(productCreate);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponseModel(ServerCode.PRODUCT_CREATE.message,new Object()));
        } catch (DataExistsExceptions e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new ApiResponseModel(new Object(),e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@NotNull(message = "Id not be null!") @PathVariable(name = "id") Long id){
        try {
            productService.delete(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponseModel(ServerCode.PRODUCT_DELETE.message,new Object()));
        } catch (DataNotFoundExceptions e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseModel(new Object(),e.getMessage()));
        }
    }

    @GetMapping
    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    public HttpEntity<?> list(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.list());
    }

    @GetMapping("/userProducts")
    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    public HttpEntity<?> userProducts(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.getUserProductList());
    }
}
