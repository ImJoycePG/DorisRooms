package net.imjoycepg.mc.utils.entity.temporal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class RoomsRentalTemp {

    private String idRental;
    private Date dateJoin;
    private Date dateLeave;
    private String obsRental;
    private String dniEmployee;
    private String dniClient;
    private String idRooms;
    private String idMethod;

    public RoomsRentalTemp(String idRental, Date dateJoin, Date dateLeave, String obsRental, String dniEmployee, String dniClient, String idRooms, String idMethod) {
        this.idRental = idRental;
        this.dateJoin = dateJoin;
        this.dateLeave = dateLeave;
        this.obsRental = obsRental;
        this.dniEmployee = dniEmployee;
        this.dniClient = dniClient;
        this.idRooms = idRooms;
        this.idMethod = idMethod;
    }

    public RoomsRentalTemp() {
    }
}
