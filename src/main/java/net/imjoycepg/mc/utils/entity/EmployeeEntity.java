package net.imjoycepg.mc.utils.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmployeeEntity {
    private String dniEmployee;
    private String namesEmployee;
    private String surnamesEmployee;

    public EmployeeEntity(String dniEmployee, String namesEmployee, String surnamesEmployee) {
        this.dniEmployee = dniEmployee;
        this.namesEmployee = namesEmployee;
        this.surnamesEmployee = surnamesEmployee;
    }

    public EmployeeEntity() {
    }
}
