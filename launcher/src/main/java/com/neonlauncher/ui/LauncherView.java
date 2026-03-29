package com.neonlauncher.ui;

import com.neonlauncher.db.AccountRepository;
import com.neonlauncher.model.Account;
import com.neonlauncher.util.GameLauncher;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LauncherView {
    private final Stage stage;
    private final AccountRepository accountRepository;
    private Account selectedAccount;

    public LauncherView(Stage stage, AccountRepository accountRepository) {
        this.stage = stage;
        this.accountRepository = accountRepository;
    }

    public void showLoginScreen() {
        Label title = new Label("NEON LAUNCHER");
        title.getStyleClass().add("title");

        ListView<Account> accountListView = new ListView<>();
        accountListView.getItems().setAll(accountRepository.getAll());
        accountListView.getSelectionModel().selectedItemProperty().addListener((obs, oldAccount, newAccount) -> selectedAccount = newAccount);

        Button addAccountButton = new Button("Add account");
        addAccountButton.setOnAction(event -> showAddAccountScreen());

        Button playButton = new Button("Play");
        playButton.setOnAction(event -> {
            selectedAccount = accountListView.getSelectionModel().getSelectedItem();
            if (selectedAccount == null) {
                showAlert("Select an account first.");
                return;
            }
            showMainScreen();
        });

        HBox buttons = new HBox(12, addAccountButton, playButton);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        VBox content = new VBox(16, title, accountListView, buttons);
        content.setPadding(new Insets(24));
        content.setAlignment(Pos.TOP_LEFT);
        content.getStyleClass().add("card");

        StackPane root = new StackPane(content);
        root.setPadding(new Insets(32));
        root.getStyleClass().add("root");

        stage.setScene(createScene(root));
    }

    public void showAddAccountScreen() {
        Label title = new Label("Add account");
        title.getStyleClass().add("title");

        TextField nicknameField = new TextField();
        nicknameField.setPromptText("Nickname");

        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            try {
                Account account = accountRepository.save(nicknameField.getText());
                selectedAccount = account;
                showLoginScreen();
            } catch (Exception exception) {
                showAlert("Unable to save account. Nickname may already exist.");
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> showLoginScreen());

        HBox actions = new HBox(12, backButton, saveButton);
        actions.setAlignment(Pos.CENTER_RIGHT);

        VBox card = new VBox(16, title, nicknameField, actions);
        card.setPadding(new Insets(24));
        card.getStyleClass().add("card");

        StackPane root = new StackPane(card);
        root.setPadding(new Insets(32));
        root.getStyleClass().add("root");

        stage.setScene(createScene(root));
    }

    public void showMainScreen() {
        Label nicknameLabel = new Label("Selected: " + selectedAccount.getNickname());
        nicknameLabel.getStyleClass().add("title");

        Button startGameButton = new Button("Start Game");
        startGameButton.setOnAction(event -> {
            try {
                GameLauncher.launch(selectedAccount);
            } catch (Exception exception) {
                showAlert(exception.getMessage());
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> showLoginScreen());

        HBox actions = new HBox(12, backButton, startGameButton);
        actions.setAlignment(Pos.CENTER_RIGHT);

        VBox card = new VBox(16, nicknameLabel, new Label("Ready to launch modded Minecraft."), actions);
        card.setPadding(new Insets(24));
        card.getStyleClass().add("card");

        StackPane root = new StackPane(card);
        root.setPadding(new Insets(32));
        root.getStyleClass().add("root");

        stage.setScene(createScene(root));
    }

    private Scene createScene(Pane root) {
        Scene scene = new Scene(root, 680, 440);
        scene.getStylesheets().add(getClass().getResource("/launcher.css").toExternalForm());
        return scene;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setHeaderText("Neon Launcher");
        alert.showAndWait();
    }
}
