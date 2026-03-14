package models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Order {
    private String dni;
    private List<LP> comanda;

    public Order(String dni) {
        this.dni = dni;
        this.comanda = new ArrayList<>();
    }

    public void addLP(int i, String s) {
        this.comanda.add(new LP(i,s));
    }

    public String getUser() {
        return this.dni;
    }

    public List<LP> getComanda() {
        return this.comanda;
    }
}
