package com.example.projetoLanchonete.demo.service;

import com.example.projetoLanchonete.demo.entities.Product;
import com.example.projetoLanchonete.demo.repository.ProductRepository;
import com.example.projetoLanchonete.demo.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(String id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new ObjectNotFoundException("Product not found");
        }
        return product.get();
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void delete(String id) {
        productRepository.deleteById(id);
    }

    public List<Product> findByCategory(String category) {
        List<Product> products = findAll();
        return products.stream()
                .filter(a -> Objects.equals(a.getCategory().toString(), category))
                .collect(Collectors.toList());

    }

    public void saveAll(List<Product> products) {
        productRepository.saveAll(products);
    }

}
