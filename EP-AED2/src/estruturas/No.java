package estruturas;

import java.util.List;
import java.util.ArrayList;

public class No {
    // Atributos
    private String valor;
    private List<No> vizinhos;
    private No pai;
    public static enum Cor{
        BRANCO, CINZA, PRETO
    }
    Cor cor;

    // Métodos
    public No(String valor){
        this.valor = valor;
        this.cor = Cor.BRANCO;
        this.pai = null;
        this.vizinhos = new ArrayList<No>();
    }

    public Cor getCor(){
        return this.cor;
    }

    public void setCor(Cor cor){
        this.cor = cor;
    }

    public String getValor(){
        return this.valor;
    }

    public No getPai(){
        return this.pai;
    }

    public void setPai(No pai){
        this.pai = pai;
    }

    public void addVizinho(No vizinho){
        this.vizinhos.add(vizinho);
    }

    public List<No> getVizinhos(){
        return this.vizinhos;
    }

    @Override
    public String toString(){
        return this.valor;
    }
}