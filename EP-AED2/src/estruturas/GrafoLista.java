package estruturas;

import estruturas.No;
import interfaces.IGrafo;
import java.util.List;
import java.util.ArrayList;

public class GrafoLista implements IGrafo{
    // atributos
    private List<No> vertices;
    
    // Métodos construtores   
    public GrafoLista(String entrada){
        // constructor 1
    }

    
    public GrafoLista(List<Componente> componentes){
        // constructor 2
    }
    
    // Métodos da interface
    
    public IGrafo kosaraju(){

    }

    public void imprimeGrafo(){

    }

    public int getQtdVertices(){

    }

    public IGrafo getArestasTranspostas(){

    }

    // Métodos do GrafoLista
    
    private GrafoLista geraGrafoComponentes(){

    }

}