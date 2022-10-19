package net.imjoycepg.mc.utils.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class ProductEntity {
    private String idProduct;
    private String nameProduct;
    private int stockProduct;
    private Date joinDateProduct;
    private String idCategory;


}
