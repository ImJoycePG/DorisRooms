package net.imjoycepg.mc.utils.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BuyProdEntity {
    private String idBuyProd;
    private String idBuy;
    private String idProduct;
    private double priceProduct;
    private int cantBuyProd;
}
