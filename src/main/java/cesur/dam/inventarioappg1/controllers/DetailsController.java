package cesur.dam.inventarioappg1.controllers;

import cesur.dam.inventarioappg1.data.DataSourceFactory;
import cesur.dam.inventarioappg1.data.EquipoJdbcDAO;
import cesur.dam.inventarioappg1.models.Equipo;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DetailsController implements Initializable {

    @FXML
    private Label welcomeText;

    @FXML
    private Button btnCrear;

    @FXML
    private TableView<Equipo> table;

    @FXML
    private TableColumn<Equipo, String> cId;

    @FXML
    private TableColumn<Equipo, String> cName;

    @FXML
    private TableColumn<Equipo, String> cCategory;

    @FXML
    private TableColumn<Equipo, String> cAvailability;

    private EquipoJdbcDAO dao;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dao = new EquipoJdbcDAO(DataSourceFactory.getDataSource());

        cId.setCellValueFactory(row -> new SimpleStringProperty(String.valueOf(row.getValue().getId())));
        cName.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getNombre()));
        cCategory.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getCategoria()));
        cAvailability.setCellValueFactory(row -> new SimpleStringProperty(
                        row.getValue().getDisponible()
                                ? "Disponible"
                                : "No disponible"));
        cargarDatos();
    }

    private void cargarDatos() {
        table.setItems(FXCollections.observableArrayList(dao.findAll()));
    }

    @FXML
    public void crear(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cesur/dam/inventarioappg1/views/create-view.fxml"));

            Stage stage = new Stage();
            stage.setTitle("Crear equipo");
            stage.setScene(new Scene(loader.load()));

            stage.showAndWait();

            cargarDatos();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cesur/dam/inventarioappg1/views/login-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage =
                    (Stage) ((Button) event.getSource())
                            .getScene()
                            .getWindow();

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}