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

    public Floresta(int[] pais) {
        this();

        for(int i = 0; i < pais.length; i++) {
            if(pais[i] == -1) //Nao possui pai
                raizes.add(i + "");
            else {
                if(!filhos.containsKey(pais[i] + ""))
                    filhos.put(pais[i] + "", new ArrayList<>());

                filhos.get(pais[i] + "").add(i + "");
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

    private List<String> getArvoreComoLista(String no) {
        List<String> ret = new ArrayList<>();

        if(!filhos.containsKey(no.toString())) { //Nao possui filhos
            ret.add(no.toString());
            return ret;
        }

        for(String filho : filhos.get(no.toString()))
            ret.addAll(getArvoreComoLista(filho));
        ret.add(no.toString());

        return ret;
    }
}