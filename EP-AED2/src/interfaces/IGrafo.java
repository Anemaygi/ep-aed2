package interfaces;

public interface IGrafo {
    IGrafo kosaraju();

    void imprimeGrafo();

    int getQtdVertices();

    IGrafo getArestasTranspostas();
}
