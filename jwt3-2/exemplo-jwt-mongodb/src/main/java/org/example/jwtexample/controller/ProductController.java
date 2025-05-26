package org.example.jwtexample.controller;

import org.example.jwtexample.model.mongo.Product;
import org.example.jwtexample.repository.mongo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Para proteger endpoints
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
// @PreAuthorize("isAuthenticated()") // Pode proteger a classe inteira, ou métodos individuais
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    @PreAuthorize("isAuthenticated()") // Só usuários autenticados podem criar
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        // O ID e dataCadastro serão gerados/preenchidos automaticamente
        Product savedProduct = productRepository.save(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        Optional<Product> productData = productRepository.findById(id);
        return productData.map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product productDetails) {
        Optional<Product> productData = productRepository.findById(id);

        if (productData.isPresent()) {
            Product existingProduct = productData.get();
            existingProduct.setNome(productDetails.getNome());
            existingProduct.setValor(productDetails.getValor());
            // dataCadastro não deve ser alterado em um update
            return new ResponseEntity<>(productRepository.save(existingProduct), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()") // Exemplo: poderia ser @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable String id) {
        try {
            productRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}