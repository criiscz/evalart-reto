package com.criiscz.evaart_reto.presenters;

import com.criiscz.evaart_reto.logic.ManageTables;
import com.criiscz.evaart_reto.views.Console;

public class Presenter {
    private ManageTables model;
    private Console view;

    public Presenter() {
        model = new ManageTables();
        view = new Console(model.getTables());
    }

    public static void main(String[] args) {
        new Presenter();
    }
}
