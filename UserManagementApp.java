package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;
import java.io.*;

public class UserManagementApp extends Application {
	private Map<String, User> users = new HashMap<>();
	private Map<String, Articles> articles = new HashMap<>();
	private User currentUser;
	public String oneTimePassword, oneTimeStudent, oneTimeInstructor, oneTimeAdmin, oneTimeStudentIns, oneTimeReset;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("User Management Application");
		showLoginPage(primaryStage);
	}

	private void showAdminPage(Stage stage, String user) {		//show admin main home page
		save();
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20,20,20,20));
		
		Button resetUser = new Button("Reset Users Account");
		TextField resetUserInput = new TextField();
		resetUserInput.setPromptText("Enter username for user you want to reset");
		
		Button listUsers = new Button("List Users");
		Button deleteUsers = new Button("Delete a user");
		TextField deleteAccountInput = new TextField();
		Button updateRoles = new Button("Update a user's role(s)");
		TextField updateRolesInput = new TextField();
		Button generatePasswordStudent = new Button("Generate invite code for student");
		Button generatePasswordInstructor = new Button("Generate invite code password for instructor");
		Button generatePasswordAdmin = new Button("Generate invite code for admin");
		Button generatePasswordStuIns = new Button("Generate invite code for student and instructor");
		Button logout = new Button("Log Out");
		Button articles = new Button("Articles");
		Button specialArticles = new Button("Special Articles");
		TextField articleGroup = new TextField();
		articleGroup.setPromptText("Enter the special group you would like to view");
		
		//button actions
		resetUser.setOnAction(e -> {
			String name = resetUserInput.getText();
			User resetUsername = users.get(name);
			if(resetUsername != null)
			{
				oneTimeReset = generateRandomPassword(8);
				System.out.print("Reset one-time password is: " + oneTimeReset + "\nThis password expires 12/31/2024");
				resetUser(stage, name);
				save();
			}
			else
				showAlert("Error", "This username does not exist, please enter a valid account username");
		});
		logout.setOnAction(e -> showLoginPage(stage));		//handles logout
		generatePasswordStudent.setOnAction(e -> {		//handle generating password
			oneTimeStudent = generateRandomPassword(8);
			System.out.print("Student's invite code: " + oneTimeStudent + "\nThis code expires 12/31/2024\n");
		});
		generatePasswordInstructor.setOnAction(e -> {
			oneTimeInstructor = generateRandomPassword(8);
			System.out.print("Instructor's invite code: " + oneTimeInstructor + "\nThis code expires 12/31/2024\n");
		});
		generatePasswordAdmin.setOnAction(e-> {
			oneTimeAdmin = generateRandomPassword(8);
			System.out.print("Admin's invite code: " + oneTimeAdmin + "\nThis code expires 12/31/2024\n");
		});
		generatePasswordStuIns.setOnAction(e-> {
			oneTimeStudentIns = generateRandomPassword(8);
			System.out.print("Invite code for both student and instructor role: " + oneTimeInstructor + "\nThis code expires 12/31/2024\n");
		});
		articles.setOnAction(e-> articleHomePage(stage));
		specialArticles.setOnAction(e-> specialAccessPage(stage));
		
		updateRolesInput.setPromptText("Enter the username of the desired account to update its roles"); //update roles
		updateRoles.setOnAction(e ->{
			String username = updateRolesInput.getText();
			User desiredUser = users.get(username);
			if(desiredUser != null) {
				updateRolesPage(stage, username);
			} else {
				showAlert("Error", "This username does not exist, please enter a valid account username");
			}});
		
	    	layout.getChildren().addAll(updateRolesInput, updateRoles);

		
		deleteAccountInput.setPromptText("Enter the username of the account desired to be deleted");
		
		deleteUsers.setOnAction(e -> {
			
			String username = deleteAccountInput.getText();
			User deleteUser = users.get(username);
			if (deleteUser != null) {
				confirmDelete(stage,deleteAccountInput.getText(),username);	//Show the confirmation page
			} else {
				showAlert("Error", "This username does not exist, please enter a valid account username");
			}
		});
		
		layout.getChildren().addAll(listUsers, deleteAccountInput, deleteUsers, resetUserInput, resetUser, generatePasswordStudent, generatePasswordInstructor, generatePasswordAdmin, generatePasswordStuIns,articles,articleGroup,specialArticles,logout);
		Scene scene = new Scene(layout, 600, 600);
		stage.setScene(scene);
		listUsers.setOnAction(e ->listUsers(stage));

		
	}
	
	private void specialAccessPage(Stage stage)	{
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20,20,20,20));
		
		Label lbArticles = new Label("List articles in this group.");
		Button btArticles = new Button("List Articles");
		
		Label lbAdmins = new Label("List admins given rights to this group.");
		Button btAdmins = new Button("List Admins");
		
		Label lbInstructors = new Label("List instructors given viewing rights to this group.");
		Button btInstructors = new Button("List Instructors");
		
		Label lbAdminInstructors = new Label("List instructors given rights to this group.");
		Button btAdminInstructors = new Button("List Instructors");
		
		Label lbStudents = new Label("List students given viewing rights to this group.");
		Button btStudents = new Button("List Students");
		
		Button goBack = new Button("Go Back");
		goBack.setOnAction(e ->showAdminPage(stage, "admin"));
		
		layout.getChildren().addAll(lbArticles,btArticles,lbAdmins,btAdmins,lbInstructors,btInstructors,lbAdminInstructors,btAdminInstructors,lbStudents,btStudents,goBack);
		Scene scene = new Scene(layout, 400, 400);
		stage.setScene(scene);
		stage.show();
	}
	
	private void helpSystemPage(Stage stage)	{
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20,20,20,20));
		
		Label title = new Label("Welcome to the help system");
		Label search = new Label("What key words would you like to search?");
		
	}
	
	private void articleHomePage(Stage stage)	{
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20,20,20,20));
		
		Label action = new Label("What action would you like to take?");
		
		//buttons for the article page
		Button listArticles = new Button("List Articles");
		Button createArticle = new Button("Create");
		Button deleteArticle = new Button("Delete");
		Button viewArticle = new Button("View Article");
		Button goBack = new Button("Go Back");
		Button viewByGroup = new Button("View articles by group");
		Button backup = new Button("Backup all articles");
		Button restore = new Button("Restore all articles from backup");
		Button backupGroup = new Button("Backup Group");
		Button restoreGroup = new Button("Restore Group");
		//text fields for buttons
		TextField deleteArticleInput = new TextField();
		TextField viewArticleInput = new TextField();
		TextField viewByGroupTf = new TextField();
		TextField backupByGroup = new TextField();
		TextField restoreByGroup = new TextField();
		//Set prompts for text fields
		backupByGroup.setPromptText("Enter the group you wish to backup");
		deleteArticleInput.setPromptText("Enter the title of the article you wish to delete");
		viewArticleInput.setPromptText("Enter the title of the article you wish to view");
		viewByGroupTf.setPromptText("Enter the group you wish to view");
		restoreByGroup.setPromptText("Enter the group you wish to restore");
		
		//button actions
		if(currentUser.getRoles().get(0).equals("Admin"))
			goBack.setOnAction(e ->showAdminPage(stage, "admin"));
		else if(currentUser.getRoles().get(0).equals("Instructor"))
			goBack.setOnAction(e ->showHomePageInstructor(stage, "Instructor"));
		viewArticle.setOnAction(e -> {
			String name = viewArticleInput.getText();
			Articles articleName = articles.get(name);
			if(articleName != null)
			{
				articleWindow(stage, name);
			}
			else
				showAlert("Error", "The article you entered does not exist");
		});
		createArticle.setOnAction(e -> createArticlePage(stage));
		listArticles.setOnAction(e -> listArticles(stage));
		viewByGroup.setOnAction(e -> {
			if(viewByGroupTf.getText().isEmpty())	{
			listArticlesByGroup(stage, viewByGroupTf.getText());
			}
			else {
				showAlert("Error","Please enter a group");
			}
		});
		
		backup.setOnAction(e -> backupArticles());
		restore.setOnAction(e -> restoreArticles());
		deleteArticle.setOnAction(e -> {
			String input = deleteArticleInput.getText();
			if(articles.get(input) != null)
			{
				articles.remove(input);
				showAlert("Success", "Article Successfully deleted");
			}
			else
				showAlert("Error", "Article does not exist");
		});
		backupGroup.setOnAction(e -> {
			String line = backupByGroup.getText();
			if(!line.isEmpty())
			{
				backupByGroup(line);
				showAlert("Success", "Successfully backed up group " + line);
			}
		});
		restoreGroup.setOnAction(e ->{
			String line = restoreByGroup.getText();
			if(!line.isEmpty())
			{
				restoreByGroup(line);
				showAlert("Success", "Successfully restored group "+ line);
			}		
		});
		
		layout.getChildren().addAll(action,listArticles,createArticle,deleteArticleInput,deleteArticle,viewArticleInput,viewArticle, viewByGroupTf, viewByGroup, backup, restore, backupByGroup, backupGroup, restoreByGroup, restoreGroup);
		Scene scene = new Scene(layout, 500, 600);
		stage.setScene(scene);
		stage.show();
	}
	
	private void articleWindow(Stage stage, String ArticleName)	{
		
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20,20,20,20));
		Articles article = articles.get(ArticleName);
		
		//text field for article text
		TextArea articleBody = new TextArea();
		articleBody.setText(article.getBody());
		
		//buttons
		Button goBack = new Button("Go Back");
		Button updateArticle = new Button("Update Article");
		updateArticle.setOnAction(e -> {
			article.setBody(articleBody.getText());
			save();
			showAlert("Success", "The article has been updated");
			articleHomePage(stage);
		});
		//button actions
		goBack.setOnAction(e -> articleHomePage(stage));
		layout.getChildren().addAll(articleBody, goBack, updateArticle);
		Scene scene = new Scene(layout, 500, 500);
		stage.setScene(scene);
		stage.show();
	}
	
	private void listUsers(Stage stage) {
		ObservableList<User> userList = FXCollections.observableArrayList(users.values());
		ListView<User> listview = new ListView<>(userList);
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20,20,20,20));
		Button goBack = new Button("Go Back");
		goBack.setOnAction(e ->showAdminPage(stage, "Admin"));
		layout.getChildren().addAll(listview, goBack);
		Scene scene = new Scene(layout, 300, 200);
		stage.setScene(scene);
		stage.show();
		
	}
	
	private void listArticles(Stage stage)				//list all articles method for admin and instructor
	{
		ObservableList<Articles> articleList = FXCollections.observableArrayList(articles.values());
		ListView<Articles> listview = new ListView<>(articleList);
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20,20,20,20));
		Button goBack = new Button("Go Back");
		goBack.setOnAction(e -> articleHomePage(stage));
		layout.getChildren().addAll(listview, goBack);
		Scene scene = new Scene(layout, 300, 200);
		stage.setScene(scene);
		stage.show();
	}	
	
	private void listArticlesByGroup(Stage stage, String group)		//list all articles with the same group
	{
		Map<String, Articles> groupMap = getArticlesByGroup(group);
		ObservableList<Articles> articleList = FXCollections.observableArrayList(groupMap.values());
		ListView<Articles> listview = new ListView<>(articleList);
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20,20,20,20));
		Button goBack = new Button("Go Back");
		goBack.setOnAction(e -> articleHomePage(stage));
		layout.getChildren().addAll(listview, goBack);
		Scene scene = new Scene(layout, 300, 200);
		stage.setScene(scene);
		stage.show();
	}

	private void updateRolesPage(Stage stage, String username) {
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20,20,20,20));
		
		
		Button addRoleButton = new Button ("Add role");
		Button removeRoleButton = new Button ("Remove role");
		Button goBackButton = new Button("Go back");
		
		TextField addRoleInput = new TextField();
		TextField removeRoleInput = new TextField();
		
		addRoleInput.setPromptText("Enter role to add: Instructor, Student");
		removeRoleInput.setPromptText("Enter role to remove: Instructor, Student");
		
		User desiredUser = users.get(username);
		
		goBackButton.setOnAction(e -> showAdminPage(stage, "Admin"));
		
		
		addRoleButton.setOnAction(e -> {
			String addedRole = addRoleInput.getText();
			if(!addedRole.equals("Admin")) { //check if the user is attempting to add an admin role without a code
				if(addedRole.equals("Student") || addedRole.equals("Instructor")) { //check if the role being added is a valid role
					if(!desiredUser.getRoles().contains(addedRole)) { //check if user already has the desired role to add
						desiredUser.getRoles().add(addedRole);
						showAlert("Success", "Role successfully added");
					}
					else {
						showAlert("Error", "This user already has this role");
					}
				}
				else {
					showAlert("Error", "Please enter a valid role to add");
				}
			}
			else {
				showAlert("Error", "Admin roles can only be added through invitations");
			}
		});
		
		
		
		removeRoleButton.setOnAction(e -> {
			String removedRole = removeRoleInput.getText();
			if(!removedRole.equals("Admin")){
				if(desiredUser.getRoles().size() > 1) { //check if the user has more than 1 role
					if(removedRole.equals("Student") || removedRole.equals("Instructor")) { //check if the role being removed is valid
						if(desiredUser.getRoles().contains(removedRole)) { //check if the user has the role before removing
							desiredUser.getRoles().remove(removedRole);
							showAlert("Success", "Role has been successfully removed");
						}
						else {
							showAlert("Error", "This role cannot be removed as the user does not have that role.");
						}
						
					}
					else {
						showAlert("Error", "Please enter a valid role to remove: Instructor, Student");
					}
				}
				else {
					showAlert("Error", "This role cannot be removed as the user only has one role.");
				}
			}
			else {
				showAlert("Error", "Admin roles cannot be removed");
			}
		});
		
		layout.getChildren().addAll(addRoleInput,addRoleButton,removeRoleInput,removeRoleButton,goBackButton);
		Scene scene = new Scene(layout, 500, 500);
		stage.setScene(scene);
		stage.show();
	}

	
	private void confirmDelete(Stage stage, String name, String admin) {			//confirmation of deletion page
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20,20,20,20));
		
		Label usernameLabel = new Label("Are you sure you would like to\ndelete this account?");
		//buttons
		Button confirmDelete = new Button("Delete");
		Button goBack = new Button("Go Back");
		//button actions
		goBack.setOnAction(e ->showAdminPage(stage, "Admin"));
		confirmDelete.setOnAction(e -> {				//handles delete account button
			if (!name.isEmpty()) {
				deleteAccount(stage, name, admin);
			} else {
				showAlert("Error", "Please enter an account username");
			}
		});
		
		layout.getChildren().addAll(usernameLabel, confirmDelete,goBack);
		
		Scene scene = new Scene(layout, 300, 200);
		stage.setScene(scene);
		stage.show();
		
	}
	
	private void deleteAccount(Stage stage, String name, String admin) {			//delete user from list method
		User user = users.get(name);
		if (user != null) {
			users.remove(name);
			save();
			showAlert("Success", "Account has been deleted");
		} else {
			showAlert("Error", "This email does not exist, please enter a valid account email");
		}
		showAdminPage(stage, admin);
	}
	private void resetUser(Stage stage, String email)		//resets users account
	{
		User user = users.get(email);
		if(user != null)
		{
			user.setReset(true);
			showAlert("Success", "Account has been reset");
		}
		else
		{
			showAlert("Error", "Username does not exist");
		}
	}

	private void showLoginPage(Stage stage) {		//first page, will be login page with buttons to use one time password
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(8);
		grid.setHgap(10);

		Label usernameLabel = new Label("Username:");
		GridPane.setConstraints(usernameLabel, 0, 0);
		TextField usernameInput = new TextField();
		GridPane.setConstraints(usernameInput, 1, 0);

		Label passwordLabel = new Label("Password:");
		GridPane.setConstraints(passwordLabel, 0, 1);
		PasswordField passwordInput = new PasswordField();
		GridPane.setConstraints(passwordInput, 1, 1);

		Button loginButton = new Button("Login");
		GridPane.setConstraints(loginButton, 1, 2);
		loginButton.setOnAction(e -> handleLogin(stage, usernameInput.getText(), passwordInput.getText()));

		Button inviteCodeButton = new Button("Use Invite Code");
		GridPane.setConstraints(inviteCodeButton, 1, 3);
		inviteCodeButton.setOnAction(e -> showInviteCodePage(stage));

		grid.getChildren().addAll(usernameLabel, usernameInput, passwordLabel, passwordInput, loginButton, inviteCodeButton);

		Scene scene = new Scene(grid, 300, 200);
		stage.setScene(scene);
		stage.show();
		if(users.size() == 0)
			load();
		if (users.size() == 0) {
			oneTimePassword = generateRandomPassword(8);
			System.out.println("Welcome Admin. One time password is : " + oneTimePassword + 
					"\nThis password expires 12/31/2024");
		}
	}
	
	public static String generateRandomPassword(int length) {		//Generate a random 8 character 1 time password
		int leftLimit = 97; // letter 'a'
	    int rightLimit = 122; // letter 'z'
	    Random random = new Random();
	    StringBuilder buffer = new StringBuilder(length);

	    for (int i = 0; i < length; i++) {
	        int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
	        buffer.append((char) randomLimitedInt);
	        }

	    return buffer.toString();
	}
	
	private void handleResetPage(Stage stage, User user)		//page to reset account
	{
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20,20,20,20));
		
		TextField oneTimeInput = new TextField();
		Label prompt = new Label("Enter one-time password to reset account");
		oneTimeInput.setPromptText("Enter one-time password to reset account");
		Button enter = new Button("Enter");
		enter.setOnAction(e -> {
			LocalDateTime date = LocalDateTime.now();		//check if one time password has expired
			int year = date.getYear();
			if(year >= 2025)
			{
				showAlert("Error", "Password has expired!");
			}
			else {
				String text = oneTimeInput.getText();
				if(text.equals(oneTimeReset))			//check password
				{
					setNewPasswordPage(stage, user);
					oneTimeReset = "";
				}
				else {
					showAlert("Error", "Password does not match");
				}
			}
		});
		
		layout.getChildren().addAll(prompt, oneTimeInput, enter);
		Scene scene = new Scene(layout, 300, 300);
		stage.setScene(scene);
	}
	
	private void setNewPasswordPage(Stage stage, User user)		//set new password page
	{
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20,20,20,20));
		
		//prompts for scene
		Label label1 = new Label("Enter New Password");
		Label label2 = new Label("Confirm Password");
		TextField textfield = new TextField();
		TextField textfield2 = new TextField();
		textfield.setPromptText("New Password");
		textfield2.setPromptText("New Password");
		Button enter = new Button("Enter");
		enter.setOnAction(e -> {
			if(textfield.getText().equals(textfield2.getText()))
			{
				user.setPassword(textfield.getText());
				user.setReset(false);
				showLoginPage(stage);
			}
			else
			{
				showAlert("Error", "Passwords do not match");
			}
		});
		
		layout.getChildren().addAll(label1, textfield, label2, textfield2, enter);
		Scene scene = new Scene(layout, 400,400);
		stage.setScene(scene);
		
	}
	
	private void handleLogin(Stage stage, String username, String password) {		//method to handle login attempt
		User user = users.get(username);
		if (users.size() == 0 && password.equals(oneTimePassword)) {
			showAccountCreationPage(stage, List.of("Admin"));
		}
		else {
		if (user != null && user.getPassword().equals(password)) {
			currentUser = user;
			if(user.getReset()) {
				handleResetPage(stage, user);
			}
			else if (!user.isSetupComplete()) {
				showAccountSetupPage(stage);
			} else if (user.getRoles().size() > 1) {
				showRoleSelectionPage(stage);
			} else {
				showHomePage(stage, user.getRoles().get(0));
			}
		} else {
			showAlert("Login Failed", "Invalid username or password.");
		}
		}
	}

	private void showInviteCodePage(Stage stage) {		//scene to show invite code page
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20, 20, 20, 20));
		
		Button goBack = new Button("Go Back");
		goBack.setOnAction(e -> showLoginPage(stage));
		
		Label inviteCodeLabel = new Label("Enter Invite Code:");
		TextField inviteCodeInput = new TextField();

		Button submitButton = new Button("Submit");
		submitButton.setOnAction(e -> handleInviteCode(stage, inviteCodeInput.getText()));

		layout.getChildren().addAll(inviteCodeLabel, inviteCodeInput, submitButton, goBack);
		Scene scene = new Scene(layout, 300, 200);
		stage.setScene(scene);
	}
	

	private void handleInviteCode(Stage stage, String inviteCode) {		//method to handle invite code
		//oneTimePassword, oneTimeStudent, oneTimeInstructor, oneTimeAdmin, oneTimeStudentIns;
		
		if (inviteCode.equals(oneTimeAdmin) && !inviteCode.equals("")) {		//handles one time password for admin
			showAccountCreationPage(stage, List.of("Admin"));
			oneTimeAdmin = "";													//remove the one time password
		} else if (inviteCode.equals(oneTimeStudent) && !inviteCode.equals("")) {		//handles one time password for student
			showAccountCreationPage(stage, List.of("Student"));
			oneTimeStudent = "";														//removes one time password for student
		} else if (inviteCode.equals(oneTimeInstructor) && !inviteCode.equals("")) {
			showAccountCreationPage(stage, List.of("Instructor"));
			oneTimeInstructor = "";
		} else if(inviteCode.equals(oneTimePassword) && !inviteCode.equals("")) {
				showAccountCreationPage(stage, List.of("Admin"));
				oneTimePassword = null;
			}
		 else {
			showAlert("Invalid Code", "The invite code is invalid.");
		}
	}

	private void showAccountCreationPage(Stage stage, List<String> roles) {		//account user name and password creation
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20, 20, 20, 20));

		Label usernameLabel = new Label("Username:");
		TextField usernameInput = new TextField();

		Label passwordLabel = new Label("Password:");
		PasswordField passwordInput = new PasswordField();

		Label confirmPasswordLabel = new Label("Confirm Password:");
		PasswordField confirmPasswordInput = new PasswordField();

		Button createAccountButton = new Button("Create Account");
		createAccountButton.setOnAction(e -> {
			if (passwordInput.getText().equals(confirmPasswordInput.getText())) {
				User newUser = new User(usernameInput.getText(), passwordInput.getText(), roles);
				users.put(usernameInput.getText(), newUser);
				showLoginPage(stage);
			} else {
				showAlert("Password Mismatch", "Passwords do not match.");
			}
		});

		layout.getChildren().addAll(usernameLabel, usernameInput, passwordLabel, passwordInput, confirmPasswordLabel, confirmPasswordInput, createAccountButton);
		Scene scene = new Scene(layout, 300, 300);
		stage.setScene(scene);
	}

	private void showAccountSetupPage(Stage stage) {		//account set up page
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20, 20, 20, 20));

		Label firstNameLabel = new Label("First Name:");
		TextField firstNameInput = new TextField();

		Label middleNameLabel = new Label("Middle Name:");
		TextField middleNameInput = new TextField();

		Label lastNameLabel = new Label("Last Name:");
		TextField lastNameInput = new TextField();

		Label preferredNameLabel = new Label("Preferred First Name (Optional):");
		TextField preferredNameInput = new TextField();

		Label emailLabel = new Label("Email:");
		TextField emailInput = new TextField();

		Button finishSetupButton = new Button("Finish Setup");		//once clicked with create new user with the text and set user to set up complete
		finishSetupButton.setOnAction(e -> {
			if(!firstNameInput.getText().equals("") && !middleNameInput.getText().equals("") && !lastNameInput.getText().equals("") && !emailInput.getText().equals("")) {	//check to see if required text fields are full
			currentUser.setFirstName(firstNameInput.getText());
			currentUser.setMiddleName(middleNameInput.getText());
			currentUser.setLastName(lastNameInput.getText());
			currentUser.setPreferredName(preferredNameInput.getText());
			currentUser.setEmail(emailInput.getText());
			currentUser.setSetupComplete(true);
			showHomePage(stage, currentUser.getRoles().get(0));	
		}
			else
				showAlert("Error", "Not all required fields have been filled");
		});
		layout.getChildren().addAll(firstNameLabel, firstNameInput, middleNameLabel, middleNameInput, lastNameLabel, lastNameInput, preferredNameLabel, preferredNameInput, emailLabel, emailInput, finishSetupButton);
		Scene scene = new Scene(layout, 400, 400);
		stage.setScene(scene);
	}

	private void showRoleSelectionPage(Stage stage) {		//If user has multiple rows show page to see which one they want to use for this login session
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20, 20, 20, 20));

		Label roleLabel = new Label("Select Role for this Session:");
		ComboBox<String> roleComboBox = new ComboBox<>();
		roleComboBox.getItems().addAll(currentUser.getRoles());

		Button selectRoleButton = new Button("Select Role");
		String thisRole = roleComboBox.getValue();
		if(thisRole.equals("")) {
		selectRoleButton.setOnAction(e -> showHomePage(stage, roleComboBox.getValue()));
		}
		layout.getChildren().addAll(roleLabel, roleComboBox, selectRoleButton);
		Scene scene = new Scene(layout, 300, 200);
		stage.setScene(scene);
	}

	private void showHomePage(Stage stage, String role) {		//Original showHome page to be rerouted to the correct role
		if(role.equals("Student"))
		{
			showHomePageStudent(stage, role);
		}
		else if (role.equals("Instructor"))
		{
			showHomePageInstructor(stage, role);
		}
		else if(role.equals("Admin"))
			showAdminPage(stage, role);
		else
		{
			showAlert("Error", "Could not find role");		//error message in case role could not be determined
		}
	}
	
	private void showHomePageInstructor(Stage stage, String role) {		//home page for instructor role
		save();
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20, 20, 20, 20));
		Button articles = new Button("Articles");
		
		Label welcomeLabel = new Label("Welcome, " + currentUser.getDisplayName() + " (" + role + ")");
		Button logoutButton = new Button("Log Out");
		logoutButton.setOnAction(e -> {
			currentUser = null;
			showLoginPage(stage);
		});
		articles.setOnAction(e -> articleHomePage(stage));

		layout.getChildren().addAll(welcomeLabel, articles, logoutButton);
		Scene scene = new Scene(layout, 300, 200);
		stage.setScene(scene);
	}
	
	private void showHomePageStudent(Stage stage, String role) {		//role page for student role
		save();
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20, 20, 20, 20));

		Label welcomeLabel = new Label("Welcome, " + currentUser.getDisplayName() + " (" + role + ")");
		Button logoutButton = new Button("Log Out");
		logoutButton.setOnAction(e -> {
			currentUser = null;
			showLoginPage(stage);
		});

		layout.getChildren().addAll(welcomeLabel, logoutButton);
		Scene scene = new Scene(layout, 300, 200);
		stage.setScene(scene);
	}

	private void showAlert(String title, String message) {		//alert method to be able to let user know if any errors
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
	
	private void createArticlePage(Stage stage)
	{
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20,20,20,20));
		Label prompt = new Label("Please fill out article's information: ");		//prompts to add article
		TextField titleTf = new TextField();
		TextField authorsTf = new TextField();
		TextField bodyTf = new TextField();
		TextField keywordsTf = new TextField();
		TextField descriptionTf = new TextField();
		TextField referencesTf = new TextField();
		TextField groupTf = new TextField();
		titleTf.setPromptText("Enter the title of the article");
		authorsTf.setPromptText("Enter the author(s) of the article");
		bodyTf.setPromptText("Enter the body of the article");
		keywordsTf.setPromptText("Enter the keywords of the article seperated by ','");
		descriptionTf.setPromptText("Enter the description of the article");
		referencesTf.setPromptText("Enter the references for the article");
		groupTf.setPromptText("Enter the group of the article");
		Button addButt = new Button("Submit Article");
		Button goBack = new Button("Go Back");
		addButt.setOnAction(e -> {				//add article button action
			String t = titleTf.getText();
			String a = authorsTf.getText();
			String b = bodyTf.getText();
			String k = keywordsTf.getText();
			String d = keywordsTf.getText();
			String r = referencesTf.getText();
			String g = groupTf.getText();
			if(!t.equals("") && !a.equals("") && !b.equals("") && !k.equals("") && !d.equals("") && !r.equals("") && !g.equals(""))		//if textfields are filled out
			{
				Articles newArticle = new Articles(t, d, k, a, b, r, g);
				if(articles.get(t) == null)			//if article is unique then add it to system else show error
				{
					articles.put(t, newArticle);
					save();
					showAlert("Success!", "The article has been created.");
					articleHomePage(stage);
				}
				else
					showAlert("Error", "This article is already in the system.");
			}
			
			else
			{
				showAlert("Error", "Not all text fields have been filled out");
			}
		});
	
		//button actions
		goBack.setOnAction(e ->articleHomePage(stage));
		
		layout.getChildren().addAll(prompt, titleTf, authorsTf, bodyTf, keywordsTf, descriptionTf, referencesTf, groupTf, addButt, goBack);
		Scene scene = new Scene(layout, 500, 500);
		stage.setScene(scene);
		
	}
	
	private void save()			//save database
	{
		try {
		      File myObj = new File("data.txt");
		      if (myObj.createNewFile()) {
		        System.out.println("File created: " + myObj.getName());
		      } 
		      
		      FileWriter writer = new FileWriter("data.txt");
		      BufferedWriter myWriter = new BufferedWriter(writer);
		      myWriter.write("users");
		      myWriter.newLine();
		      User temp;
		      Articles tempA;
		      for(Map.Entry<String, User> entry : users.entrySet())		//write the users into data
		      {
		    	  temp = entry.getValue();
		    	  myWriter.write(temp.getUsername());
		    	  myWriter.newLine();
		    	  myWriter.write(temp.getPassword());
		    	  myWriter.newLine();
		    	  myWriter.write(temp.getEmail());
		    	  myWriter.newLine();
		    	  myWriter.write(String.join(":", temp.getRoles()));
		    	  myWriter.newLine();
		    	  myWriter.write(temp.getFirstName());
		    	  myWriter.newLine();
		    	  myWriter.write(temp.getMiddleName());
		    	  myWriter.newLine();
		    	  myWriter.write(temp.getLastName());
		    	  myWriter.newLine();
		    	  myWriter.write(temp.getDisplayName());
		    	  myWriter.newLine();
		    	  if(temp.isSetupComplete())
		    		  myWriter.write("1");
		    	  else
		    		  myWriter.write("0");
		    	  myWriter.newLine();
		      }
		      myWriter.write("articles");
		      myWriter.newLine();
		      
		      for(Map.Entry<String, Articles> entry : articles.entrySet())			//write articles into data
		      {
		    	  tempA = entry.getValue();
		    	  myWriter.write(tempA.getTitle());
		    	  myWriter.newLine();
		    	  myWriter.write(tempA.getAuthors());
		    	  myWriter.newLine();
		    	  myWriter.write(tempA.getBody());
		    	  myWriter.newLine();
		    	  myWriter.write(tempA.getDescription());
		    	  myWriter.newLine();
		    	  myWriter.write(tempA.getGroup());
		    	  myWriter.newLine();
		    	  myWriter.write(tempA.getKeywords());
		    	  myWriter.newLine();
		    	  myWriter.write(tempA.getReferences());
		    	  myWriter.newLine();
		    	  
		      }
		      myWriter.close();
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
	
	private boolean load()			//load database
	{
		try {
		      File myObj = new File("data.txt");
		      if (myObj.createNewFile()) {
		        System.out.println("File created: " + myObj.getName());
		        return true;
		      } else {
		    	  
		        //System.out.println("File already exists.");
		        Scanner reader = new Scanner(myObj);		        
		        String line;
		        if(reader.hasNextLine())
		        	line = reader.nextLine();
		        while(reader.hasNextLine())
		        {
		        	line = reader.nextLine();
		        	if(line.equals("articles"))
		        		break;
		        	String un = line;
		        	line = reader.nextLine();
		        	String p = line;
		        	line = reader.nextLine();
		        	String e = line;
		        	line = reader.nextLine();
		        	String rol = line;
		        	String[] r = rol.split(":");
		        	List<String> ro = Arrays.asList(r);
		        	line = reader.nextLine();
		        	String fn = line;
		        	line = reader.nextLine();
		        	String mn = line;
		        	line = reader.nextLine();
		        	String ln = line;
		        	line = reader.nextLine();
		        	String pn = line;
		        	line = reader.nextLine();
		        	int i = Integer.parseInt(line);
		        	boolean setUp;
		        	if(i == 1)
		        		setUp = true;
		        	else
		        		setUp = false;
		        	User newUser = new User(un, p, ro);
		        	newUser.setEmail(e);
		        	newUser.setFirstName(fn);
		        	newUser.setLastName(ln);
		        	newUser.setMiddleName(mn);
		        	newUser.setPreferredName(pn);
		        	newUser.setSetupComplete(setUp);
		        	users.put(un, newUser);
		        	
		        }
		        while(reader.hasNextLine())
		        {
		        	line = reader.nextLine();
		        	String t = line;
		        	line = reader.nextLine();
		        	String a = line;
		        	line = reader.nextLine();
		        	String b = line;
		        	line = reader.nextLine();
		        	String d = line;
		        	line = reader.nextLine();
		        	String g = line;
		        	line = reader.nextLine();
		        	String k = line;
		        	line = reader.nextLine();
		        	String r = line;
		        	Articles newArt = new Articles(t, d, k, a, b, r, g);
		        	articles.put(t, newArt);
		        }
		        reader.close();
		        return false;
		        
		      }
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		return true;
	}
	
	private Map<String, Articles> getArticlesByGroup(String group)		//returns a map of articles all from the same group
	{
		Map<String, Articles> groupMap = new HashMap<>();
		Articles temp;
		for(Map.Entry<String, Articles> entry : articles.entrySet())
		{
			temp = entry.getValue();
			if(temp.getGroup().equals(group))
			{
				groupMap.put(temp.getTitle(), temp);
			}
		}
		return groupMap;
		
	}
	
	private void backupArticles()		//back up all articles
	{
		File myFile = new File("backup.txt");
		try {
			if(myFile.createNewFile())
				System.out.println("File created with name: " + myFile.getName());
			else
				System.out.println("File already existed overwriting backup");
		
			FileWriter writer = new FileWriter("backup.txt");
			BufferedWriter myWriter = new BufferedWriter(writer);
			Articles tempA;
		for(Map.Entry<String, Articles> entry : articles.entrySet())			//write articles into data
	      {
	    	  tempA = entry.getValue();
	    	  myWriter.write(tempA.getTitle());
	    	  myWriter.newLine();
	    	  myWriter.write(tempA.getAuthors());
	    	  myWriter.newLine();
	    	  myWriter.write(tempA.getBody());
	    	  myWriter.newLine();
	    	  myWriter.write(tempA.getDescription());
	    	  myWriter.newLine();
	    	  myWriter.write(tempA.getGroup());
	    	  myWriter.newLine();
	    	  myWriter.write(tempA.getKeywords());
	    	  myWriter.newLine();
	    	  myWriter.write(tempA.getReferences());
	    	  myWriter.newLine();
	    	  
	      }
	      myWriter.close();
		}
	      catch (IOException e)
			{
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
	}
	
	private void backupByGroup(String group)
	{
		File myFile = new File(group + ".txt");			//new file with group name
		try
		{
			if(myFile.createNewFile())
				System.out.println("New backup created with name " + myFile.getName());
			else
				System.out.println("Backup with group " + group + " already existed overwriting withe new backup");
			
			FileWriter writer = new FileWriter(myFile);
			BufferedWriter myWriter = new BufferedWriter(writer);
			Articles tempA;
			
			for(Map.Entry<String, Articles> entry : articles.entrySet())			//write articles that exist in that group into backup
		      {
		    	  tempA = entry.getValue();
		    	  if(tempA.getGroup().equals(group)) {
		    		  myWriter.write(tempA.getTitle());
		    		  myWriter.newLine();
		    		  myWriter.write(tempA.getAuthors());
		    		  myWriter.newLine();
		    		  myWriter.write(tempA.getBody());
		    		  myWriter.newLine();
		    		  myWriter.write(tempA.getDescription());
		    		  myWriter.newLine();
		    		  myWriter.write(tempA.getGroup());
		    		  myWriter.newLine();
		    		  myWriter.write(tempA.getKeywords());
		    		  myWriter.newLine();
		    		  myWriter.write(tempA.getReferences());
		    		  myWriter.newLine();
		    	  }
		      }
			myWriter.close();
		}
		catch (IOException e)
		{
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	
	private void restoreByGroup(String group)
	{
		File backup = new File(group + ".txt");
		try
		{
			if(backup.createNewFile())		//if file didn't already exist return since you cannot restore
			{
				System.out.println("File does not exist");
				return;
			}
			else
			{
				Scanner reader = new Scanner(backup);		        
		        String line;
		        while(reader.hasNextLine())
		        {
		        	line = reader.nextLine();
		        	String t = line;
		        	line = reader.nextLine();
		        	String a = line;
		        	line = reader.nextLine();
		        	String b = line;
		        	line = reader.nextLine();
		        	String d = line;
		        	line = reader.nextLine();
		        	String g = line;
		        	line = reader.nextLine();
		        	String k = line;
		        	line = reader.nextLine();
		        	String r = line;
		        	Articles newArt = new Articles(t, d, k, a, b, r, g);
		        	if(articles.get(t) == null)				//only restore article if it does not already exist in real time system
		        		articles.put(t, newArt);
		        }
		        reader.close();
			}
		}
		catch (IOException e)
		{
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	
	private void restoreArticles()		//restore articles from backup 
	{
		File backup = new File("backup.txt");
		try {
			if(backup.createNewFile())
			{
				System.out.println("Backup file does not exist");
			}
			else
			{
				Scanner reader = new Scanner(backup);		        
		        String line;
		        while(reader.hasNextLine())
		        {
		        	line = reader.nextLine();
		        	String t = line;
		        	line = reader.nextLine();
		        	String a = line;
		        	line = reader.nextLine();
		        	String b = line;
		        	line = reader.nextLine();
		        	String d = line;
		        	line = reader.nextLine();
		        	String g = line;
		        	line = reader.nextLine();
		        	String k = line;
		        	line = reader.nextLine();
		        	String r = line;
		        	Articles newArt = new Articles(t, d, k, a, b, r, g);
		        	if(articles.get(t) == null)				//only restore article if it does not already exist in real time system
		        		articles.put(t, newArt);
		        }
		        reader.close();
			}
		}
		catch (IOException e)
		{
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
}