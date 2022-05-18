import java.util.Scanner;

import estruturas.GrafoMatriz;
import estruturas.GrafoLista;
import interfaces.IGrafo;
import funcionalidades.Entrada;

public class App {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws Exception {
        //Lendo a entrada
        Entrada entrada = Entrada.pegarEntrada(args[0]);
 
        IGrafo g = null;
        if(entrada.getEstrutura().equals("1"))
            g = GrafoLista.leGrafoDeEntrada(entrada.getEntrada());
        else if(entrada.getEstrutura().equals("2"))
            g = GrafoMatriz.leGrafoDeEntrada(entrada.getEntrada());

        //Criando o grafo de componentes
        IGrafo resul = g.kosaraju();
        System.out.println(resul.getQtdVertices() == 1 ? "Sim":"Não");
        System.out.println(resul.getQtdVertices());
        resul.imprimeGrafo();

        //Iniciando o exemplo de aplicacao aplicacao
        System.out.println("Usar grafo de componentes gerado para o e exemplo de aplicacao? [s/n]");
        final String lido = sc.nextLine();

        if(lido.equals("s"))
            usarComoAplicacao(resul);

        sc.close();
    }

    public static void usarComoAplicacao(IGrafo grafoComponente) {
        System.out.println("=== Exemplo de aplicacao: simulacao de micro servicos ===");
        System.out.println("-> Os vertices anteriores representam micro servicos");
        System.out.println("-> As arestas representam as dependencias entre eles");
        System.out.println("-> Os componentes representam deadlocks\n");

        String opcao = "";
        do {
            System.out.println("Digite o nome do micro servico a ser derrubado: ");
            final String valorVertice = sc.nextLine();

            final String resul = grafoComponente.getVizinhosEmProfundidade(valorVertice);
            System.out.println();

            if(resul == null)
                System.out.println("Micro servico nao presente");
            else {
                System.out.println("Caso o micro servico '" + valorVertice + "' caisse, os seguintes micro servicos cairiam: ");
                System.out.println(resul);
            }

            System.out.println("Deseja executar mais uma simulacao? [s/n]");
            opcao = sc.nextLine();
            System.out.println();
        } while(opcao.equals("s"));
    }
}
