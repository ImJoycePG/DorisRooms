package net.imjoycepg.mc.utils.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class NewProductEntity {

    private String idProduct;
    private String nameProduct;
    private int stockProduct;
    private Date dateJoin;
    private String idCategory;

    public NewProductEntity(String idProduct, String nameProduct, int stockProduct, Date dateJoin, String idCategory) {
        this.idProduct = idProduct;
        this.nameProduct = nameProduct;
        this.stockProduct = stockProduct;
        this.dateJoin = dateJoin;
        this.idCategory = idCategory;
    }

    public NewProductEntity(){}
}
