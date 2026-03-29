package com.neonlauncher.db;

import com.neonlauncher.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository {

    public List<Account> getAll() {
        List<Account> result = new ArrayList<>();
        try (Connection connection = Database.connect();
             PreparedStatement statement = connection.prepareStatement("SELECT id, nickname FROM accounts ORDER BY nickname ASC");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                result.add(new Account(resultSet.getInt("id"), resultSet.getString("nickname")));
            }
            return result;
        } catch (SQLException exception) {
            throw new IllegalStateException("Failed to load accounts", exception);
        }
    }

    public Account save(String nickname) {
        String cleanNickname = nickname == null ? "" : nickname.trim();
        if (cleanNickname.isEmpty()) {
            throw new IllegalArgumentException("Nickname cannot be empty");
        }

        try (Connection connection = Database.connect();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO accounts(nickname) VALUES(?)")) {
            statement.setString(1, cleanNickname);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new IllegalStateException("Failed to save account: " + cleanNickname, exception);
        }

        return getAll().stream()
                .filter(account -> account.getNickname().equalsIgnoreCase(cleanNickname))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Unable to load saved account"));
    }
}
