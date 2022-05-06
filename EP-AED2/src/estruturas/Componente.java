package estruturas;

import java.util.ArrayList;
import java.util.List;

public class Componente {
    private List<String> vertices;
    private List<Componente> ligacoes;

    public Componente(List<String> vertices) {
        this.vertices = vertices;
        this.ligacoes = new ArrayList<>();
    }

    public boolean contemVertice(String vertice) {
        return vertices.contains(vertice);
    }

    public void addLigacao(Componente componente) {
        if(componente == null)
            return;

        ligacoes.add(componente);
    }

    public List<Componente> getLigacoes() {
        return this.ligacoes;
    }

    public List<String> getVertices() {
        return vertices;
    }
}
