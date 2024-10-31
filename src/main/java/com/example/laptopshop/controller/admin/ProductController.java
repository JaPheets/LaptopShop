package com.example.laptopshop.controller.admin;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.laptopshop.domian.Product;
import com.example.laptopshop.service.ProductService;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product")
    public Product createProduct(@RequestBody Product createProduct) {
        Product postProduct = this.productService.handleCreateProduct(createProduct);
        return postProduct;
    }

    @DeleteMapping("/product/{id}")
    public String deleteProduct(@PathVariable("id") long id) {
        this.productService.handleDeleteProduct(id);
        return "delete product";
    }

    @GetMapping("/product")
    public List<Product> getAllProduct() {
        return this.productService.fetchAllProduct();
    }

    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable("id") long id) {
        return this.productService.fetchProductById(id);
    }

    @PutMapping("/product")
    public Product updateProduct(@RequestBody Product updateProduct) {
        Product putProduct = this.productService.handleUpdateProduct(updateProduct);
        return putProduct;
    }
}
