package estruturas;

import estruturas.No.Cor;
import interfaces.IGrafo;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class GrafoLista implements IGrafo{
    // atributos
    private List<No> vertices;
    
    // Métodos construtores e inicializadores   
    public static IGrafo leGrafoDeEntrada(List<String> vertices) {
        // Adicionando os vértices
        List<No> verticesGrafo = new ArrayList<No>();
        for(String linha : vertices){
            String valor = linha.split(":")[0];
            verticesGrafo.add(new No(valor));                       
        }
        // Adicionando as arestas
        for(String linha : vertices){
            String[] separado = linha.split(":");
            if(separado.length > 1){
                String[] vizinhos = separado[1].split(";");
                No cadaVertice = findVertice(separado[0],verticesGrafo);
                for(String vizinhoVez : vizinhos){
                    vizinhoVez = vizinhoVez.strip();
                    cadaVertice.addVizinho(findVertice(vizinhoVez, verticesGrafo));
                }          
            }
        }
        return new GrafoLista(verticesGrafo);
    }

    private GrafoLista(List<No> componentes){
        this.vertices = componentes;
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

    // Métodos da interface
    public IGrafo kosaraju(){
        GrafoLista transposto =(GrafoLista) this.getArestasTranspostas();
        List<No> ordem = this.vertices;        
        ordem = this.DFS(ordem); 
        limpaVertices(); 
        transposto.DFS(ordem);
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

        return new GrafoLista(novosVertices);
    }

    // Métodos da classe

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

    public List<No> getVertices(){
        return this.vertices;
    }

    public static No findVertice(String valor, List<No> lista){
        for(No item : lista){
            if(item.getValor().equals(valor)) return item;
        }
        return null;
    }

    // Métodos da aplicação
   public String getVizinhosEmProfundidade(String valorVertice) {
        final No idVertice = findVerticeComponent(valorVertice, this.vertices);
        if(idVertice == null) return null;
        this.limpaVertices();
        return getVizinhosEmProfundidade(idVertice);
    }

    public static No findVerticeComponent(String valor, List<No> lista){
        for(No item : lista){
            if(List.of(item.getValor().split("-")).contains(valor)) return item;}
        return null;
    }
    
    
    private String getVizinhosEmProfundidade(No atual) {
        String saida = atual.getValor() + " ";
        atual.setCor(Cor.PRETO);
        for(No vizinho : atual.getVizinhos()){
            if(vizinho.getCor() == Cor.BRANCO){
                saida += getVizinhosEmProfundidade(vizinho) + " ";
            }
        }
        return saida;

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