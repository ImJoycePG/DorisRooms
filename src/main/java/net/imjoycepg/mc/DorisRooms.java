package net.imjoycepg.mc;

import com.dustinredmond.fxalert.FXAlert;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import lombok.Setter;
import net.imjoycepg.mc.utils.JsonConfig;
import net.imjoycepg.mc.utils.Language;
import net.imjoycepg.mc.utils.MySQL;
import net.imjoycepg.mc.utils.configs.Database;
import net.imjoycepg.mc.utils.tables.LoginTable;

import java.io.IOException;
import java.util.Objects;

@Getter
public class DorisRooms {
    @Getter
    private static final DorisRooms instance = new DorisRooms();
    private final MySQL mySQL = new MySQL();

    @Getter @Setter
    private Language language = Language.ENGLISH;

    protected Stage stage;
    private Scene scene;
    private Database database;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
    private final Image AlertImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/META-INF/images/DorisRoomsAlerts.png")));

    private final LoginTable loginTable = new LoginTable();

    public void startApp(Stage stage) throws IOException{
        this.stage = stage;
        try{
            database = JsonConfig.loadConfig(Database.class);
        }catch (InstantiationException | IllegalAccessException e){
            FXAlert.setGlobalTitleBarIcon(AlertImage);
            FXAlert.showException(e, "");
            return;
        }
        mySQL.connectDatabase();
    }

    public Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DorisRooms.class.getClassLoader().getResource("META-INF/menus/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public void sceneLoginMenu() throws IOException{
        scene = new Scene(loadFXML("LoginMenu"));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.setResizable(false);
        scene.setFill(Color.TRANSPARENT);
        stage.show();
    }

    public void sceneMainMenu() throws IOException{
        scene = new Scene(loadFXML("MainMenu"));
        stage.setScene(scene);
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.show();
    }

    public void sceneManageEmployees() throws IOException{
        scene = new Scene(loadFXML("ManageEmployees"));
        stage.setScene(scene);
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.show();
    }
}
