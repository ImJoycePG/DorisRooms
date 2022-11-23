package net.imjoycepg.mc.controllers;

import com.dustinredmond.fxalert.FXAlert;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import net.imjoycepg.mc.DorisRooms;
import net.imjoycepg.mc.utils.entity.RoomsEntity;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RoomsMenu implements Initializable {

    @FXML
    private TableView<RoomsEntity> table_model;
    @FXML
    private TableColumn<RoomsEntity, String> table_column_idRooms, table_column_desc, table_column_price, table_column_obs, table_column_idtype;
    @FXML
    private TextField searchText;
    private final ObservableList<RoomsEntity> inventoryObservableList2 = DorisRooms.getInstance().getRoomsTable().showRooms();
    private final FilteredList<RoomsEntity> filteredData = new FilteredList<>(inventoryObservableList2, p->true);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateLanguage();
        tableModel();

    }

    private void updateLanguage(){
        table_column_idRooms.setText(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsMenu_TableIDRoomsText").getAsString());
        table_column_desc.setText(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsMenu_TableDescRoomsText").getAsString());
        table_column_price.setText(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsMenu_TablePriceRoomsText").getAsString());
        table_column_obs.setText(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsMenu_TableObsRoomsText").getAsString());
        table_column_idtype.setText(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsMenu_TableIDTypeText").getAsString());
    }

    private void tableModel(){
        table_column_idRooms.setCellValueFactory(new PropertyValueFactory<>("idRooms"));
        table_column_desc.setCellValueFactory(new PropertyValueFactory<>("descRooms"));
        table_column_price.setCellValueFactory(new PropertyValueFactory<>("priceRooms"));
        table_column_obs.setCellValueFactory(new PropertyValueFactory<>("obsRooms"));
        table_column_idtype.setCellValueFactory(new PropertyValueFactory<>("idTypeRooms"));

        ObservableList<RoomsEntity> loginObservableList = DorisRooms.getInstance().getRoomsTable().showRooms();
        table_model.setItems(loginObservableList);
        table_model.refresh();
    }


    @FXML
    public void openNewRoom(){
        try {
            DorisRooms.getInstance().sceneManageRoomsNewMenu();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void openTypeMenu(){
        try {
            DorisRooms.getInstance().sceneManageRoomsTypeMenu();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void openEditMenu(){
        if(table_model.getSelectionModel().getSelectedItem() == null){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("SelectTableViewTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("SelectTableViewDescription").getAsString());
            return;
        }
        String idRooms = table_model.getSelectionModel().getSelectedItem().getIdRooms();
        try {
            if (DorisRooms.getInstance().getRoomsTable().findRooms(idRooms) != null) {
                DorisRooms.getInstance().getRoomsTemp().setIdRooms(idRooms);
                DorisRooms.getInstance().getRoomsTemp().setDescRooms(table_model.getSelectionModel().getSelectedItem().getDescRooms());
                DorisRooms.getInstance().getRoomsTemp().setPriceRooms(table_model.getSelectionModel().getSelectedItem().getPriceRooms());
                DorisRooms.getInstance().getRoomsTemp().setObsRooms(table_model.getSelectionModel().getSelectedItem().getObsRooms());
                DorisRooms.getInstance().getRoomsTemp().setIdTypeRooms(table_model.getSelectionModel().getSelectedItem().getIdTypeRooms());

                DorisRooms.getInstance().sceneManageRoomsEditMenu();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    public void deleteRooms(){
        if(table_model.getSelectionModel().getSelectedItem() == null){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("SelectTableViewTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("SelectTableViewDescription").getAsString());
            return;
        }
        String idRooms = table_model.getSelectionModel().getSelectedItem().getIdRooms();
        DorisRooms.getInstance().getRoomsTable().deleteRooms(idRooms);
        tableModel();
    }

    @FXML
    public void openRentalMenu(){
        try {
            DorisRooms.getInstance().sceneManageRoomsRentalMenu();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void openMethodPay(){
        try {
            DorisRooms.getInstance().sceneManageMethodMenu();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void openReservationMenu(){
        try {
            DorisRooms.getInstance().sceneManageRoomsReservationMenu();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void backMenu(){
        try {
            DorisRooms.getInstance().sceneMainMenu();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void searchForName(){
        String nameProduct = searchText.getText();
        DorisRooms.getInstance().getNewProductTable().findProductName(nameProduct);
        searchText.textProperty().addListener((observable, oldValue, newValue) ->{
            filteredData.setPredicate(firstNameClient -> {
                if(newValue == null || newValue.isEmpty()) return true;

                String lowerCaseFilter = newValue.toLowerCase();

                if(String.valueOf(firstNameClient.getIdRooms()).toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if(String.valueOf(firstNameClient.getDescRooms()).toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                return false;
            });
        });

        SortedList<RoomsEntity> sortedList = new SortedList<>(filteredData);

        sortedList.comparatorProperty().bind(table_model.comparatorProperty());
        table_model.setItems(sortedList);
    }
}
