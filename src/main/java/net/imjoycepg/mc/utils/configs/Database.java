package net.imjoycepg.mc.utils.configs;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Database {
    private String host = "127.0.0.1";
    private String port = "3306";
    private String username = "root";
    private String password = "THIS_PASSWORD";
    private String database = "DorisRooms";
}
