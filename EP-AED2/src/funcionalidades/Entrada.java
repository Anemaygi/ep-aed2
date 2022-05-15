package funcionalidades;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Entrada{
    private List<String> entrada;
    private String estrutura;

    public List<String> getEntrada(){
        return this.entrada;
    }

    public String getEstrutura(){
        return this.estrutura;
    } 

    public void setEntrada(List<String> entrada){
        this.entrada = entrada;
    }

    public void setEstrutura(String estrutura){
        this.estrutura = estrutura;
    } 

        
    public static Entrada pegarEntrada(String argumento) throws FileNotFoundException{
        Entrada objeto = new Entrada();
        List<String> entrada = new ArrayList<String>();
        
        File arquivo = new File(argumento);
        Scanner scanner = new Scanner(arquivo);
        while (scanner.hasNextLine()){
            entrada.add(scanner.nextLine());
        }
        scanner.close();
        objeto.setEstrutura(entrada.get(entrada.size()-1));
        entrada.remove(entrada.get(0));
        entrada.remove(entrada.get(entrada.size()-1));
        objeto.setEntrada(entrada);

        return objeto;
    }
}