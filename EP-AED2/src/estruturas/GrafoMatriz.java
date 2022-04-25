package estruturas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import interfaces.IGrafo;

public class GrafoMatriz {
    private String[] valores;
    private boolean[][] arestas;
    private int[] pais;
    private final int qtdVertices;

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
            this.valores[verticeId] = entradaQuebrada[0];
            todosVizinhos[verticeId] = entradaQuebrada[1].split(";");
            this.pais[verticeId] = -1;

            conversorDeValor.put(entradaQuebrada[0], verticeId);
        }

        //Adicionando os vizinhos
        for(int verticeId = 0; verticeId < qtdVertices; verticeId++) {
            for(String vizinho : todosVizinhos[verticeId])
                this.arestas[verticeId][conversorDeValor.get(vizinho.trim())] = true;
        }
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
