package net.imjoycepg.mc.utils;

import java.util.UUID;

public class Utilities {

    public String randomUUID(){
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }
}
