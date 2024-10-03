package com.example.ordermanager;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class PedidoController {

    private List<Pedido> pedidos = new ArrayList<>();
    private final RestTemplate restTemplate = new RestTemplate();

    private final String USER_SERVICE_URL = "http://localhost:8081/usuarios";  // Update as needed
    private final String PRODUCT_SERVICE_URL = "http://localhost:8082/productos";

    // Add a new order
    @PostMapping
    public Pedido addOrder(@RequestBody Pedido pedidoRequest) {
        System.out.println("Received order request: " + pedidoRequest);
    
        try {
            UserDTO user = restTemplate.getForObject(USER_SERVICE_URL + "/" + pedidoRequest.getUsuarioId(), UserDTO.class);
            System.out.println("Fetched User: " + user);
    
            ProductDTO product = restTemplate.getForObject(PRODUCT_SERVICE_URL + "/" + pedidoRequest.getProductoIds().get(0), ProductDTO.class);
            System.out.println("Fetched Product: " + product);
    
            if (user != null && product != null) {
                Pedido newPedido = new Pedido(
                        pedidoRequest.getId(),
                        pedidoRequest.getUsuarioId(),
                        pedidoRequest.getProductoIds(),
                        pedidoRequest.getCantidad()// Calculate total price
                );
                pedidos.add(newPedido);
                return newPedido;
            } else {
                System.out.println("User or Product is null");
                return null; // Handle this case better if needed
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the full stack trace for any errors
            return null; // or handle this according to your error management strategy
        }
    }

    @GetMapping
    public Collection<Pedido> getAllOrders() {
        return pedidos;
    }
    // Find order by ID
    @GetMapping("/{id}")
    public Pedido getOrderById(@PathVariable Long id) {
        return pedidos.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }

    // Delete an order by ID
    @DeleteMapping("/{id}")
    public String deleteOrderById(@PathVariable Long id) {
        pedidos.removeIf(p -> p.getId().equals(id));
        return "Order deleted!";
    }
}

