package com.example.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.laptopshop.domian.Product;
import com.example.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product handleCreateProduct(Product product) {
        return this.productRepository.save(product);
    }

    public void handleDeleteProduct(Long id) {
        this.productRepository.deleteById(id);
    }

    public List<Product> fetchAllProduct() {
        return this.productRepository.findAll();
    }

    public Product fetchProductById(long id) {
        Optional<Product> productOptional = this.productRepository.findById(id);
        if (productOptional.isPresent()) {
            return productOptional.get();
        }
        return null;
    }

    public Product handleUpdateProduct(Product putProduct) {
        Product currenProduct = this.fetchProductById(putProduct.getId());
        if (currenProduct != null) {
            currenProduct.setName(putProduct.getName());
            currenProduct.setPrice(putProduct.getPrice());
            currenProduct.setShortDesc(putProduct.getShortDesc());
            currenProduct.setQuantity(putProduct.getQuantity());
            currenProduct.setSold(putProduct.getSold());

            currenProduct = this.productRepository.save(currenProduct);
        }
        return currenProduct;
    }

}
