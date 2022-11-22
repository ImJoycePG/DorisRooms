package net.imjoycepg.mc.utils.entity.temporal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class OrderProductTemp {

    private String idOrder;
    private Date dateOrder;
    private String dniEmployee;
    private String rucProved;

    private String idOrderProd;
    private double priceOrderProd;
    private int stockOrderProd;
    private String idProduct;
    private String urlOrder;
}
