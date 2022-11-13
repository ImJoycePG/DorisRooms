package net.imjoycepg.mc.utils.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CategoryProdEntity {
    private String idCategory;
    private String nameCategory;
    private String descCategory;

    public CategoryProdEntity(String idCategory, String nameCategory, String descCategory) {
        this.idCategory = idCategory;
        this.nameCategory = nameCategory;
        this.descCategory = descCategory;
    }

    public CategoryProdEntity(){}
}
