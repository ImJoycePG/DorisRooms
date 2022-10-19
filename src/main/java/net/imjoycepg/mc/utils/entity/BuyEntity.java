package net.imjoycepg.mc.utils.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class BuyEntity {
    private String idBuy;
    private Date dateBuy;
    private String dniEmployee;
    private String rucProved;
}
