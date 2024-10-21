package com.spring.jwt.service;

import com.spring.jwt.Interfaces.IProducts;
import com.spring.jwt.dto.ProductsDTO;
import com.spring.jwt.entity.Products;
import com.spring.jwt.repository.ProductsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductsService implements IProducts {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductsDTO getProductByID(Integer id) {
        Products product = productsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
        return modelMapper.map(product, ProductsDTO.class);
    }

    @Override
    public ProductsDTO saveInformation(ProductsDTO productsDTO) {
        Products product = modelMapper.map(productsDTO, Products.class);
        Products savedProduct = productsRepository.save(product);
        return modelMapper.map(savedProduct, ProductsDTO.class);
    }

    @Override
    public ProductsDTO updateAny(Integer id, ProductsDTO productsDTO) {
        Products product = productsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));

        if (productsDTO.getProductName() != null) {
            product.setProductName(productsDTO.getProductName());
        }
        if (productsDTO.getDescription() != null) {
            product.setDescription(productsDTO.getDescription());
        }
        if (productsDTO.getPrice() != null) {
            product.setPrice(productsDTO.getPrice());
        }

        Products updatedProduct = productsRepository.save(product);
        return modelMapper.map(updatedProduct, ProductsDTO.class);
    }

    @Override
    public void deleteProduct(Integer id) {
        Products existingProduct = productsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
        productsRepository.delete(existingProduct);
    }

    @Override
    public List<ProductsDTO> getAllProducts() {
        List<Products> productsList = productsRepository.findAll();
        List<ProductsDTO> productsDTOList = new ArrayList<>();

        for (Products product : productsList) {
            productsDTOList.add(modelMapper.map(product, ProductsDTO.class));
        }

        return productsDTOList;
    }
}
