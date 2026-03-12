package models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Order {
    private String dni;
    private List<String> comanda;

    public Order(String dni) {
        this.dni = dni;
        this.comanda = new ArrayList<String>();
    }

    public void addLP(int i, String s) {
        for(int j=0; j<i; i++ ){
            this.comanda.add(s);
        }
    }

    public String getUser() {
        return this.dni;
    }

    public List<String> getComanda() {
        return this.comanda;
    }
}
