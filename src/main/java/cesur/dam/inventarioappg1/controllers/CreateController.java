package cesur.dam.inventarioappg1.controllers;

import cesur.dam.inventarioappg1.data.DataSourceFactory;
import cesur.dam.inventarioappg1.data.EquipoJdbcDAO;
import cesur.dam.inventarioappg1.models.Equipo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateController implements Initializable {

    @FXML
    private CheckBox chkAvailable;

    @FXML
    private TextField txtName;

    @FXML
    private Spinner<Integer> spinnerId;

    @FXML
    private ChoiceBox<String> choiceCategory;

    private EquipoJdbcDAO dao;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dao = new EquipoJdbcDAO(DataSourceFactory.getDataSource());

        spinnerId.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(
                        0, 1000, 0, 1
                )
        );

        choiceCategory.getItems().addAll(
                "Keyboard",
                "Laptop",
                "Gamepad",
                "Monitor",
                "Computer",
                "Printer"
        );

        choiceCategory.getSelectionModel().selectFirst();
    }

    @FXML
    public void guardar(ActionEvent actionEvent) {

        if (txtName.getText().isBlank()) {
            return;
        }

        Equipo equipo = new Equipo();

        equipo.setNombre(txtName.getText());
        equipo.setCategoria(choiceCategory.getValue());
        equipo.setDisponible(chkAvailable.isSelected());

        dao.save(equipo);

        Stage stage = (Stage) txtName.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void cancelar(ActionEvent actionEvent) {

        Stage stage = (Stage) txtName.getScene().getWindow();
        stage.close();
    }
}