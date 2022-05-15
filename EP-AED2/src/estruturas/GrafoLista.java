package estruturas;

import estruturas.No.Cor;
import interfaces.IGrafo;
import java.util.List;
import java.util.ArrayList;


public class GrafoLista implements IGrafo{
    // atributos
    private List<No> vertices;
    
    // Métodos construtores   

    public GrafoLista(List<String> entrada){
        // Adicionando os vértices
        List<No> vertices = new ArrayList<No>();
        for(String linha : entrada){
            String valor = linha.split(":")[0];
            vertices.add(new No(valor));                       
        }
        // Adicionando as arestas
        for(String linha : entrada){
            String[] separado = linha.split(":");
            if(separado.length > 1){
                String[] vizinhos = separado[1].split(";");
                No cadaVertice = findVertice(separado[0],vertices);
                for(String vizinhoVez : vizinhos){
                    vizinhoVez = vizinhoVez.strip();
                    cadaVertice.addVizinho(findVertice(vizinhoVez, vertices));
                }          
            }
        }
        this.vertices = vertices;
    }

    private GrafoLista(List<No> vertices, char x){
        this.vertices = vertices;
    }

    private GrafoLista(List<Componente> componentes, int x){
        List<No> grafoDeComponentes = new ArrayList<No>();
        for (Componente componente : componentes){
            No aux = new No(componente.toString());
            grafoDeComponentes.add(aux);
        }
        for (Componente componente : componentes){
            No aux1 = findVertice(componente.toString(),grafoDeComponentes);
            for (Componente vizinho:componente.getLigacoes()){
                aux1.addVizinho(findVertice(vizinho.toString(),grafoDeComponentes));
            }
        }
        this.vertices = grafoDeComponentes;
        
    }

    public ArrayList<No> DFS(List<No> ordemDFS){
        ArrayList<No> topologica = new ArrayList<No>();
        for (No vert : ordemDFS){           
            if(vert.getCor() == Cor.BRANCO){
                DFSVisit(vert, topologica);
            }
        }
        return topologica;
    }

    public void DFSVisit(No vert, List<No> topologica){
        vert.setCor(Cor.CINZA);
        for (No vizinho : vert.getVizinhos()){
            if(vizinho.getCor() == Cor.BRANCO){
                vizinho.setPai(vert);
                DFSVisit(vizinho, topologica);
            }
        }
        vert.setCor(Cor.PRETO);
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
        List<No> ordem = this.vertices;        
        ordem = this.DFS(ordem); 
        limpaVertices(); 
        transposto.DFS(transposto.mudaVizinhos(ordem));
        Floresta floresta = new Floresta(transposto.vertices);
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
                
        List<No> componentesDFS = this.DFS(this.getVertices());
        for (No componente : componentesDFS){
            System.out.printf(componente+" ");
        }
        System.out.printf("\n");

        for (No item : vertices) {
            System.out.printf(item+": ");
            for(No vizinho : item.getVizinhos()){
                System.out.printf(vizinho +"; ");
            }
            System.out.printf("\n");
        }
    }

    public int getQtdVertices(){
        
        return vertices.size();
    }

    public List<No> getVertices(){
        return this.vertices;
    }

    public static No findVertice(String valor, List<No> lista){
        for(No item : lista){
            if(item.getValor().equals(valor)) return item;
        }
        return null;
    }

    public IGrafo getArestasTranspostas(){
        // Hashmap (exemplo no construtor)
        ArrayList<No> auxiliar = new ArrayList<No>();    
        for(No item : vertices){
            No aux = new No(item.getValor());
            auxiliar.add(aux);
        }
        for(No item : vertices){ 
            for(No itemcompare : vertices){ 
                for(No vizinho : itemcompare.getVizinhos()){ 
                    if(vizinho == item){
                       No auxi = findVertice(item.getValor(),auxiliar);
                       auxi.addVizinho(findVertice(itemcompare.getValor(),auxiliar)); 
                    }
                }
                
            }
            
        }
        GrafoLista transpostas = new GrafoLista(auxiliar,'a');
        return transpostas;
    }

    @Override
    public String toString(){
        String saida = "";
        for (No item : vertices) {
            saida = saida + item.toString() + " ";    
        }
        return saida;
    }

}