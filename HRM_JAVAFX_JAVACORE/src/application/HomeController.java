package application;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class HomeController {
	@FXML
	private Label welcomeLabel;
	@FXML
	private TextField emailTextField;
	@FXML
	private TextField fullnameTextField;
	@FXML
	private TextField passwordTextField;
	@FXML
	private ToggleGroup genderGroup;
	@FXML
	private RadioButton maleRadioButton;
	@FXML
	private RadioButton femaleRadioButton;
	@FXML
	private DatePicker birthdayPicker;
	@FXML
	private Button addBtn;
	@FXML
	private Button updateBtn;
	@FXML
	private Button deleteBtn;
	@FXML
	private ChoiceBox<String> courseChoiceBox;
	@FXML
	private TableView<User> userListTableView;
	@FXML
	private TableColumn<User, Integer> idCol;
	@FXML
	private TableColumn<User, String> emailCol;
	@FXML
	private TableColumn<User, String> fullnameCol;
	@FXML
	private TableColumn<User, String> courseCol;

	private UserDAO userDAO;

	public void initialize() throws SQLException {
		userDAO = new UserDAO();

		idCol.setCellValueFactory(new PropertyValueFactory<User, Integer>("userID"));
		emailCol.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
		fullnameCol.setCellValueFactory(new PropertyValueFactory<User, String>("fullName"));
		courseCol.setCellValueFactory(new PropertyValueFactory<User, String>("course"));

		List<String> courses = Arrays.asList("Java", "Python", "C++", "Web Development");
		courseChoiceBox.getItems().addAll(courses);

		maleRadioButton.setSelected(true);
		femaleRadioButton.setSelected(false);

		welcomeLabel.setText("Xin chào " + UserSession.getInstance().getFullname());
		
		loadUserList();
	}

	public void loadUserList() throws SQLException {
		ObservableList<User> users = FXCollections.observableArrayList(userDAO.getAllUsers());
		userListTableView.setItems(users);
	}

	@FXML
	public void onRowClick() {
		User selectedUser = userListTableView.getSelectionModel().getSelectedItem();
		if (selectedUser != null) {
			emailTextField.setText(selectedUser.getEmail());
			fullnameTextField.setText(selectedUser.getFullName());
			courseChoiceBox.setValue(selectedUser.getCourse());
			passwordTextField.setText(selectedUser.getPassword());
			birthdayPicker.setValue(selectedUser.getBirthday());

			if (selectedUser.isGender()) {
				maleRadioButton.setSelected(true);
			} else {
				femaleRadioButton.setSelected(true);
			}
		}
	}

	@FXML
	public void onClickLogout() throws IOException {
		UserSession.getInstance().cleanUserSession();
		
		Stage loginState = (Stage)emailTextField.getScene().getWindow();
        loginState.close();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginForm.fxml"));
        Parent root = loader.load();
        
        Stage homeStage = new Stage();
        homeStage.setTitle("Đăng nhập CLB Tin học");
        homeStage.setScene(new Scene(root));
        homeStage.show();
	}

	@FXML
	public void onClickAddButton() throws SQLException {
		User user = null;
		String email = emailTextField.getText();
		String fullname = fullnameTextField.getText();
		String password = passwordTextField.getText();
		LocalDate birthday = birthdayPicker.getValue();
		boolean gender = maleRadioButton.isSelected();
		String course = courseChoiceBox.getValue();

		String validateMessage = validateForm(email, fullname, birthday, password, course);

		if (validateMessage != null) {
			showAlert(AlertType.ERROR, "Lỗi", validateMessage);
			return;
		}

		if (userDAO.getUserByEmail(email) != null) {
			showAlert(AlertType.ERROR, "Lỗi", "Email đã được sử dụng!");
			return;
		}

		user = new User(email, fullname, gender, birthday, course, password);
		boolean isAdded = userDAO.addUser(user);

		if (isAdded) {
			loadUserList();
			clearForm();
			showAlert(AlertType.INFORMATION, "Thành công", "Thêm khách hàng thành công!");
		} else {
			showAlert(AlertType.ERROR, "Lỗi", "Thêm khách hàng không thành công!");
		}
	}

	@FXML
	public void onClickUpdateButton() throws SQLException {
	    User selectedUser = userListTableView.getSelectionModel().getSelectedItem();

	    if (selectedUser == null) {
	        showAlert(AlertType.ERROR, "Lỗi", "Không tìm thấy người dùng");
	        return;
	    }

	    String newEmail = emailTextField.getText();
	    
	    if (!newEmail.equals(selectedUser.getEmail())) {
	        if (userDAO.getUserByEmail(newEmail) != null) {
	            showAlert(AlertType.ERROR, "Lỗi", "Email đã được sử dụng!");
	            return;
	        }
	    }

	    selectedUser.setEmail(newEmail);
	    selectedUser.setFullName(fullnameTextField.getText());
	    selectedUser.setPassword(passwordTextField.getText());
	    selectedUser.setBirthday(birthdayPicker.getValue());
	    selectedUser.setGender(maleRadioButton.isSelected());
	    selectedUser.setCourse(courseChoiceBox.getValue());

	    String validateMessage = validateForm(selectedUser.getEmail(), selectedUser.getFullName(),
	            selectedUser.getBirthday(), selectedUser.getPassword(), selectedUser.getCourse());

	    if (validateMessage != null) {
	        showAlert(AlertType.ERROR, "Lỗi", validateMessage);
	        return;
	    }

	    boolean isUpdated = userDAO.updateUser(selectedUser);

	    if (isUpdated) {
	        loadUserList();
	        clearForm();
	        showAlert(AlertType.INFORMATION, "Thành công", "Chỉnh sửa thông tin khách hàng thành công!");
	    } else {
	        showAlert(AlertType.ERROR, "Lỗi", "Chỉnh sửa thông tin khách hàng không thành công!");
	    }
	}

	@FXML
	public void onClickDeleteButton() throws SQLException {
		User selectedUser = userListTableView.getSelectionModel().getSelectedItem();
		boolean isDeleted = false;

		if (selectedUser == null) {
			showAlert(AlertType.ERROR, "Lỗi", "Không tìm thấy người dùng");
			return;
		}

		Optional<ButtonType> optional = showAlert(AlertType.CONFIRMATION, "Xác nhận",
				"Bạn có chắc chắn muốn xóa người dùng này không?");
		if (optional.get() == ButtonType.OK) {
			isDeleted = userDAO.deleteUser(selectedUser.getUserID());
		}

		if (isDeleted) {
			loadUserList();
			clearForm();
			showAlert(AlertType.INFORMATION, "Thành công", "Đã xóa người dùng!");
		} else {
			showAlert(AlertType.INFORMATION, "Lỗi", "Lỗi khi xóa người dùng!");
		}
	}

	public String validateForm(String email, String fullname, LocalDate birthday, String password, String course) {
		String error = null;

		if (email == null || email.isEmpty()) {
			error = "Vui lòng nhập email!";
		} else if (!isEmailValid(email)) {
			error = "Email không hợp lệ!";
		} else if (fullname == null || fullname.isEmpty()) {
			error = "Vui lòng nhập họ tên!";
		} else if (birthday == null) {
			error = "Vui lòng nhập ngày sinh!";
		} else if (birthday.isAfter(LocalDate.now())) {
			error = "Ngày sinh không hợp lệ!";
		} else if (password == null || password.isEmpty()) {
			error = "Vui lòng nhập mật khẩu";
		} else if (course == null || course.isEmpty()) {
			error = "Vui lòng chọn khóa học!";
		}

		return error;
	}

	public boolean isEmailValid(String email) {
		return email != null && email.contains("@");
	}

	public void clearForm() {
		emailTextField.clear();
		fullnameTextField.clear();
		courseChoiceBox.setValue(null);
		passwordTextField.clear();
		birthdayPicker.setValue(LocalDate.now());
		maleRadioButton.setSelected(true);
		femaleRadioButton.setSelected(false);
	}

	public Optional<ButtonType> showAlert(AlertType type, String title, String message) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);

		return alert.showAndWait();
	}
}
