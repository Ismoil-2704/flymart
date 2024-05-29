package com.example.flymart.controller;

import com.example.flymart.entity.enums.ServerCode;
import com.example.flymart.exeptions.DataExistsExceptions;
import com.example.flymart.exeptions.DataNotFoundExceptions;
import com.example.flymart.model.ReqUserCreate;
import com.example.flymart.model.ReqUserUpdate;
import com.example.flymart.payload.ApiResponseModel;
import com.example.flymart.service.UserService;
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
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAuthority('USER_CREATE')")
    @PostMapping("/create")
    public HttpEntity<?> create(@Valid @RequestBody ReqUserCreate reqUserCreate){
        try {
            userService.create(reqUserCreate);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponseModel(ServerCode.USER_CREATED.message,new Object()));
        } catch (DataExistsExceptions e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new ApiResponseModel(new Object(),e.getMessage()));
        } catch (DataNotFoundExceptions e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseModel(new Object(),e.getMessage()));
        }
    }
    @PreAuthorize("hasAuthority('USER_EDIT')")
    @PutMapping("/update")
    public HttpEntity<?> update(@Valid @RequestBody ReqUserUpdate reqUserUpdate){
        try {
            userService.update(reqUserUpdate);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponseModel(ServerCode.USER_UPDATED.message,new Object()));
        } catch (DataNotFoundExceptions e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseModel(new Object(),e.getMessage()));
        }
    }
    @PreAuthorize("hasAuthority('USER_EDIT')")
    @PutMapping("/status")
    public HttpEntity<?> updateStatus(@NotNull(message = "Id not be null!") @RequestParam(name = "id") Long id,
                                      @RequestParam(name = "status") boolean status){
        try {
            userService.updateStatus(id,status);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponseModel(ServerCode.USER_STATUS_UPDATED.message,new Object()));
        } catch (DataNotFoundExceptions e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseModel(new Object(),e.getMessage()));
        }
    }

    @PreAuthorize("hasAuthority('USER_READ')")
    @GetMapping
    public HttpEntity<?> list(){
       return ResponseEntity.status(HttpStatus.OK)
               .body(userService.list());
    }

    @PreAuthorize("hasAuthority('USER_READ')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> update(@NotNull(message = "Id not be null!") @PathVariable(name = "id") Long id){
        try {
            userService.delete(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponseModel(ServerCode.USER_DELETED.message,new Object()));
        } catch (DataNotFoundExceptions e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseModel(new Object(),e.getMessage()));
        }
    }
}
