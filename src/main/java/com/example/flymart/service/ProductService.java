package com.example.flymart.service;

import com.example.flymart.entity.Product;
import com.example.flymart.entity.Region;
import com.example.flymart.entity.enums.ServerCode;
import com.example.flymart.exeptions.DataExistsExceptions;
import com.example.flymart.exeptions.DataNotFoundExceptions;
import com.example.flymart.model.ReqProductCreate;
import com.example.flymart.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;

    public void create (ReqProductCreate productCreate) throws DataExistsExceptions {
        Optional<Product> optionalProduct = productRepo.findByName(productCreate.getName());
        if (optionalProduct.isPresent()){
            throw new DataExistsExceptions(ServerCode.PRODUCT_EXIST.message);
        }
        Product product = new Product();
        product.setName(productCreate.getName());
        productRepo.save(product);
    }

    public void delete(Long id) throws DataNotFoundExceptions {
        Optional<Product> optional = productRepo.findById(id);
        if (optional.isEmpty()){
            throw new DataNotFoundExceptions(ServerCode.REGION_NOT_FOUND.message);
        }
        productRepo.delete(optional.get());
    }

    public Object list() {
        return productRepo.findAll();
    }
}
