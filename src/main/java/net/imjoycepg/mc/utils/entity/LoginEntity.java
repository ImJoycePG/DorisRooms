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

    public LoginEntity(String user, String password) {
        this.userLogin = user;
        this.passLogin = password;
    }
}
