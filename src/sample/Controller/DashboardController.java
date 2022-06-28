package sample.Controller;

import org.hibernate.annotations.FilterDef;
import sample.MainApplication;
import sample.User.Firms;
import sample.User.FirmsDAO;
import sample.User.UserDAO;
import sample.User.UserSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utils.Validation;

import javax.persistence.Table;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Label groupLabel, usernameLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField codeField;
    @FXML
    private TextField addressField;
    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn codeColumn;
    @FXML
    private TableColumn addressColumn;
    @FXML
    private TableView firmTableView;

    @FXML
    public void onLogOutButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(MainApplication.class.getResource("login-view.fxml"));
        Stage LoginStage = new Stage();
        LoginStage.setTitle("Prisijungimo langas");
        LoginStage.setScene(new Scene(root, 600, 400));
        LoginStage.show();
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
    @FXML
    public void onCreateButtonClick() {
        String nameField2 = nameField.getText();
        String codeField2 = codeField.getText();
        String addressField2 = addressField.getText();

        if(groupLabel.getText().equals("ADMINISTRATORIUS")){
            // Tikriname pagal Validacijas
            if (!Validation.isValidTitle(nameField2)) {
                statusLabel.setText("Neteisingai įvedėte pavadinimą");
            } else {

                Firms firms = new Firms(nameField2,codeField2,addressField2);
                FirmsDAO.create(firms);
                statusLabel.setText("Pavyko sukurti įrašą");
            }
        }
        else{
            statusLabel.setText("Pridėti įrašą gali tik administratorius");
        }

    }
    ObservableList<Firms> list = FXCollections.observableArrayList();
    @FXML
    public void onSearchButtonClick() {
        list.clear();
        String nameField2 = nameField.getText();

        List<Firms> firmsList = FirmsDAO.searchByName(nameField2);

        for (Firms firms : firmsList) {

            list.add(new Firms(firms.getId(),firms.getName(), firms.getCode(), firms.getAddress()));
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
            addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));


            firmTableView.setItems(list);

        }

    }
    @FXML
    public void onUpdateButtonClick() {
        String nameField2 = nameField.getText();
        String codeField2 = codeField.getText();
        String addressField2 = addressField.getText();
        if(groupLabel.getText().equals("ADMINISTRATORIUS")){
            if(idField.getText().isEmpty()){
                statusLabel.setText("Norint redaguoti įrašą reikia įvesti norimą id");
            }
            int id = Integer.parseInt(idField.getText());
            Firms firms = new Firms(id,nameField2,codeField2,addressField2);
            FirmsDAO.update(firms);
            statusLabel.setText("Pavyko paredaguoti įrašą");
        } else{
            // Vartotojas
            statusLabel.setText("Redaguoti įrašą gali tik administratorius");
        }

    }
    @FXML
    public void onToMenuButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(MainApplication.class.getResource("menu-view.fxml"));
        Stage LoginStage = new Stage();
        LoginStage.setTitle("Meniu langas");
        LoginStage.setScene(new Scene(root, 1280, 720));
        LoginStage.show();
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
    @FXML
    public void onDeleteButtonClick() {
        String idField2 = idField.getText();

        if(groupLabel.getText().equals("ADMINISTRATORIUS")) {
            if (!Validation.isValidTime(idField2)) {
                statusLabel.setText("Neteisingai įvestas ID");
            } else {
                int idField3 = Integer.parseInt(idField.getText());
                FirmsDAO.deleteById(idField3);
                statusLabel.setText("Pavyko sėkmingai ištrinti įrašą");
            }
        } else{
            // Vartotojas
            statusLabel.setText("Trinti įrašą gali tik administratorius.");
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Parodomas prisijunges vartotojas
        String username = UserSingleton.getInstance().getUserName();
        usernameLabel.setText(username);

        // Parodoma prisijungusio vartotojo role(grupe)
        String role = UserDAO.searchByUsername(username);
        groupLabel.setText(role);

    }

}
