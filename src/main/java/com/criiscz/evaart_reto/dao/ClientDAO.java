package com.criiscz.evaart_reto.dao;

import com.criiscz.evaart_reto.connection.ConnectionSQL;
import com.criiscz.evaart_reto.models.Account;
import com.criiscz.evaart_reto.models.Client;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClientDAO {

    public List<Client> getClients(HashMap<String, Integer> filters) throws SQLException {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM CLIENT WHERE ";
        String filtersString = findFilters(filters);
        sql += filtersString;
        Connection conn = ConnectionSQL.getConnection();
        Statement statement = conn.createStatement();
        System.out.println(sql);
        ResultSet resultSet = statement.executeQuery(sql);

        while(resultSet.next()){
            sql = "SELECT id, balance from account where client_id = " + resultSet.getInt("id");
            Statement statementTwo = conn.createStatement();
            ResultSet resultSetAccount = statementTwo.executeQuery(sql);
            List<Account> accounts = new ArrayList<>();
            while(resultSetAccount.next()) {
                accounts.add(new Account(
                        resultSetAccount.getInt("id"),
                        resultSetAccount.getDouble("balance")
                ));
            }
            clients.add(new Client(
                resultSet.getInt("id"),
                    resultSet.getString("code"),
                    resultSet.getInt("male") == 1,
                    resultSet.getShort("type"),
                    resultSet.getInt("location"),
                    resultSet.getInt("company"),
                    resultSet.getInt("encrypt") == 1,
                    accounts
            ));
        }
        return clients;
    }

    private String findFilters(HashMap<String, Integer> filters) {
        String finalString = "";
        if (filters.containsKey("TC")) finalString += "type = " + filters.get("TC") +" ";
        if (filters.containsKey("UG")) finalString += "and location = " + filters.get("UG");
        if(finalString.startsWith("and")) return finalString.substring(3);
        else return finalString;
    }
}
