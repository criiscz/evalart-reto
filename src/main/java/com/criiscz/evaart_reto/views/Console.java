package com.criiscz.evaart_reto.views;

import com.criiscz.evaart_reto.models.Client;
import com.criiscz.evaart_reto.models.Table;

import java.util.List;

public class Console {

    public Console(List<Table> data) {
        printTables(data);
    }

    private void printTables(List<Table> data) {
        for(Table table : data) {
            System.out.printf("<%s>\n" ,table.getName());
            for(Client client : table.getClients()) {
                System.out.print(client.getCode()+",");
            }
            System.out.println("");
        }
    }
}
