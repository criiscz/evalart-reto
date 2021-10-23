package com.criiscz.evaart_reto.models;

import java.util.List;

public class Client {
    private int id;
    private String code;
    private boolean male;
    private short type;
    private int location;
    private int company;
    private boolean encrypt;
    private List<Account> accounts;

    public Client(int id, String code, boolean male, short type, int location, int company, boolean encrypt, List<Account> accounts) {
        this.id = id;
        this.code = code;
        this.male = male;
        this.type = type;
        this.location = location;
        this.company = company;
        this.encrypt = encrypt;
        this.accounts = accounts;
    }

    public double calculateTotalAccount(){
        return accounts.stream().mapToDouble(Account::getBalance).sum();
    }

    public void setCode(String code){
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public boolean isMale() {
        return male;
    }

    public short getType() {
        return type;
    }

    public int getLocation() {
        return location;
    }

    public int getCompany() {
        return company;
    }

    public boolean isEncrypt() {
        return encrypt;
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}
