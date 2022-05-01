package estruturas;

import estruturas.No;
import interfaces.IGrafo;
import java.util.List;
import java.util.ArrayList;

public class GrafoLista implements IGrafo{
    // atributos
    private List<No> vertices;
    
    // Métodos construtores   
    public GrafoLista(/*String entrada*/){
        // TESTE SEM ENTRADA
        No na = new No("a");
        No nb = new No("b");
        No nc = new No("c");
        No nd = new No("d");
        No ne = new No("e");
        No nf = new No("f");
        No ng = new No("g");
        No nh = new No("h");

        na.addVizinho(nb);
        nb.addVizinho(nc);
        nb.addVizinho(ne);
        nb.addVizinho(nf);
        nc.addVizinho(nd);
        nc.addVizinho(ng);
        nd.addVizinho(nc);
        nd.addVizinho(nh);
        ne.addVizinho(na);
        ne.addVizinho(nf);
        nf.addVizinho(ng);
        ng.addVizinho(nf);
        ng.addVizinho(nh);
        nh.addVizinho(nh);

        ArrayList<No> vertices = new ArrayList<No>();
        vertices.add(na);
        vertices.add(nb);
        vertices.add(nc);
        vertices.add(nd);
        vertices.add(ne);
        vertices.add(nf);
        vertices.add(ng);
        vertices.add(nh);
        this.vertices = vertices;
    }

    //teste

    public GrafoLista(List<No> vertices){
        this.vertices = vertices;
    }
    /*
    
    
    
    
    }
/*

8
a: b;
b: c; e; f;
c: d; g;
d: c; h;
e: a; f;
f: g;
g: f; h;
h: h;
2

 
 //   
    public GrafoLista(List<Componente> componentes){
        // constructor 2
    }
    
    // Métodos da interface
 */   

    public ArrayList<No> DFS(){
        ArrayList<No> ordem = new ArrayList<No>();
        for (No vert : this.vertices){
            if(vert.getCor() == No.Cor.BRANCO){
                DFSVisit(vert, ordem);
            }
        }
        return ordem;
    }

    public void DFSVisit(No vert, List<No> ordem){
        vert.setCor(No.Cor.CINZA);
        for (No vizinho : vert.getVizinhos()){
            if(vizinho.getCor() == No.Cor.BRANCO){
                vizinho.setPai(vert);
                DFSVisit(vizinho, ordem);
            }
        }
        vert.setCor(No.Cor.PRETO);
        ordem.add(0,vert);
    }

    public IGrafo kosaraju(){
        return this;

        /*
        1. Chamar DFS (V, A) para calcular f[u]
        2. Calcular A T
        3. Chamar DFS (V, A T ) considerando no laço principal
os vértices em ordem decrescente de f (calculados na
linha 1)
        4. Devolva os vértices de cada árvore resultante da linha 3 como
uma componente fortemente conectada separada
         */
    }

    public void imprimeGrafo(){
        /* working */        
        for (No item : vertices) {
            System.out.printf(item+": ");
            for(No vizinho : item.getVizinhos()){
                System.out.printf(vizinho +"; ");
            }
            System.out.printf("\n");
        }
    }

    public int getQtdVertices(){
        /* working */
        return vertices.size();
    }

    public IGrafo getArestasTranspostas(){
        
        /* working */
        ArrayList<No> auxiliar = new ArrayList<No>();
        
        for(No item : vertices){
            No aux = new No(item.getValor());
            for(No itemcompare : vertices){
                //if (itemcompare == item) continue;
                for(No vizinho : itemcompare.getVizinhos()){
                    if(vizinho == item){
                       aux.addVizinho(itemcompare); 
                    }
                }
                
            }
            auxiliar.add(aux);
        }
        vertices = auxiliar;
        return this;
    }

    // Métodos do GrafoLista
    /*
    private GrafoLista geraGrafoComponentes(){

    }
*/

    @Override

    public String toString(){
        String saida = "";
        for (No item : vertices) {
            saida = saida + item.toString() + " ";    
        }
        return saida;
    }

    public static void main(String args[]) {
        GrafoLista teste = new GrafoLista();
        System.out.println(teste);
        System.out.println(teste.getQtdVertices());
        teste.imprimeGrafo();
        teste.getArestasTranspostas();
        System.out.println("~~~~");
        //GrafoLista teste2 = new GrafoLista(teste.getArestasTranspostas()); 
        teste.imprimeGrafo();
         System.out.println("~~~~");
        List<No> ordem = teste.DFS();
        for (No no : ordem){
            System.out.printf(no.getValor()+" ");
        }
    }

}