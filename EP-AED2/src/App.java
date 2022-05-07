import java.util.List;

import estruturas.GrafoMatriz;
import interfaces.IGrafo;

public class App {
    public static void main(String[] args) throws Exception {
        IGrafo g = new GrafoMatriz(List.of(
            "a: b;",
            "b: c; e; f;",
            "c: d; g;",
            "d: c; h;",
            "e: a; f;",
            "f: g;",
            "g: f; h;",
            "h: h;"
            ));
        
        IGrafo resul = g.kosaraju();
        resul.imprimeGrafo();
    }
}
