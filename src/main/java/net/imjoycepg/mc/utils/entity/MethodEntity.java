package net.imjoycepg.mc.utils.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MethodEntity {
    private String idMethod;
    private String typeMethod;
    private String descMethod;

    public MethodEntity(String idMethod, String typeMethod, String descMethod) {
        this.idMethod = idMethod;
        this.typeMethod = typeMethod;
        this.descMethod = descMethod;
    }

    public MethodEntity(){}
}
