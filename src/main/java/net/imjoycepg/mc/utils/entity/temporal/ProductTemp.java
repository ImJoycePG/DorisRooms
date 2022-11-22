package net.imjoycepg.mc.utils.entity.temporal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class ProductTemp {

    private String idProduct;
    private String nameProduct;
    private int stockProduct;
    private Date dateJoin;
    private String idCategory;
}
