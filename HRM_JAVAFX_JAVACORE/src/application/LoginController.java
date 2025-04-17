package application;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

	private UserDAO userDAO;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Label errorLabel;
    
    @FXML
    public void initialize() throws SQLException {
    	userDAO = new UserDAO();
    	errorLabel.setText("");
    }

    @FXML
    private void onClickLoginButton() throws SQLException, IOException {
    	User user;
        String email = emailTextField.getText().trim();
        String password = passwordTextField.getText().trim();

        if(email.isEmpty() || password.isEmpty()) {
        	errorLabel.setText("Email và mật khẩu không được để trống!");
        	return;
        }
        
        user = userDAO.getUserByEmail(email);
        
        if (user == null) {
        	errorLabel.setText("Tài khoản không tồn tại!");
        	return;
        }
        
        if (!user.getPassword().equals(password)) {
        	errorLabel.setText("Mật khẩu không chính xác!");
        	return;
        }
        
        errorLabel.setText("");
        
        UserSession.createInstance(email, user.getFullName());
        
        goHomeScreen();
    }

    @FXML
    private void onClickRegisterLink() throws IOException {
        Stage registerState = (Stage)emailTextField.getScene().getWindow();
        registerState.close();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterForm.fxml"));
        Parent root = loader.load();
        
        Stage homeStage = new Stage();
        homeStage.setTitle("Đăng ký khóa học");
        homeStage.setScene(new Scene(root));
        homeStage.show();
    }
    
    public void goHomeScreen() throws IOException {
    	Stage loginState = (Stage)emailTextField.getScene().getWindow();
        loginState.close();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeScreen.fxml"));
        Parent root = loader.load();
        
        Stage homeStage = new Stage();
        homeStage.setTitle("CLB Tin học");
        homeStage.setScene(new Scene(root));
        homeStage.show();
    }
}
