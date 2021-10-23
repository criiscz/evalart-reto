package com.criiscz.evaart_reto.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Table {
    private List<Client> clients;
    private String name;
    private boolean cancelled;
    private HashMap<String, Integer> filters;


    public Table(String name) {
        this.name = name;
        this.clients = new ArrayList<>();
        this.cancelled = false;
        filters = new HashMap<>();
    }

    public void addFilter(String name, Integer value){
        filters.put(name,value);
    }

    public void setCancelled(boolean cancelled){
        this.cancelled = cancelled;
    }

    public int getClientsLength() {
        return clients.size();
    }

    public List<Client> getClients() {
        return clients;
    }

    public String getName() {
        return name;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public HashMap<String,Integer> getFilters() {
        return filters;
    }

    public void addClient(Client client) {
        clients.add(client);
    }

    public void setClients(List<Client> clients){
        this.clients = clients;
    }
}
