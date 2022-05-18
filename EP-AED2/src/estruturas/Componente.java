package estruturas;

import java.util.ArrayList;
import java.util.List;

public class Componente {
    public static String separador = "-";

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
        if(componente == null || componente == this || ligacoes.contains(componente))
            return;

        ligacoes.add(componente);
    }

    public List<Componente> getLigacoes() {
        return this.ligacoes;
    }

    public List<String> getVertices() {
        return vertices;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((vertices == null) ? 0 : vertices.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Componente other = (Componente) obj;
        if (vertices == null) {
            if (other.vertices != null)
                return false;
        } else if (!vertices.equals(other.vertices))
            return false;
        return true;
    }

    @Override
    public String toString() {
        String ret = "";

        for(String vertice : vertices) {
            ret += vertice + Componente.separador;
        }

        return ret.substring(0, ret.length() - 1);
    }
}
