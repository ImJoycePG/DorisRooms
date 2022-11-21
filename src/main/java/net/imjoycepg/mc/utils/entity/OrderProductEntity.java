package net.imjoycepg.mc.utils.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderProductEntity {

    private String idOrderProd;
    private double priceOrderProd;
    private int stockOrderProd;
    private String idProduct;
    private String idOrder;

    public OrderProductEntity() {
    }

    public OrderProductEntity(String idOrderProd, double priceOrderProd, int stockOrderProd, String idProduct) {
        this.idOrderProd = idOrderProd;
        this.priceOrderProd = priceOrderProd;
        this.stockOrderProd = stockOrderProd;
        this.idProduct = idProduct;
    }
}
