package com.neonlauncher;

import com.neonlauncher.db.AccountRepository;
import com.neonlauncher.db.Database;
import com.neonlauncher.ui.LauncherView;
import javafx.application.Application;
import javafx.stage.Stage;

public class LauncherApp extends Application {
    @Override
    public void start(Stage stage) {
        Database.init();
        LauncherView launcherView = new LauncherView(stage, new AccountRepository());
        stage.setTitle("Neon Launcher");
        stage.setResizable(false);
        launcherView.showLoginScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
