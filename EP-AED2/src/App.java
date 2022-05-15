import java.util.List;

import estruturas.GrafoMatriz;
import estruturas.GrafoLista;
import interfaces.IGrafo;
import funcionalidades.Entrada;

public class App {
    public static void main(String[] args) throws Exception {
        Entrada entrada = Entrada.pegarEntrada(args[0]);
        
        //List<String> entrada = Entrada.pegarEntrada(args[0]);
        //String estrutura = entrada.get(entrada.size()-1);
        //entrada.remove(entrada.size()-1);

        IGrafo g = null;
        if(entrada.getEstrutura().equals("1"))
            g = new GrafoLista(entrada.getEntrada());
        else if(entrada.getEstrutura().equals("2"))
            g = new GrafoMatriz(entrada.getEntrada());
  
        IGrafo resul = g.kosaraju();
        System.out.println(resul.getQtdVertices() == 1 ? "Sim":"Não");
        System.out.println(resul.getQtdVertices());
        resul.imprimeGrafo();
    }

    public static List<String> caso1() {
        return List.of(
            "a: b;",
            "b: c; e; f;",
            "c: d; g;",
            "d: c; h;",
            "e: a; f;",
            "f: g;",
            "g: f; h;",
            "h: h;"
        );
    }

    public static List<String> caso2() {
        return List.of(
            "undershorts: pants; shoes;",
            "pants: belt; shoes;",
            "belt: jacket;",
            "shirt: belt; tie;",
            "tie: jacket;",
            "jacket:",
            "socks: shoes;",
            "shoes:",
            "watch:"
        );
    }

    public static List<String> caso3() {
        return List.of(
            "a: b; c;",
            "b: e;",
            "c: e;",
            "e: a;"
        );
    }
}
