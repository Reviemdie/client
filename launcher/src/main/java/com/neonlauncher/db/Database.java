package com.neonlauncher.db;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class Database {
    private static final String DB_PATH = Path.of(System.getProperty("user.home"), ".neonlauncher", "launcher.db").toString();

    private Database() {
    }

    public static Connection connect() throws SQLException {
        ensureParentDirectory();
        return DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
    }

    public static void init() {
        try (Connection connection = connect(); Statement statement = connection.createStatement()) {
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS accounts (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        nickname TEXT NOT NULL UNIQUE
                    );
                    """);
        } catch (SQLException exception) {
            throw new IllegalStateException("Failed to initialize database", exception);
        }
    }

    private static void ensureParentDirectory() {
        try {
            Files.createDirectories(Path.of(DB_PATH).getParent());
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to create launcher data directory", exception);
        }
    }
}
