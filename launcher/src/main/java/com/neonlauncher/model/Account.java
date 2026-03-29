package com.neonlauncher.model;

public class Account {
    private final int id;
    private final String nickname;

    public Account(int id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public String toString() {
        return nickname;
    }
}
