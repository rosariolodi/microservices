package com.example.productmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/productos")
public class ProductController {

    private List<Producto> products = new ArrayList<>();
    private AtomicLong counter = new AtomicLong();

    @GetMapping
    public List<Producto> getAllProducts() {
        return products;
    }

    @PostMapping
    public Producto createProduct(@RequestBody Producto product) {
        product.setId(counter.incrementAndGet());
        products.add(product);
        return product;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductById(@PathVariable Long id) {
        return products.stream()
            .filter(product -> product.getId().equals(id))
            .findFirst()
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProduct(@PathVariable Long id, @RequestBody Producto updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                updatedProduct.setId(id);
                products.set(i, updatedProduct);
                return ResponseEntity.ok(updatedProduct);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        products.removeIf(product -> product.getId().equals(id));
        return ResponseEntity.ok().build();
    }
}
