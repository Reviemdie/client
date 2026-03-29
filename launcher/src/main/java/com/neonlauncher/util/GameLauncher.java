package com.neonlauncher.util;

import com.neonlauncher.model.Account;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class GameLauncher {

    private static final Path DEFAULT_GAME_DIR = Path.of(System.getProperty("user.home"), ".minecraft");
    private static final Path DEFAULT_GAME_JAR = DEFAULT_GAME_DIR.resolve("forge-client.jar");

    private GameLauncher() {
    }

    public static void launch(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("No account selected");
        }

        String javaExecutable = Path.of(System.getProperty("java.home"), "bin", isWindows() ? "java.exe" : "java").toString();
        String gameJar = System.getProperty("neon.gameJar", DEFAULT_GAME_JAR.toString());
        String gameDir = System.getProperty("neon.gameDir", DEFAULT_GAME_DIR.toString());

        if (!Files.exists(Path.of(gameJar))) {
            throw new IllegalStateException("Minecraft jar not found: " + gameJar);
        }

        ProcessBuilder processBuilder = new ProcessBuilder(
                javaExecutable,
                "-jar",
                gameJar,
                "--username", account.getNickname(),
                "--version", "NeonClient-Forge-1.16.5",
                "--gameDir", gameDir,
                "--assetsDir", Path.of(gameDir, "assets").toString(),
                "--assetIndex", "1.16"
        );

        processBuilder.directory(Path.of(gameDir).toFile());
        processBuilder.inheritIO();

        try {
            processBuilder.start();
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to launch game process", exception);
        }
    }

    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }
}
