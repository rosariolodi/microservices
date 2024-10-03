package com.example.usermanager;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


@RestController
@RequestMapping("/usuarios")
public class UserController {

    private Map<Long, Usuario> users = new HashMap<>();
    private AtomicLong counter = new AtomicLong();

    @GetMapping
    public Collection<Usuario> getAllUsers() {
        return users.values();
    }

    @PostMapping
    public Usuario createUser(@RequestBody Usuario user) {
        long id = counter.incrementAndGet();
        user.setId(id);
        users.put(id, user);
        return user;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUserById(@PathVariable Long id) {
        Usuario user = users.get(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUser(@PathVariable Long id, @RequestBody Usuario user) {
        if (!users.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        user.setId(id);
        users.put(id, user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        users.remove(id);
        return ResponseEntity.ok().build();
    }
}
