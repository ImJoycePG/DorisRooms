package net.imjoycepg.mc.utils.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginEntity {
    private String idLogin;
    private String userLogin;
    private String passLogin;
    private String roleLogin;

    private String LoginUser;
    private String LoginPass;
    private String LoginRole;

    public LoginEntity(String user, String password) {
        this.userLogin = user;
        this.passLogin = password;
    }public LoginEntity(String user, String password, String role) {
        this.userLogin = user;
        this.passLogin = password;
        this.roleLogin = role;
    }

    public LoginEntity() {}

    public boolean checkAdminOrRoot(){
        return this.LoginRole.equalsIgnoreCase("Administrator") || this.LoginRole.equalsIgnoreCase("root") ||
                this.LoginRole.equalsIgnoreCase("Administrador") || this.LoginRole.equalsIgnoreCase("Recepcionista") ||
                this.LoginRole.equalsIgnoreCase("Receptionist");
    }
}
