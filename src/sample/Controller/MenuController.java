package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.MainApplication;
import sample.User.Firms;
import sample.User.FirmsDAO;
import sample.User.MenuDAO;

import javax.xml.soap.Text;
import java.awt.*;
import java.io.IOException;

public class MenuController {
    @FXML
    private TextField idTextField;
    @FXML
    private TextField groupLabel;
    @FXML
    private TextField statusLabel;
    @FXML
    public void onBackButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(MainApplication.class.getResource("dashboard-view.fxml"));
        Stage LoginStage = new Stage();
        LoginStage.setTitle("Maisto užsakymas");
        LoginStage.setScene(new Scene(root, 1280, 720));
        LoginStage.show();
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
    @FXML
    public void onDeleteButtonClick() {
        String idField2 = idTextField.getText();

        if(groupLabel.getText().equals("ADMINISTRATORIUS")) {
            if (!utils.Validation.isValidTime(idField2)) {
                statusLabel.setText("Neteisingai įvestas ID");
            } else {
                int idField3 = Integer.parseInt(idTextField.getText());
                MenuDAO.deleteById(idField3);
                statusLabel.setText("Pavyko sėkmingai ištrinti įrašą");
            }
        } else{
            // Vartotojas
            statusLabel.setText("Trinti įrašą gali tik administratorius.");
        }
    }

}
