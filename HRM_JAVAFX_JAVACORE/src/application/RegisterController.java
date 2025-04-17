package application;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class RegisterController {
	private UserDAO userDAO;
	@FXML
	private TextField emailTextField;
	@FXML
	private TextField fullnameTextField;
	@FXML
	private TextField passwordTextField;
	@FXML
	private DatePicker birthdayPicker;
	@FXML
	private ChoiceBox<String> courseChoiceBox;
	@FXML
	private Label errorLabel;
	@FXML
	private ToggleGroup genderGroup;
	@FXML
	private RadioButton maleRadioButton;
	@FXML
	private RadioButton femaleRadioButton;
	
	@FXML
	public void initialize() throws SQLException {
		List<String> courses = Arrays.asList("Java", "Python", "C++", "Web Development");
		
		courseChoiceBox.getItems().addAll(courses);
		courseChoiceBox.getSelectionModel().selectFirst();
		maleRadioButton.setSelected(true);
		errorLabel.setText("");
		
		userDAO = new UserDAO();
	}
	
	@FXML
	public void onClickRegisterButton() throws SQLException {
		User user = null;
		String email = emailTextField.getText();
		String fullname = fullnameTextField.getText();
		String password = passwordTextField.getText();
		LocalDate birthday = birthdayPicker.getValue();
		boolean gender = maleRadioButton.isSelected();
		String course = courseChoiceBox.getValue();
		
		String validateMessage = validateForm(email, fullname, birthday, password, course);
		
		if(validateMessage != null ) {
			errorLabel.setText(validateMessage);
			return;
		}
		
		if(userDAO.getUserByEmail(email) != null) {
			errorLabel.setText("Email đã được sử dụng!");
			return;
		}
		
		user = new User(email, fullname, gender, birthday, course, password);
		boolean isAdded = userDAO.addUser(user);
		
		if(isAdded) {
			errorLabel.setText("Đăng ký tài khoản thành công!");
		} else {
			errorLabel.setText("Đăng ký không thành công!");
		}
	}
	
	public String validateForm(String email, String fullname, LocalDate birthday, String password, String course) {
		String error = null;
		
		if(email == null || email.isEmpty()) {
			error = "Vui lòng nhập email!";
		} else if(!isEmailValid(email)) {
			error = "Email không hợp lệ!";
		} else if(fullname == null || fullname.isEmpty()) {
			error = "Vui lòng nhập họ tên!";
		} else if(birthday == null) {
			error = "Vui lòng nhập ngày sinh!";
		} else if (birthday.isAfter(LocalDate.now())) {
			error = "Ngày sinh không hợp lệ!";
		} else if(password == null || password.isEmpty()) {
			error = "Vui lòng nhập mật khẩu";
		} else if(course == null || course.isEmpty()) {
			error = "Vui lòng chọn khóa học!";
		}
		
		return error;
	}
	
	public boolean isEmailValid(String email) {
		return email != null && email.contains("@");
	}
	
	@FXML
	public void onClickLoginButton() throws IOException {
		Stage loginState = (Stage)emailTextField.getScene().getWindow();
        loginState.close();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginForm.fxml"));
        Parent root = loader.load();
        
        Stage homeStage = new Stage();
        homeStage.setTitle("Đăng nhập CLB Tin học");
        homeStage.setScene(new Scene(root));
        homeStage.show();
	}
}
