package com.criiscz.evaart_reto.connection;

import com.criiscz.evaart_reto.utils.SqlInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSQL implements SqlInfo {
    private static Connection conn;

    public static Connection getConnection() throws SQLException {
        if(conn == null){
            conn = DriverManager.getConnection(URL_SQL, USER_SQL, PASS_SQL);
        }
        return conn;
    }
}
