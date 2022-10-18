package net.imjoycepg.mc.utils.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProvedEntity {
    private String rucProved;
    private String namesProved;
    private String surnamesProved;
    private String phoneProved;
    private String emailProved;

    public ProvedEntity(String rucProved, String namesProved, String surnamesProved, String phoneProved, String emailProved) {
        this.rucProved = rucProved;
        this.namesProved = namesProved;
        this.surnamesProved = surnamesProved;
        this.phoneProved = phoneProved;
        this.emailProved = emailProved;
    }

    public ProvedEntity() {
    }
}
