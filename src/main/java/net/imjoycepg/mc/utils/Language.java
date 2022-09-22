package net.imjoycepg.mc.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import net.imjoycepg.mc.DorisRooms;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@RequiredArgsConstructor
public enum Language {

    SPANISH("messages_es"),
    ENGLISH("messages_en");

    private final String fileName;

    private JsonObject config;
    public JsonObject getConfig(){
        if(config == null){
            config = loadConfig();
        }
        return config;
    }

    JsonObject loadConfig() {
        File file = new File(fileName + ".json");
        try {
            Files.copy(DorisRooms.getInstance().getClass().getClassLoader().getResourceAsStream(fileName + ".json"), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            return JsonParser.parseReader(new FileReader(file)).getAsJsonObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
