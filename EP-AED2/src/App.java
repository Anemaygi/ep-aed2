import java.util.List;

import estruturas.GrafoMatriz;
import estruturas.GrafoLista;
import interfaces.IGrafo;
import funcionalidades.Entrada;

public class App {
    public static void main(String[] args) throws Exception {
        List<String> entrada = Entrada.pegarEntrada(args);
        String estrutura = entrada.get(entrada.size()-1);
        entrada.remove(entrada.size()-1);

        if(estrutura.equals("1")){
            IGrafo g = new GrafoLista(entrada);
            IGrafo resul = g.kosaraju();
            System.out.println(resul.getQtdVertices() == 1 ? "Sim":"Não");
            System.out.println(resul.getQtdVertices());
            resul.imprimeGrafo();
        }
        else if(estrutura.equals("2")){
            IGrafo g = new GrafoMatriz(entrada);
            IGrafo resul = g.kosaraju();
            resul.imprimeGrafo();
        }
  
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
