package it.polimi.ingsw.sagrada.gui;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginGuiView {
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
}
