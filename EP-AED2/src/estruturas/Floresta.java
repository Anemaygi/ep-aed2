package estruturas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Floresta {
    private Map<String, List<String>> filhos;
    private List<String> raizes;

    //Construtores
    private Floresta() {
        this.filhos = new HashMap<>();
        this.raizes = new ArrayList<>();
    }

    public Floresta(String[] nomes, int[] pais) {
        this();

        for(int i = 0; i < pais.length; i++) {
            if(pais[i] == -1) //Nao possui pai
                raizes.add(nomes[i] + "");
            else {
                String pai = nomes[pais[i]];

                if(!filhos.containsKey(pai + ""))
                    filhos.put(pai + "", new ArrayList<>());

                filhos.get(pai + "").add(nomes[i] + "");
            }
        }
    }

    public Floresta(List<No> vertices) {
        this();

        for(No v : vertices) {
            if(v.getPai() == null)
                raizes.add(v.toString());
            else {
                No pai = v.getPai();

                if(!filhos.containsKey(pai.toString()))
                    filhos.put(pai.toString(), new ArrayList<>());

                filhos.get(pai.toString()).add(v.toString());
            }
        }
    }

    //Metodos
    public List<Componente> geraComponentes() {
        List<Componente> ret = new ArrayList<>();

        for(String raiz: raizes) {
            Componente c = new Componente(getArvoreComoLista(raiz));
            ret.add(c);
        }

        return ret;
    }

    private List<String> getArvoreComoLista(String raiz) {
        List<String> ret = new ArrayList<>();

        if(!filhos.containsKey(raiz.toString())) { //Nao possui filhos
            ret.add(raiz.toString());
            return ret;
        }

        for(String filho : filhos.get(raiz.toString()))
            ret.addAll(getArvoreComoLista(filho));
        ret.add(raiz.toString());

        return ret;
    }

    public static Componente achaComponente(String nomeVer, List<Componente> componentes) {
        for(Componente c : componentes)
            if(c.contemVertice(nomeVer))
                return c;

        return null;
    }
}