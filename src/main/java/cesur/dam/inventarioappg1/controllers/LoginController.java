package cesur.dam.inventarioappg1.controllers;

import cesur.dam.inventarioappg1.AppG1;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @javafx.fxml.FXML
    private Button btnLogin;

    @FXML
    public void login(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(AppG1.class.getResource("views/details-view.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) ((Button) event.getSource())
                .getScene()
                .getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
