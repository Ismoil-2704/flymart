package com.example.flymart.service;

import com.example.flymart.entity.Product;
import com.example.flymart.entity.User;
import com.example.flymart.entity.enums.ServerCode;
import com.example.flymart.exeptions.DataExistsExceptions;
import com.example.flymart.exeptions.DataNotFoundExceptions;
import com.example.flymart.model.ReqProductCreate;
import com.example.flymart.model.ReqProductUpdate;
import com.example.flymart.repository.ProductRepo;
import com.example.flymart.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;
    private final AuthService authService;
    private final UserRepo userRepo;

    public void create(ReqProductCreate productCreate) throws DataExistsExceptions, DataNotFoundExceptions {
        Optional<Product> optionalProduct = productRepo.findByCode(productCreate.getCode());
        User user = userRepo.findById(productCreate.getUserId()).orElseThrow(() -> new DataNotFoundExceptions(ServerCode.USER_NOT_FOUND.message));
        if (optionalProduct.isPresent()) {
            throw new DataExistsExceptions(ServerCode.PRODUCT_EXIST_WITH_CODE.message);
        }
        Product product = new Product();
        productToSave(product, productCreate.getName(), productCreate.getPrice(), productCreate.getCode(), user);
        productRepo.save(product);
    }

    public void update(ReqProductUpdate productUpdate) throws DataNotFoundExceptions {
        Optional<Product> optionalProduct = productRepo.findById(productUpdate.getId());
        if (optionalProduct.isEmpty()) {
            throw new DataNotFoundExceptions(ServerCode.PRODUCT_NOT_FOUND.message);
        }
        Product product = new Product();
        productToSave(product,productUpdate.getName(),productUpdate.getPrice(),productUpdate.getCode(),optionalProduct.get().getUser());
        productRepo.save(product);
    }

    private static void productToSave(Product product, String name, Double price, String code, User user) {
        product.setName(name);
        product.setPrice(price);
        product.setCode(code);
        product.setUser(user);
    }

    public void delete(Long id) throws DataNotFoundExceptions {
        Optional<Product> optional = productRepo.findById(id);
        if (optional.isEmpty()) {
            throw new DataNotFoundExceptions(ServerCode.REGION_NOT_FOUND.message);
        }
        productRepo.delete(optional.get());
    }

    public Object list() {
        return productRepo.findAll();
    }

    public Object getUserProductList() {
        try {
            User user = authService.currentUser();
            return productRepo.findByUserId(user.getId());
        } catch (DataNotFoundExceptions e) {
            throw new RuntimeException(e);
        }
    }
}
