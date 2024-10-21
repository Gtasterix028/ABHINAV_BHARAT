package com.spring.jwt.controller;

import com.spring.jwt.Interfaces.IProducts;
import com.spring.jwt.dto.ProductsDTO;
import com.spring.jwt.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private IProducts productsInterface;

    @GetMapping("/getByID")
    public ResponseEntity<Response> getProductById(@RequestParam Integer id) {
        try {
            ProductsDTO productsDTO = productsInterface.getProductByID(id);
            return ResponseEntity.ok(new Response("Product retrieved successfully", productsDTO, false));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response("An error occurred", e.getMessage(), true));
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<Response> getAllProducts() {
        try {
            List<ProductsDTO> productsList = productsInterface.getAllProducts();
            return ResponseEntity.ok(new Response("All products retrieved successfully", productsList, false));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response("An error occurred", e.getMessage(), true));
        }
    }

    @PostMapping("/saveInformation")
    public ResponseEntity<Response> createProduct(@RequestBody ProductsDTO productsDTO) {
        try {
            ProductsDTO createdProduct = productsInterface.saveInformation(productsDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new Response("Product created successfully", createdProduct, false));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response("An error occurred", e.getMessage(), true));
        }
    }

    @PatchMapping("/updateAny")
    public ResponseEntity<Response> updateProduct(@RequestParam Integer id, @RequestBody ProductsDTO productsDTO) {
        try {
            ProductsDTO updatedProduct = productsInterface.updateAny(id, productsDTO);
            return ResponseEntity.ok(new Response("Product updated successfully", updatedProduct, false));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response("An error occurred", e.getMessage(), true));
        }
    }

    @DeleteMapping("/deleteByID")
    public ResponseEntity<Response> deleteProductByID(@RequestParam Integer id) {
        try {
            productsInterface.deleteProduct(id);
            return ResponseEntity.ok(new Response("Product deleted successfully", null, false));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response("An error occurred", e.getMessage(), true));
        }
    }
}
