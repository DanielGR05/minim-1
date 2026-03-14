package models;


public class LP {
    private int quantitat;
    private String identificador;

    public LP (int q, String s){
        quantitat = q;
        identificador = s;
    }

    public int getQuantitat(){
        return quantitat;
    }

    public String getIdentificador(){
        return identificador;
    }
}
