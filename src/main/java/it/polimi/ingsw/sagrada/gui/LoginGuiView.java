package it.polimi.ingsw.sagrada.gui;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginGuiView extends Application {
    @FXML
    private RadioButton socketRadioButton;
    @FXML
    private RadioButton rmiRadiobutton;
    @FXML
    private Button loginButton;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private Label errorText;

    private Stage window;

    public void addLoginButtonListener(ChangeListener listener) {
        loginButton.armedProperty().addListener(listener);
    }

    public void setErrorText(String message) {
        errorText.setText(message);
    }

    public boolean isCredentialCorrect() {
        return (usernameField.getText().length()!=0 && passwordField.getText().length()!=0);
    }

    public String getSelectedCommunication() {
        if(socketRadioButton.isSelected()) return "Socket";
        else return "RMI";
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return passwordField.getText();
    }

    public void setRadioGroup() {
        ToggleGroup group = new ToggleGroup();
        socketRadioButton.setToggleGroup(group);
        socketRadioButton.setSelected(true);
        rmiRadiobutton.setToggleGroup(group);
    }

    public Stage getWindow() {
        return (Stage)loginButton.getScene().getWindow();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.window = primaryStage;

        //Login GUI
        FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/templates/LoginGui.fxml"));
        Parent root = loaderLogin.load();
        LoginGuiView loginGuiView = loaderLogin.getController();
        new LoginGuiController(loginGuiView, window);
        loginGuiView.setRadioGroup();

        window.setTitle("SagradaClient");
        window.setResizable(false);
        window.setScene(new Scene(root, 590, 776));
        window.show();
    }
}
