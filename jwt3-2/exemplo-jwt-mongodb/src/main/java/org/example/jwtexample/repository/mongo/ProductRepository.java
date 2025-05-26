package org.example.jwtexample.repository.mongo;

import org.example.jwtexample.model.mongo.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    // MongoRepository<Product, String> já fornece CRUD básico.
    // 'Product' é a entidade, 'String' é o tipo do ID.
    // Você pode adicionar métodos de busca customizados aqui se precisar.
}