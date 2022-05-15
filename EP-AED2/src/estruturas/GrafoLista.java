package estruturas;

import estruturas.No.Cor;
import interfaces.IGrafo;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;


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
        for (Componente componente : componentes)
            grafoDeComponentes.add(new No(componente.toString()));

        for (Componente componente : componentes){
            No achado = findVertice(componente.toString(),grafoDeComponentes);

            for (Componente vizinho:componente.getLigacoes()){
                achado.addVizinho(findVertice(vizinho.toString(),grafoDeComponentes));
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

    public IGrafo kosaraju(){
        GrafoLista transposto =(GrafoLista) this.getArestasTranspostas();
        List<No> ordem = this.vertices;        
        ordem = this.DFS(ordem); 
        limpaVertices(); 
        transposto.DFS(transposto.getVertices());
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
        Map<String, No> mapaVersTrans = new HashMap<>();
        List<No> novosVertices = new ArrayList<>();

        //Gerando os nos pela primeira vez
        for(No atual : vertices) {
            No novoNoTransposto = new No(atual.getValor());

            mapaVersTrans.put(atual.getValor(), novoNoTransposto);
            novosVertices.add(novoNoTransposto); //Ja guardando os enderecos para gerar o GrafoLista
        }

        //Adicionando os vizinhos
        for(No atual : vertices) {
            No vizinhoTransposto = mapaVersTrans.get(atual.getValor());

            for(No viz : atual.getVizinhos())
                mapaVersTrans.get(viz.getValor()).addVizinho(vizinhoTransposto);
        }

        return new GrafoLista(novosVertices, 'x');
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