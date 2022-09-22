package net.imjoycepg.mc.start;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.imjoycepg.mc.DorisRooms;

import java.util.Objects;

public class Start extends Application {
    private final Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/META-INF/images/DorisRoomsLogo.png")));

    @Override
    public void start(Stage stage) throws Exception {
        DorisRooms.getInstance().startApp(stage);
        stage.getIcons().add(image);
    }

    public static void main(String[] args){
        launch();
    }
}
