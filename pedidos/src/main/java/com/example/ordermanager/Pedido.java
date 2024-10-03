package com.example.ordermanager;

import java.util.List;

public class Pedido {
    private Long id;
    private Long usuarioId; // ID del usuario que hace el pedido
    private List<Long> productoIds;
    private int cantidad;

    public Pedido() {
    }

    public Pedido(Long id, Long usuarioId, List<Long> productoIds, int cantidad) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.productoIds = productoIds;
        this.cantidad = cantidad;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public List<Long> getProductoIds() {
        return productoIds;
    }

    public void setProductoIds(List<Long> productoIds) {
        this.productoIds = productoIds;
    }

    public int getCantidad(){
        return cantidad;
    }

    public void setCantidad(int cantidad){
        this.cantidad = cantidad;
    }
}
