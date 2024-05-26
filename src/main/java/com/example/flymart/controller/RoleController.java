package com.example.flymart.controller;

import com.example.flymart.entity.enums.ServerCode;
import com.example.flymart.exeptions.DataExistsExceptions;
import com.example.flymart.exeptions.DataNotFoundExceptions;
import com.example.flymart.model.ReqRoleCreate;
import com.example.flymart.model.ReqRoleUpdate;
import com.example.flymart.payload.ApiResponseModel;
import com.example.flymart.service.RoleService;
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
@RequestMapping("/api/role")
@PreAuthorize("hasRole('ADMIN')")
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/create")
    public HttpEntity<?> create(@Valid @RequestBody ReqRoleCreate roleCreate){
        try {
            roleService.create(roleCreate);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponseModel(ServerCode.ROLE_CREATED.message,new Object()));
        } catch (DataExistsExceptions e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new ApiResponseModel(new Object(),e.getMessage()));
        }
    }

    @PutMapping("/update")
    public HttpEntity<?> update(@Valid @RequestBody ReqRoleUpdate roleUpdate){
        try {
            roleService.update(roleUpdate);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponseModel(ServerCode.ROLE_UPDATE.message,new Object()));
        } catch (DataNotFoundExceptions e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseModel(new Object(),e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@NotNull(message = "Id not be null!") @PathVariable(name = "id") Long id){
        try {
            roleService.delete(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponseModel(ServerCode.ROLE_DELETE.message,new Object()));
        } catch (DataNotFoundExceptions e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseModel(new Object(),e.getMessage()));
        }
    }
}
