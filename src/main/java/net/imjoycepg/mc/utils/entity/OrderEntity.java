package net.imjoycepg.mc.utils.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class OrderEntity {

    private String idOrder;
    private Date dateOrder;
    private String dniEmployee;
    private String rucProved;


    public OrderEntity(String idOrder, Date dateOrder, String dniEmployee, String rucProved) {
        this.idOrder = idOrder;
        this.dateOrder = dateOrder;
        this.dniEmployee = dniEmployee;
        this.rucProved = rucProved;
    }

    public OrderEntity() {
    }
}
