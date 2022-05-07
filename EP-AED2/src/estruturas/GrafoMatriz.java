package estruturas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import estruturas.No.Cor;
import interfaces.IGrafo;

public class GrafoMatriz implements IGrafo {
    private String[] valores;
    private boolean[][] arestas;
    private int[] pais;
    private final int qtdVertices;

    // === CONSTRUTORES === //
    public GrafoMatriz(List<String> vertices) {
        this.qtdVertices = vertices.size();
        this.valores = new String[qtdVertices];
        this.arestas = new boolean[qtdVertices][qtdVertices];
        this.pais = new int[qtdVertices];

        String[][] todosVizinhos = new String[qtdVertices][];
        //Como teremos que converter varios valores dos vizinhos para ids, um map acelera o processo
        Map<String, Integer> conversorDeValor = new HashMap<>();

        //Lendo todos os valores dos vertices
        for(int verticeId = 0; verticeId < qtdVertices; verticeId++) {
            String vertice = vertices.get(verticeId);
            String[] entradaQuebrada = vertice.split(": ");

            if(entradaQuebrada.length > 1) {
                this.valores[verticeId] = entradaQuebrada[0];
                todosVizinhos[verticeId] = entradaQuebrada[1].split(";");
            } else {
                this.valores[verticeId] = vertice.substring(0, vertice.length() - 1);
                todosVizinhos[verticeId] = new String[0];
            }
            this.pais[verticeId] = -1;

            conversorDeValor.put(this.valores[verticeId], verticeId);
        }

        //Adicionando os vizinhos
        for(int verticeId = 0; verticeId < qtdVertices; verticeId++) {
            for(String vizinho : todosVizinhos[verticeId]) {
                this.arestas[verticeId][conversorDeValor.get(vizinho.trim())] = true;
            }
        }
    }

    private GrafoMatriz(String[] vertices) {
        this.qtdVertices = vertices.length;
        this.arestas = new boolean[qtdVertices][qtdVertices];
        this.pais = new int[qtdVertices];
        this.valores = new String[qtdVertices];

        for(int i = 0; i < qtdVertices; i++) {
            this.valores[i] = vertices[i];
            this.pais[i] = -1;
        }
    }

    private GrafoMatriz(Componente[] componentes) {
        this.qtdVertices = componentes.length;
        this.valores = new String[qtdVertices];
        this.arestas = new boolean[qtdVertices][qtdVertices];
        this.pais = new int[qtdVertices];

        Map<Componente, Integer> conversorDeValor = new HashMap<>();

        //Fazendo a leitura dos vertices
        for(int i = 0; i < qtdVertices; i++) {
            this.pais[i] = -1;
            this.valores[i] = componentes[i].toString();
            conversorDeValor.put(componentes[i], i);
        }

        //Fazendo a leitura dos vizinhos
        for(int atual = 0; atual < qtdVertices; atual++)
            for(Componente outro : componentes[atual].getLigacoes()) {
                final int outroIndex = conversorDeValor.get(outro);
                this.arestas[atual][outroIndex] = true;
            }
    }

    // === KOSARAJU === //
    public IGrafo kosaraju() {
        GrafoMatriz transposto = (GrafoMatriz) this.getArestasTranspostas();
        List<Integer> ordemTopo = buscaEmProfundidade();
        transposto.buscaEmProfundidade(ordemTopo);

        Floresta floresta = new Floresta(transposto.valores, transposto.pais);
        List<Componente> componentes = floresta.geraComponentes();

        for(int atual = 0; atual < qtdVertices; atual++) {
            Componente compAtual = Floresta.achaComponente(valores[atual], componentes);

            for(int outro = 0; outro < qtdVertices; outro++) {
                if(arestas[atual][outro] == false)
                    continue;

                compAtual.addLigacao(Floresta.achaComponente(valores[outro], componentes));
            }
        }

        return new GrafoMatriz(componentes.toArray(new Componente[componentes.size()]));
    }

    private List<Integer> buscaEmProfundidade() {
        //Usaremos a ordem comum dos vertices
        return buscaEmProfundidade(IntStream.range(0, qtdVertices).boxed().collect(Collectors.toList()));
    }

    private List<Integer> buscaEmProfundidade(List<Integer> ordemASeguir) {
        List<Integer> ordemTopo = new ArrayList<Integer>();

        //Inicializando as cores
        Cor[] cores = new Cor[qtdVertices];
        for(int i = 0; i < qtdVertices; i++)
            cores[i] = Cor.BRANCO;

        //Loop principal
        int atual = ordemASeguir.get(0);
        while(atual != -1) {
            buscaEmNo(atual, cores, ordemTopo);
            atual = pegarProximoVerDisponivel(ordemASeguir, cores);
        }       

        return ordemTopo;
    }

    private int pegarProximoVerDisponivel(List<Integer> ordemASeguir, Cor[] cores) {
        for(int i : ordemASeguir)
            if(cores[i] == Cor.BRANCO)
                return i;

        return -1;
    }

    private void buscaEmNo(int atual, Cor[] cores, List<Integer> ordemTopo) {
        cores[atual] = Cor.CINZA;

        for(int viz = 0; viz < qtdVertices; viz++) {
            if(arestas[atual][viz] == false || //Nao eh um vizinho
               cores[viz] != Cor.BRANCO)       //O vizinho ja foi explorado
                continue;

            buscaEmNo(viz, cores, ordemTopo);
            pais[viz] = atual;
        }

        cores[atual] = Cor.PRETO;
        ordemTopo.add(0, atual);
    }

    public IGrafo getArestasTranspostas() {
        GrafoMatriz transposta = new GrafoMatriz(this.valores);

        for(int i = 0; i < qtdVertices; i++)
            for(int j = 0; j < qtdVertices; j++) {
                if(this.arestas[i][j] == !this.arestas[j][i])
                    transposta.arestas[i][j] = !this.arestas[i][j];
                else if (this.arestas[i][j] && this.arestas[j][i])
                    transposta.arestas[i][j] = true;
            }

        return transposta;
    }

    // === OUTROS METODOS === //
    public int getQtdVertices() {
        return this.qtdVertices;
    }

    public void imprimeGrafo() {
        String todosVertices = "";
        int[] tamanhos = new int[qtdVertices];
        int tamanhoMaximo = -1;

        //Pegando os tamanhos para ajustar a tabela
        for(int verticeId = 0; verticeId < qtdVertices; verticeId++) {
            String vertice = valores[verticeId];
            todosVertices += vertice + " ";
            final int tamanho = vertice.length();

            if(tamanhoMaximo < tamanho)
                tamanhoMaximo = tamanho;
                tamanhos[verticeId] = tamanho;
        }

        //(tamanhoMaximo + 1) -> dando o devido espaco para a legenda vertical
        System.out.println(String.format("%-" + (tamanhoMaximo + 1) + "s", "") + todosVertices);

        //Imprimindo cada linha da tabela
        for(int i = 0; i < qtdVertices; i++) {
            String linha = String.format("%" + (tamanhoMaximo + 1) + "s", valores[i]);

            for(int j = 0; j < qtdVertices; j++) {
                String val = arestas[i][j] ? "1" : "0";
                linha += String.format("%" + tamanhos[j] + "s", val) + " ";
            }

            System.out.println(linha);
        }
    }
}
