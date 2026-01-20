package com.example.thread.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseImpl implements Database {

    private final String _url;

    public DatabaseImpl(String url) {
        _url = url;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(_url);
    }

}
