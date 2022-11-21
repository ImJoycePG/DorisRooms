package net.imjoycepg.mc.utils.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RoomsTypeEntity {
    private String idTypeRooms;
    private String nameTypeRooms;

    public RoomsTypeEntity() {
    }

    public RoomsTypeEntity(String idTypeRooms, String nameTypeRooms) {
        this.idTypeRooms = idTypeRooms;
        this.nameTypeRooms = nameTypeRooms;
    }
}
