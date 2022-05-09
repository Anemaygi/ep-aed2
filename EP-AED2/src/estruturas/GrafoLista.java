package estruturas;

import estruturas.No;
import estruturas.Floresta;
import estruturas.Componente;
import estruturas.No.Cor;
import interfaces.IGrafo;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class GrafoLista implements IGrafo{
    // atributos
    private List<No> vertices;
    
    // Métodos construtores   
    public GrafoLista(/*String entrada*/){
        // TESTE SEM ENTRADA
        /*No na = new No("a");
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
        */

        No n2 = new No("2"); // 
        No n3 = new No("3"); // 8 e 10
        No n5 = new No("5"); // 11
        No n7 = new No("7"); // 8 e 11
        No n8 = new No("8"); // 9
        No n9 = new No("9"); // 
        No n10 = new No("10"); // 
        No n11 = new No("11");// 2 9 10
        
        n3.addVizinho(n8);
        n3.addVizinho(n10);
        n5.addVizinho(n11);
        n7.addVizinho(n8);
        n7.addVizinho(n11);
        n8.addVizinho(n9);
        n11.addVizinho(n2);
        n11.addVizinho(n9);
        n11.addVizinho(n10);
        

        ArrayList<No> vertices = new ArrayList<No>();
        vertices.add(n2);
        vertices.add(n3);
        vertices.add(n5);
        vertices.add(n7);
        vertices.add(n8);
        vertices.add(n9);
        vertices.add(n10);
        vertices.add(n11);
        this.vertices = vertices;


    }


    public GrafoLista(List<String> entrada){
        // Adicionando os vértices
        List<No> vertices = new ArrayList<No>();
        for(String linha : entrada){
            String[] separado = linha.split(":");
            No aux = new No(separado[0]);
            vertices.add(aux);                       
        }
        // Adicionando as arestas
        for(String linha : entrada){
            String[] separado = linha.split(":");
            if(separado.length > 1){
                String[] vizinhos = separado[1].split(";");
                
                for(No verticeVez : vertices){
                    if(verticeVez.getValor().equals(separado[0])){
                        for(String vizinhoVez : vizinhos){
                            vizinhoVez = vizinhoVez.replace(" ", "");
                            for(No vizinhoA : vertices){
                                boolean truea = vizinhoVez.equals(vizinhoA.getValor());
                                if(vizinhoA.getValor().equals(vizinhoVez)){
                                    
                                    verticeVez.addVizinho(vizinhoA);
                                    break;
                                }
                            }
                        }
                    }
                }            
            }
        }
        this.vertices = vertices;

    }

    private GrafoLista(List<No> componentes, char x){
        this.vertices = componentes;
    }

    private GrafoLista(List<Componente> componentes, int x){
        
        List<No> grafoDeComponentes = new ArrayList<No>();
        for (Componente componente : componentes){
            No aux = new No(componente.toString());
            grafoDeComponentes.add(aux);
        }
        for (Componente componente : componentes){
            for(No item : grafoDeComponentes){
                if (item.getValor().equals(componente.toString())){
                    for (Componente vizinho:componente.getLigacoes()){
                        for(No cada : grafoDeComponentes){
                           
                            if(cada.getValor().equals(vizinho.toString())){
                                
                                item.addVizinho(cada);
                                break;
                            }
                        }
                    }
                }
            }
        }
        this.vertices = grafoDeComponentes;
        
    }

  
    // Métodos da interface
  

    public ArrayList<No> DFS(List<No> ordemDFS){
        ArrayList<No> topologica = new ArrayList<No>();
        for (No vert : ordemDFS){
            /*for(No quem : vert.getVizinhos()){
                System.out.println("o vizinho de "+vert.getValor()+" é "+quem.getValor());
            }*/
            
            
            if(vert.getCor() == Cor.BRANCO){
                System.out.println("visitando " +vert.getValor());
                DFSVisit(vert, topologica);
            }
        }
        return topologica;
    }

    public void DFSVisit(No vert, List<No> topologica){
        //System.out.println("Visitando o "+vert.getValor());
        vert.setCor(Cor.CINZA);
        //System.out.println("Estou cinza! Sou o "+vert.getValor());
        for (No vizinho : vert.getVizinhos()){
            
            if(vizinho.getCor() == Cor.BRANCO){

                //System.out.println("Sou o vizinho de "+vert.getValor() +", o :"+vizinho.getValor());
                vizinho.setPai(vert);
                DFSVisit(vizinho, topologica);
            }
        }
        vert.setCor(Cor.PRETO);
        //System.out.println("Estou Preto! Sou o "+vert.getValor());
        topologica.add(0,vert);
    }

    private void limpaVertices(){
        for (No no : vertices){
            no.setCor(Cor.BRANCO);
        }
    }

    private List<No> mudaVizinhos(List<No> trocaVertice){
        List<No> novo = new ArrayList<No>();
        for(No item : trocaVertice){
            
            for(No cada : this.vertices){
                
                if(cada.getValor().equals(item.getValor())){
                    novo.add(cada);
                    break;
                }
            }
        }
        return novo;
    }

    public IGrafo kosaraju(){

        GrafoLista transposto =(GrafoLista) this.getArestasTranspostas();
  
        //debug
        this.imprimeGrafo();
        

        List<No> ordem = this.vertices;        
        ordem = this.DFS(ordem); // os itens daqui estão com os vizinhos do normal
        System.out.println("Ordenação topologica do normal:");
        for (No item : ordem){
            System.out.printf(item+" ");
        }

        limpaVertices(); // funcionando tb

        
        // ERRO NO DFS DO TRANSPOSTO
        System.out.println("\nTransposto:");
        transposto.imprimeGrafo();
        
        //List<No> ordemt = transposto.mudaVizinhos(ordem);
        /*for(No item:ordemt){
            for(No vizinho:item.getVizinhos()){
                System.out.println("O vizinho de "+item.getValor()+" é "+vizinho.getValor());
            }
        }*/
        List<No> ordemtr = transposto.DFS(transposto.mudaVizinhos(ordem));
        for (No item : ordemtr){
            System.out.printf(item+" ");
        }
         System.out.printf("\n");

        Floresta floresta = new Floresta(vertices);
        List<Componente> componentes = floresta.geraComponentes();
        for(No vertice : vertices){
            Componente compatual = Floresta.achaComponente(vertice.getValor(),componentes);
            for(No vizinho : vertice.getVizinhos()){
                if(compatual == Floresta.achaComponente(vizinho.getValor(),componentes)){
                    continue;
                }
                compatual.addLigacao(Floresta.achaComponente(vizinho.getValor(),componentes));
            }
        }
        
        GrafoLista grafoDeComponentes = new GrafoLista(componentes,0);
        return grafoDeComponentes;
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

    public List<No> getVertices(){
        return this.vertices;
    }

    public IGrafo getArestasTranspostas(){
        
        /* working */

        // Hashmap (exemplo no construtor)
        // Não modificar o original, criar um novo (precisa terminar o constructor pra isso)

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
        GrafoLista transpostas = new GrafoLista(auxiliar,'a');
        
        return transpostas;
    }

    // Métodos do GrafoLista
    /*
    private GrafoLista geraGrafoComponentes(List<No> componentes){
        this.vertices = componentes;
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

    public void resultado(){
        GrafoLista grafoComponentes = (GrafoLista) this.kosaraju();
        System.out.println(grafoComponentes.getQtdVertices() == 1 ? "Sim":"Não");
        System.out.println(grafoComponentes.getQtdVertices());
        List<No> componentesDFS = grafoComponentes.DFS(grafoComponentes.getVertices());
        for (No componente : componentesDFS){
            System.out.printf(componente+" ");
        }
        System.out.printf("\n");
        grafoComponentes.imprimeGrafo();
    }

    
    public static void main(String args[]) {
       /*IGrafo g = new GrafoLista(List.of(
            "a: b;",
            "b: c; e; f;",
            "c: d; g;",
            "d: c; h;",
            "e: a; f;",
            "f: g;",
            "g: f; h;",
            "h: h;"
            ));
        /*g.imprimeGrafo();
            
        GrafoLista teste = new GrafoLista();
        GrafoLista grafoComponentes = (GrafoLista) teste.kosaraju();
        System.out.println(grafoComponentes.getQtdVertices() == 1 ? "Sim":"Não");
        System.out.println(grafoComponentes.getQtdVertices());
        List<No> componentesDFS = grafoComponentes.DFS(grafoComponentes.getVertices());
        for (No componente : componentesDFS){
            System.out.printf(componente+" ");
        }
        System.out.printf("\n");
        grafoComponentes.imprimeGrafo();*/
        GrafoLista teste = new GrafoLista();
        teste.resultado();
    }

}