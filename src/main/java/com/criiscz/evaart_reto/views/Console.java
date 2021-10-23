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
            StringBuilder line = new StringBuilder();
            for(Client client : table.getClients()) {
                line.append(client.getCode()).append(",");
            }
            if(table.isCancelled()) System.out.println("CANCELADA");
            else System.out.println(line.substring(0,line.length()-1));
        }
    }
}
