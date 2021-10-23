package com.criiscz.evaart_reto.logic;

import com.criiscz.evaart_reto.dao.ClientDAO;
import com.criiscz.evaart_reto.models.Client;
import com.criiscz.evaart_reto.models.Table;
import com.criiscz.evaart_reto.utils.FileUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.criiscz.evaart_reto.utils.EncryptUtils.decrypt;
import static com.criiscz.evaart_reto.utils.FIleNames.INPUT_DATA;

public class ManageTables {
    private List<Table> tables;
    private ClientDAO clientDAO;

    public ManageTables() {
        this.clientDAO = new ClientDAO();
        this.tables = new ArrayList<>();
        initProcess();
    }

    private void initProcess() {
        try {
            createTables();
            fillTables();
        } catch (IOException | URISyntaxException | SQLException e) {
            e.printStackTrace();
        }

    }

    private void fillTables() throws SQLException, IOException {
        for (Table table : tables) {
            fillTable(table);
        }
    }

    private void fillTable(Table table) throws SQLException, IOException {
        List<Client> clients = clientDAO.getClients(table.getFilters());
        HashMap<String, Integer> filters = table.getFilters();
        if (filters.containsKey("RF") || filters.containsKey("RI")) {
            if (filters.containsKey("RF"))
                clients = clients.stream().filter(client -> client.calculateTotalAccount() < filters.get("RF")).collect(Collectors.toList());
            if (filters.containsKey("RI"))
                clients = clients.stream().filter(client -> client.calculateTotalAccount() > filters.get("RI")).collect(Collectors.toList());
        }
        if (clients.size() < 4) {
            table.setCancelled(true);
            return;
        }
        clients.sort(Comparator.comparingDouble(Client::calculateTotalAccount));
        for (Client client : clients) {
            if (table.getClients().stream().noneMatch(_client -> _client.getCompany() == client.getCompany())) {
                if (client.isEncrypt())
                    client.setCode(decrypt(client.getCode()));
                table.addClient(client);
            }
        }
        Table tba = reduceTable(table);
        table.setClients(tba.getClients());

    }

    private Table reduceTable(Table table) {
        long male = table.getClients().stream().filter(Client::isMale).count();
        long female = table.getClientsLength() - male;
        if (male != female || table.getClientsLength() > 8) {
            long minor = Math.min(male, female);
            if (male >= 4 && female >= 4) return selectEightTable(table);
            else return selectMinorTable(table, minor);
        }
        return table;
    }

    private Table selectMinorTable(Table table, long minor) {
        if(minor < 2) return null;

        Table newTable = new Table(table.getName());
        List<Client> clients = table.getClients();
        long male = 0, female = 0;
        for (int i = 0; i < (minor*2); i++) {
            newTable.addClient(clients.get(i));
            if(clients.get(i).isMale()) male++;
            else female++;
        }

        List<Client> options = clients.subList((int) (minor * 2), table.getClientsLength());
        for (int i = (int) ((minor * 2) - 1); i >= 0; i--) {
            if (balanceGenreList(newTable, male, female, options, i)) break;
        }
        return newTable;


    }

    private boolean balanceGenreList(Table newTable, long male, long female, List<Client> options, int i) {
        if (checkTableBalanced(newTable)) return true;
        if (female < male) {
            if (newTable.getClients().get(i).isMale()) {
                swapClients(newTable, options, i, false);
            }
        } else {
            if (!newTable.getClients().get(i).isMale()) {
                swapClients(newTable, options, i, true);
            }
        }
        return false;
    }

    private Table selectEightTable(Table table) {
        Table newTable = new Table(table.getName());
        List<Client> clients = table.getClients();
        int male = 0, female = 0;

        for (int i = 0; i < 8; i++) {
            newTable.addClient(clients.get(i));
            if(clients.get(i).isMale()) male++;
            else female++;
        }

        List<Client> options = table.getClients().subList(8, table.getClientsLength()-1);
        for (int i = 7; i >= 0; i--) {
            if (balanceGenreList(newTable, male, female, options, i)) break;
        }
        return newTable;

    }

    private void swapClients(Table newTable, List<Client> options, int i, boolean isMale) {
        for (int j = options.size() - 1; j >= 0; j--) {
            if(options.get(j).isMale() == isMale){
                newTable.getClients().set(i, options.get(j));
                options.remove(options.get(j));
                break;
            } else {
                options.remove(options.get(j));
            }
        }
//        for (Client client : options){
//            if(client.isMale() == isMale){
//                newTable.getClients().set(i, client);
//                options.remove(client);
//                break;
//            }else {
//                options.remove(client);
//            }
//        }
    }


    private boolean checkTableBalanced(Table table){
        long male = table.getClients().stream().filter(Client::isMale).count();
        long female = table.getClientsLength() - male;
        return male == female;
    }

    private void createTables() throws IOException, URISyntaxException {
        String data = FileUtils.readFile(INPUT_DATA);
        String[] dataArray = data.replaceFirst("<", "").split("<");
        int tableNumber = dataArray.length - 1;
        for (int i = 0; i < tableNumber; i++) {
            createTable(dataArray[i]);
        }
    }

    private void createTable(String dataTable) {
        String[] data = dataTable.replace(">", "").split("\n");
        String title = data[0];
        Table table = new Table(title);
        for (int i = 1; i < data.length; i++) {
            String[] dataFilter = data[i].split(":");
            table.addFilter(dataFilter[0], Integer.valueOf(dataFilter[1]));
        }
        tables.add(table);
    }

    public List<Table> getTables() {
        return tables;
    }


}
