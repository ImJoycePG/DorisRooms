package net.imjoycepg.mc.utils.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class RoomsReservationEntity {

    private String idReservation;
    private Date dateJoin;
    private Date dateLeave;
    private String formReservation;
    private String dniEmployee;
    private String dniClient;
    private String idRooms;

    public RoomsReservationEntity(String idReservation, Date dateJoin, Date dateLeave, String formReservation, String dniEmployee, String dniClient, String idRooms) {
        this.idReservation = idReservation;
        this.dateJoin = dateJoin;
        this.dateLeave = dateLeave;
        this.formReservation = formReservation;
        this.dniEmployee = dniEmployee;
        this.dniClient = dniClient;
        this.idRooms = idRooms;
    }

    public RoomsReservationEntity() {
    }
}
