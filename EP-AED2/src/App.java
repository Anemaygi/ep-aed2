import java.util.List;

import estruturas.GrafoMatriz;
import interfaces.IGrafo;

public class App {
    public static void main(String[] args) throws Exception {
        IGrafo g = new GrafoMatriz(caso2());
        
        IGrafo resul = g.kosaraju();
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
