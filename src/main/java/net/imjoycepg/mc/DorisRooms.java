package net.imjoycepg.mc;

import com.dustinredmond.fxalert.FXAlert;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
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
import net.imjoycepg.mc.utils.*;
import net.imjoycepg.mc.utils.configs.Database;
import net.imjoycepg.mc.utils.entity.LoginEntity;
import net.imjoycepg.mc.utils.entity.temporal.OrderProductTemp;
import net.imjoycepg.mc.utils.entity.temporal.ProductTemp;
import net.imjoycepg.mc.utils.entity.temporal.RoomsTemp;
import net.imjoycepg.mc.utils.tables.*;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
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
    private Drive drive;
    private final Image AlertImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/META-INF/images/DorisRoomsAlerts.png")));
    private final Utilities utilities = new Utilities();

    private final LoginEntity loginEntity = new LoginEntity();
    private final LoginTable loginTable = new LoginTable();
    private final EmployeeTable employeeTable = new EmployeeTable();
    private final ProvedTable provedTable = new ProvedTable();
    private final ClientTable clientTable = new ClientTable();
    private final CategoryProdTable categoryProdTable = new CategoryProdTable();
    private final NewProductTable newProductTable = new NewProductTable();
    private final OrderTable orderTable = new OrderTable();
    private final MethodTable methodTable = new MethodTable();
    private final RoomsReservationTable roomsReservationTable = new RoomsReservationTable();
    private final RoomsRentalTable roomsRentalTable = new RoomsRentalTable();
    private final RoomsTypeTable roomsTypeTable = new RoomsTypeTable();
    private final RoomsTable roomsTable = new RoomsTable();

    private final ProductTemp productTemp = new ProductTemp();
    private final OrderProductTemp orderProductTemp = new OrderProductTemp();
    private final RoomsTemp roomsTemp = new RoomsTemp();
    private final ReportUtil reportUtil = new ReportUtil();

    private final JsonFactory JsonFactory = GsonFactory.getDefaultInstance();
    private final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_FILE);

    protected Credential getGoogleDrive(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        InputStream in = DorisRooms.class.getResourceAsStream("/credentials.json");
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + "credentials.json");
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JsonFactory, new InputStreamReader(in));
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JsonFactory, clientSecrets,SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new File("tokens")))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public void startApp(Stage stage) throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        this.stage = stage;
        try{
            database = JsonConfig.loadConfig(Database.class);
        }catch (InstantiationException | IllegalAccessException e){
            FXAlert.setGlobalTitleBarIcon(AlertImage);
            FXAlert.showException(e, "");
            return;
        }
        drive = new Drive.Builder(HTTP_TRANSPORT, JsonFactory, getGoogleDrive(HTTP_TRANSPORT))
                .setApplicationName("DorisRoomsApp")
                .build();

        mySQL.connectDatabase();
    }

    public Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DorisRooms.class.getClassLoader().getResource("META-INF/menus/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public void sceneLoginMenu() throws IOException {
        scene = new Scene(loadFXML("LoginMenu"));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.setResizable(false);
        scene.setFill(Color.TRANSPARENT);
        stage.show();
    }

    public void sceneLoginAgain() throws IOException{
        scene = new Scene(loadFXML("LoginMenu"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
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

    public void sceneManageAdministrator() throws IOException{
        scene = new Scene(loadFXML("AdministratorMenu"));
        stage.setScene(scene);
        stage.centerOnScreen();
        scene.setFill(Color.TRANSPARENT);
        stage.show();
    }

    public void sceneManageEmployee() throws IOException{
        scene = new Scene(loadFXML("EmployeeMenu"));
        stage.setScene(scene);
        stage.centerOnScreen();
        scene.setFill(Color.TRANSPARENT);
        stage.show();
    }

    public void sceneManageProved() throws IOException{
        scene = new Scene(loadFXML("ProvedMenu"));
        stage.setScene(scene);
        stage.centerOnScreen();
        scene.setFill(Color.TRANSPARENT);
        stage.show();
    }

    public void sceneManageClient() throws IOException{
        scene = new Scene(loadFXML("ClientMenu"));
        stage.setScene(scene);
        stage.centerOnScreen();
        scene.setFill(Color.TRANSPARENT);
        stage.show();
    }

    public void sceneManageProduct() throws IOException{
        scene = new Scene(loadFXML("ProductMenu"));
        stage.setScene(scene);
        stage.centerOnScreen();
        scene.setFill(Color.TRANSPARENT);
        stage.show();
    }

    public void sceneManageRooms() throws IOException{
        scene = new Scene(loadFXML("RoomsMenu"));
        stage.setScene(scene);
        stage.centerOnScreen();
        scene.setFill(Color.TRANSPARENT);
        stage.show();
    }

    public void sceneManageCategoryMenu() throws IOException{
        scene = new Scene(loadFXML("CategoryMenu"));
        stage.setScene(scene);
        stage.centerOnScreen();
        scene.setFill(Color.TRANSPARENT);
        stage.show();
    }

    public void sceneManageNewProduct() throws IOException{
        scene = new Scene(loadFXML("NewProductMenu"));
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void sceneManageEditProduct() throws IOException{
        scene = new Scene(loadFXML("EditProductMenu"));
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void sceneManageOrderMenu() throws IOException{
        scene = new Scene(loadFXML("OrderMenu"));
        stage.setScene(scene);
        stage.centerOnScreen();
        scene.setFill(Color.TRANSPARENT);
        stage.show();
    }

    public void sceneManageOrderTicketMenu() throws IOException{
        scene = new Scene(loadFXML("TicketMenu"));
        stage.setScene(scene);
        stage.centerOnScreen();
        scene.setFill(Color.TRANSPARENT);
        stage.show();
    }

    public void sceneManageRoomsNewMenu() throws IOException{
        scene = new Scene(loadFXML("RoomsNewMenu"));
        stage.setScene(scene);
        stage.centerOnScreen();
        scene.setFill(Color.TRANSPARENT);
        stage.show();
    }

    public void sceneManageRoomsTypeMenu() throws IOException{
        scene = new Scene(loadFXML("RoomsTypeMenu"));
        stage.setScene(scene);
        stage.centerOnScreen();
        scene.setFill(Color.TRANSPARENT);
        stage.show();
    }

    public void sceneManageRoomsEditMenu() throws IOException{
        scene = new Scene(loadFXML("RoomsEditMenu"));
        stage.setScene(scene);
        stage.centerOnScreen();
        scene.setFill(Color.TRANSPARENT);
        stage.show();
    }
    public void sceneManageRoomsRentalMenu() throws IOException{
        scene = new Scene(loadFXML("RoomsRentalMenu"));
        stage.setScene(scene);
        stage.centerOnScreen();
        scene.setFill(Color.TRANSPARENT);
        stage.show();
    }
    public void sceneManageRoomsReservationMenu() throws IOException{
        scene = new Scene(loadFXML("RoomsReservationsMenu"));
        stage.setScene(scene);
        stage.centerOnScreen();
        scene.setFill(Color.TRANSPARENT);
        stage.show();
    }

    public void sceneManageMethodMenu() throws IOException{
        scene = new Scene(loadFXML("MethodMenu"));
        stage.setScene(scene);
        stage.centerOnScreen();
        scene.setFill(Color.TRANSPARENT);
        stage.show();
    }
}
