package application;

import java.sql.SQLException;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

public class HomeController {
	// === User ===
	@FXML
	private TextField fullnameTextField;
	@FXML
	private TextField addressTextField;
	@FXML
	private TextField classTextField;
	@FXML
	private TextField passwordTextField;
	@FXML
	private ToggleGroup userTypeGroup;
	@FXML
	private RadioButton studentRadioButton;
	@FXML
	private RadioButton lecturerRadioButton;
	@FXML
	private TableView<User> userListTableView;
	@FXML
	private TableColumn<User, Integer> user_code_col;
	@FXML
	private TableColumn<User, String> fullname_col;
	@FXML
	private TableColumn<User, String> address_col;
	@FXML
	private TableColumn<User, String> class_col;

	private UserDAO userDAO;

	// === Subject ===
	@FXML
	private TextField subjectNameTextField;
	@FXML
	private TextField creditTextField;
	@FXML
	private TextField attandenceMarkTextField;
	@FXML
	private TextField examMark1TextField;
	@FXML
	private TextField examMark2TextField;
	@FXML
	private TextField examMark3TextField;
	@FXML
	private TextField finalMarkTextField;
	@FXML
	private TableView<Subject> subjectListTableView;
	@FXML
	private TableColumn<Subject, Integer> subject_code_col;
	@FXML
	private TableColumn<Subject, String> subject_name_col;
	@FXML
	private TableColumn<Subject, Integer> credit_col;
	@FXML
	private TableColumn<Subject, Float> attendance_exam_mark_col;
	@FXML
	private TableColumn<Subject, Float> exam_mark_1_col;
	@FXML
	private TableColumn<Subject, Float> exam_mark_2_col;
	@FXML
	private TableColumn<Subject, Float> exam_mark_3_col;
	@FXML
	private TableColumn<Subject, Float> final_exam_mark_col;

	private SubjectDAO subjectDAO;

	// === USER SUBJECT ===
	@FXML
	private TextField managerUserCodeTextField;
	@FXML
	private TextField managerSubjectCodeTextField;
	@FXML
	private TextField managerAttandenceMarkTextField;
	@FXML
	private TextField managerExamMark1TextField;
	@FXML
	private TextField managerExamMark2TextField;
	@FXML
	private TextField managerExamMark3TextField;
	@FXML
	private TextField managerFinalMarkTextField;
	@FXML
	private TableView<UserSubject> managerListTableView;
	@FXML
	private TableColumn<UserSubject, Integer> manager_user_code_col;
	@FXML
	private TableColumn<UserSubject, Integer> manager_subject_code_col;
	@FXML
	private TableColumn<UserSubject, Float> manager_attendance_mark_col;
	@FXML
	private TableColumn<UserSubject, Float> manager_exam_mark_1_col;
	@FXML
	private TableColumn<UserSubject, Float> manager_exam_mark_2_col;
	@FXML
	private TableColumn<UserSubject, Float> manager_exam_mark_3_col;
	@FXML
	private TableColumn<UserSubject, Float> manager_final_exam_mark_col;

	private UserSubjectDAO userSubjectDAO;

	public void initialize() throws SQLException {
		userDAO = new UserDAO();
		subjectDAO = new SubjectDAO();

		user_code_col.setCellValueFactory(new PropertyValueFactory<User, Integer>("userCode"));
		fullname_col.setCellValueFactory(new PropertyValueFactory<User, String>("fullname"));
		address_col.setCellValueFactory(new PropertyValueFactory<User, String>("address"));
		class_col.setCellValueFactory(new PropertyValueFactory<User, String>("className"));

		subject_code_col.setCellValueFactory(new PropertyValueFactory<Subject, Integer>("subjectCode"));
		subject_name_col.setCellValueFactory(new PropertyValueFactory<Subject, String>("subjectName"));
		credit_col.setCellValueFactory(new PropertyValueFactory<Subject, Integer>("credit"));
		attendance_exam_mark_col.setCellValueFactory(new PropertyValueFactory<Subject, Float>("attendanceExamMark"));
		exam_mark_1_col.setCellValueFactory(new PropertyValueFactory<Subject, Float>("examMark1"));
		exam_mark_2_col.setCellValueFactory(new PropertyValueFactory<Subject, Float>("examMark2"));
		exam_mark_3_col.setCellValueFactory(new PropertyValueFactory<Subject, Float>("examMark3"));
		final_exam_mark_col.setCellValueFactory(new PropertyValueFactory<Subject, Float>("finalExamMark"));

		manager_user_code_col.setCellValueFactory(new PropertyValueFactory<UserSubject, Integer>("userCode"));
		manager_subject_code_col.setCellValueFactory(new PropertyValueFactory<UserSubject, Integer>("subjectCode"));
		manager_attendance_mark_col
				.setCellValueFactory(new PropertyValueFactory<UserSubject, Float>("attendanceExamMark"));
		manager_exam_mark_1_col.setCellValueFactory(new PropertyValueFactory<UserSubject, Float>("examMark1"));
		manager_exam_mark_2_col.setCellValueFactory(new PropertyValueFactory<UserSubject, Float>("examMark2"));
		manager_exam_mark_3_col.setCellValueFactory(new PropertyValueFactory<UserSubject, Float>("examMark3"));
		manager_final_exam_mark_col.setCellValueFactory(new PropertyValueFactory<UserSubject, Float>("finalExamMark"));

		studentRadioButton.setSelected(true);
		lecturerRadioButton.setSelected(false);

		loadUserList();
		loadSubjectList();
		loadUserSubjectList();
	}

