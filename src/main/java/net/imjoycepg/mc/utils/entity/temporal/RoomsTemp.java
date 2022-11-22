package net.imjoycepg.mc.utils.entity.temporal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RoomsTemp {

    private String idRooms;
    private String descRooms;
    private double priceRooms;
    private String obsRooms;
    private String idTypeRooms;

    public RoomsTemp(String idRooms, String descRooms, double priceRooms, String obsRooms, String idTypeRooms) {
        this.idRooms = idRooms;
        this.descRooms = descRooms;
        this.priceRooms = priceRooms;
        this.obsRooms = obsRooms;
        this.idTypeRooms = idTypeRooms;
    }

    public RoomsTemp() {
    }
}
