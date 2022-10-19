package net.imjoycepg.mc.utils.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ClientEntity {
    private String dniClient;
    private String namesClient;
    private String surnamesClient;

    public ClientEntity(String dniClient, String namesClient, String surnamesClient) {
        this.dniClient = dniClient;
        this.namesClient = namesClient;
        this.surnamesClient = surnamesClient;
    }

    public ClientEntity(){}
}