	public Optional<ButtonType> showAlert(AlertType type, String title, String message) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);

		return alert.showAndWait();
	}

	// ======================= USER ==============================================
	public void loadUserList() throws SQLException {
		ObservableList<User> users = FXCollections.observableArrayList(userDAO.getAllUsers());
		userListTableView.setItems(users);
	}

	@FXML
	public void onRowClick() {
		User selectedUser = userListTableView.getSelectionModel().getSelectedItem();
		if (selectedUser != null) {
			fullnameTextField.setText(selectedUser.getFullname());
			addressTextField.setText(selectedUser.getAddress());
			classTextField.setText(selectedUser.getClassName());
			passwordTextField.setText(selectedUser.getPassword());

			if (selectedUser.isUserType()) {
				studentRadioButton.setSelected(true);
			} else {
				lecturerRadioButton.setSelected(true);
			}
		}
	}

	@FXML
	public void onClickAddButton() throws SQLException {
		User user = null;
		String fullname = fullnameTextField.getText();
		String address = addressTextField.getText();
		String className = classTextField.getText();
		String password = passwordTextField.getText();
		boolean userType = studentRadioButton.isSelected();

		String validateMessage = validateForm(fullname, address, className, password);

		if (validateMessage != null) {
			showAlert(AlertType.ERROR, "Lỗi", validateMessage);
			return;
		}

		user = new User(fullname, address, className, password, userType);
		boolean isAdded = userDAO.addUser(user);

		if (isAdded) {
			loadUserList();
			clearForm();
			showAlert(AlertType.INFORMATION, "Thành công", "Thêm người dùng thành công!");
		} else {
			showAlert(AlertType.ERROR, "Lỗi", "Thêm người dùng không thành công!");
		}
	}

	@FXML
	public void onClickUpdateButton() throws SQLException {
		User selectedUser = userListTableView.getSelectionModel().getSelectedItem();

		if (selectedUser == null) {
			showAlert(AlertType.ERROR, "Lỗi", "Không tìm thấy người dùng");
			return;
		}
		selectedUser.setFullname(fullnameTextField.getText());
		selectedUser.setAddress(addressTextField.getText());
		selectedUser.setClassName(classTextField.getText());
		selectedUser.setPassword(passwordTextField.getText());
		selectedUser.setUserType(studentRadioButton.isSelected());

		String validateMessage = validateForm(fullnameTextField.getText(), addressTextField.getText(),
				classTextField.getText(), passwordTextField.getText());

		if (validateMessage != null) {
			showAlert(AlertType.ERROR, "Lỗi", validateMessage);
			return;
		}

		boolean isUpdated = userDAO.updateUser(selectedUser);

		if (isUpdated) {
			loadUserList();
			clearForm();
			showAlert(AlertType.INFORMATION, "Thành công", "Chỉnh sửa thông tin người dùng thành công!");
		} else {
			showAlert(AlertType.ERROR, "Lỗi", "Chỉnh sửa thông tin người dùng không thành công!");
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
			isDeleted = userDAO.deleteUser(selectedUser.getUserCode());
		}

		if (isDeleted) {
			loadUserList();
			clearForm();
			showAlert(AlertType.INFORMATION, "Thành công", "Đã xóa người dùng!");
		} else {
			showAlert(AlertType.INFORMATION, "Lỗi", "Lỗi khi xóa người dùng!");
		}
	}

	public String validateForm(String fullname, String address, String className, String password) {
		String error = null;

		if (fullname == null || fullname.isEmpty()) {
			error = "Vui lòng nhập họ và tên!";
		} else if (address == null || address.isEmpty()) {
			error = "Vui lòng nhập địa chỉ!";
		} else if (className == null || className.isEmpty()) {
			error = "Vui lòng nhập tên lớp học!";
		} else if (password == null || password.isEmpty()) {
			error = "Vui lòng nhập mật khẩu!";
		}

		return error;
	}

	public boolean isEmailValid(String email) {
		return email != null && email.contains("@");
	}

	public void clearForm() {
		fullnameTextField.clear();
		addressTextField.clear();
		classTextField.clear();
		passwordTextField.clear();
		studentRadioButton.setSelected(true);
		lecturerRadioButton.setSelected(false);
	}

	// ================================ SUBJECT
	// ========================================
	public void loadSubjectList() throws SQLException {
		ObservableList<Subject> subjects = FXCollections.observableArrayList(subjectDAO.getAllSubject());
		subjectListTableView.setItems(subjects);
	}

	@FXML
	public void onRowSubjectTableClick() {
		Subject selectedSubject = subjectListTableView.getSelectionModel().getSelectedItem();
		if (selectedSubject != null) {
			subjectNameTextField.setText(selectedSubject.getSubjectName());
			creditTextField.setText(Integer.toString(selectedSubject.getCredit()));
			attandenceMarkTextField.setText(Float.toString(selectedSubject.getAttendanceExamMark()));
			examMark1TextField.setText(Float.toString(selectedSubject.getExamMark1()));
			examMark2TextField.setText(Float.toString(selectedSubject.getExamMark2()));
			examMark3TextField.setText(Float.toString(selectedSubject.getExamMark3()));
			finalMarkTextField.setText(Float.toString(selectedSubject.getFinalExamMark()));
		}
	}

	@FXML
	public void onClickAddSubjectButton() throws SQLException {
		String subjectName = subjectNameTextField.getText();
		String creditStr = creditTextField.getText();
		String attStr = attandenceMarkTextField.getText();
		String ex1Str = examMark1TextField.getText();
		String ex2Str = examMark2TextField.getText();
		String ex3Str = examMark3TextField.getText();
		String finalStr = finalMarkTextField.getText();

		String validateMessage = validateSubjectForm(subjectName, creditStr, attStr, ex1Str, ex2Str, ex3Str, finalStr);

		if (validateMessage != null) {
			showAlert(AlertType.ERROR, "Lỗi", validateMessage);
			return;
		}

		int credit = Integer.parseInt(creditStr);
		float attendance = Float.parseFloat(attStr);
		float exam1 = Float.parseFloat(ex1Str);
		float exam2 = Float.parseFloat(ex2Str);
		float exam3 = Float.parseFloat(ex3Str);
		float finalMark = Float.parseFloat(finalStr);

		Subject subject = new Subject(subjectName, credit, attendance, exam1, exam2, exam3, finalMark);
		boolean isAdded = subjectDAO.addSubject(subject);

		if (isAdded) {
			loadSubjectList();
			clearSubjectForm();
			showAlert(AlertType.INFORMATION, "Thành công", "Thêm môn học thành công!");
		} else {
			showAlert(AlertType.ERROR, "Lỗi", "Không thể thêm môn học!");
		}
	}

	@FXML
	public void onClickUpdateSubjectButton() throws SQLException {
		Subject selected = subjectListTableView.getSelectionModel().getSelectedItem();

		if (selected == null) {
			showAlert(AlertType.ERROR, "Lỗi", "Vui lòng chọn môn học cần sửa.");
			return;
		}

		String subjectName = subjectNameTextField.getText();
		String creditStr = creditTextField.getText();
		String attStr = attandenceMarkTextField.getText();
		String ex1Str = examMark1TextField.getText();
		String ex2Str = examMark2TextField.getText();
		String ex3Str = examMark3TextField.getText();
		String finalStr = finalMarkTextField.getText();

		String validateMessage = validateSubjectForm(subjectName, creditStr, attStr, ex1Str, ex2Str, ex3Str, finalStr);

		if (validateMessage != null) {
			showAlert(AlertType.ERROR, "Lỗi", validateMessage);
			return;
		}

		selected.setSubjectName(subjectName);
		selected.setCredit(Integer.parseInt(creditStr));
		selected.setAttendanceExamMark(Float.parseFloat(attStr));
		selected.setExamMark1(Float.parseFloat(ex1Str));
		selected.setExamMark2(Float.parseFloat(ex2Str));
		selected.setExamMark3(Float.parseFloat(ex3Str));
		selected.setFinalExamMark(Float.parseFloat(finalStr));

		boolean isUpdated = subjectDAO.updateSubject(selected);

		if (isUpdated) {
			loadSubjectList();
			clearSubjectForm();
			showAlert(AlertType.INFORMATION, "Thành công", "Cập nhật môn học thành công!");
		} else {
			showAlert(AlertType.ERROR, "Lỗi", "Không thể cập nhật môn học!");
		}
	}

	@FXML
	public void onClickDeleteSubjectButton() throws SQLException {
		Subject selected = subjectListTableView.getSelectionModel().getSelectedItem();

		if (selected == null) {
			showAlert(AlertType.ERROR, "Lỗi", "Vui lòng chọn môn học cần xóa.");
			return;
		}

		Optional<ButtonType> result = showAlert(AlertType.CONFIRMATION, "Xác nhận",
				"Bạn có chắc muốn xóa môn học này không?");
		if (result.get() == ButtonType.OK) {
			boolean isDeleted = subjectDAO.deleteSubject(selected.getSubjectCode());

			if (isDeleted) {
				loadSubjectList();
				clearSubjectForm();
				showAlert(AlertType.INFORMATION, "Thành công", "Xóa môn học thành công!");
			} else {
				showAlert(AlertType.ERROR, "Lỗi", "Không thể xóa môn học!");
			}
		}
	}

	public String validateSubjectForm(String name, String credit, String att, String e1, String e2, String e3,
			String f) {
		if (name == null || name.trim().isEmpty())
			return "Vui lòng nhập tên môn học!";
		try {
			Integer.parseInt(credit);
			Float.parseFloat(att);
			Float.parseFloat(e1);
			Float.parseFloat(e2);
			Float.parseFloat(e3);
			Float.parseFloat(f);
		} catch (NumberFormatException e) {
			return "Vui lòng nhập đúng định dạng số!";
		}
		return null;
	}

	public void clearSubjectForm() {
		subjectNameTextField.clear();
		creditTextField.clear();
		attandenceMarkTextField.clear();
		examMark1TextField.clear();
		examMark2TextField.clear();
		examMark3TextField.clear();
		finalMarkTextField.clear();
	}

	// ================================ USER SUBJECT ========================================
	@FXML
	public void loadUserSubjectList() throws SQLException {
		if (userSubjectDAO == null) {
			userSubjectDAO = new UserSubjectDAO();
		}
		ObservableList<UserSubject> userSubjects = FXCollections
				.observableArrayList(userSubjectDAO.getAllUserSubject());
		managerListTableView.setItems(userSubjects);
	}

	@FXML
	public void onRowUserSubjectClick() {
		UserSubject selected = managerListTableView.getSelectionModel().getSelectedItem();
		if (selected != null) {
			managerUserCodeTextField.setText(String.valueOf(selected.getUserCode()));
			managerSubjectCodeTextField.setText(String.valueOf(selected.getSubjectCode()));
			managerAttandenceMarkTextField.setText(String.valueOf(selected.getAttendanceExamMark()));
			managerExamMark1TextField.setText(String.valueOf(selected.getExamMark1()));
			managerExamMark2TextField.setText(String.valueOf(selected.getExamMark2()));
			managerExamMark3TextField.setText(String.valueOf(selected.getExamMark3()));
			managerFinalMarkTextField.setText(String.valueOf(selected.getFinalExamMark()));
		}
	}

	@FXML
	public void onClickAddManagerButton() throws SQLException {
		try {
			int userCode = Integer.parseInt(managerUserCodeTextField.getText());
			int subjectCode = Integer.parseInt(managerSubjectCodeTextField.getText());
			float attendanceMark = Float.parseFloat(managerAttandenceMarkTextField.getText());
			float examMark1 = Float.parseFloat(managerExamMark1TextField.getText());
			float examMark2 = Float.parseFloat(managerExamMark2TextField.getText());
			float examMark3 = Float.parseFloat(managerExamMark3TextField.getText());
			float finalMark = Float.parseFloat(managerFinalMarkTextField.getText());
			
			if(userDAO.getUserById(userCode) == null || subjectDAO.getSubjectById(subjectCode) == null) {
				showAlert(AlertType.ERROR, "Lỗi", "Không tìm thấy mã người dùng hoặc mã môn học!");
				return;
			}

			UserSubject us = new UserSubject(userCode, subjectCode, attendanceMark, examMark1, examMark2, examMark3,
					finalMark);
			boolean added = userSubjectDAO.addUserSubject(us);

			if (added) {
				loadUserSubjectList();
				clearUserSubjectForm();
				showAlert(AlertType.INFORMATION, "Thành công", "Thêm thông tin quản lý môn học thành công!");
			} else {
				showAlert(AlertType.ERROR, "Lỗi", "Thêm không thành công. Kiểm tra lại mã người dùng hoặc mã môn học!");
			}

		} catch (NumberFormatException e) {
			showAlert(AlertType.ERROR, "Lỗi", "Vui lòng nhập đúng định dạng số cho các trường điểm và mã!");
		}
	}

	@FXML
	public void onClickUpdateManagerButton() throws SQLException {
		UserSubject selected = managerListTableView.getSelectionModel().getSelectedItem();

		if (selected == null) {
			showAlert(AlertType.ERROR, "Lỗi", "Không tìm thấy bản ghi quản lý môn học để chỉnh sửa.");
			return;
		}

		try {
			selected.setUserCode(Integer.parseInt(managerUserCodeTextField.getText()));
			selected.setSubjectCode(Integer.parseInt(managerSubjectCodeTextField.getText()));
			selected.setAttendanceExamMark(Float.parseFloat(managerAttandenceMarkTextField.getText()));
			selected.setExamMark1(Float.parseFloat(managerExamMark1TextField.getText()));
			selected.setExamMark2(Float.parseFloat(managerExamMark2TextField.getText()));
			selected.setExamMark3(Float.parseFloat(managerExamMark3TextField.getText()));
			selected.setFinalExamMark(Float.parseFloat(managerFinalMarkTextField.getText()));
			
			if(userDAO.getUserById(Integer.parseInt(managerUserCodeTextField.getText())) == null 
					|| subjectDAO.getSubjectById(Integer.parseInt(managerSubjectCodeTextField.getText())) == null) {
				showAlert(AlertType.ERROR, "Lỗi", "Không tìm thấy mã người dùng hoặc mã môn học!");
				return;
			}

			boolean updated = userSubjectDAO.updateUserSubject(selected);

			if (updated) {
				loadUserSubjectList();
				clearUserSubjectForm();
				showAlert(AlertType.INFORMATION, "Thành công", "Chỉnh sửa bản ghi quản lý môn học thành công!");
			} else {
				showAlert(AlertType.ERROR, "Lỗi", "Chỉnh sửa không thành công.");
			}

		} catch (NumberFormatException e) {
			showAlert(AlertType.ERROR, "Lỗi", "Vui lòng nhập đúng định dạng số!");
		}
	}

	@FXML
	public void onClickDeleteManagerButton() throws SQLException {
		UserSubject selected = managerListTableView.getSelectionModel().getSelectedItem();

		if (selected == null) {
			showAlert(AlertType.ERROR, "Lỗi", "Không tìm thấy bản ghi quản lý môn học để xóa.");
			return;
		}

		Optional<ButtonType> confirm = showAlert(AlertType.CONFIRMATION, "Xác nhận xóa",
				"Bạn có chắc chắn muốn xóa bản ghi này?");
		if (confirm.get() == ButtonType.OK) {
			boolean deleted = userSubjectDAO.deleteUserSubject(selected.getUserSubjectCode());
			if (deleted) {
				loadUserSubjectList();
				clearUserSubjectForm();
				showAlert(AlertType.INFORMATION, "Thành công", "Xóa bản ghi thành công!");
			} else {
				showAlert(AlertType.ERROR, "Lỗi", "Xóa không thành công.");
			}
		}
	}

	public void clearUserSubjectForm() {
		managerUserCodeTextField.clear();
		managerSubjectCodeTextField.clear();
		managerAttandenceMarkTextField.clear();
		managerExamMark1TextField.clear();
		managerExamMark2TextField.clear();
		managerExamMark3TextField.clear();
		managerFinalMarkTextField.clear();
	}
}
