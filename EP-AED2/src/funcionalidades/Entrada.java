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

    //private 

/*
    public void setEntrada(List<String> entrada){
        this.entrada = entrada;
    }

    public void setEstrutura(String estrutura){
        this.estrutura = estrutura;
    } 
*/
    public Entrada(List<String> entrada, String estrutura){
        this.entrada = entrada;
        this.estrutura = estrutura;
    }
        
    public static Entrada pegarEntrada(String argumento) throws FileNotFoundException{
        List<String> entrada = new ArrayList<String>();
        
        File arquivo = new File(argumento);
        Scanner scanner = new Scanner(arquivo);
        while (scanner.hasNextLine()){
            entrada.add(scanner.nextLine());
        }
        scanner.close();
        String estrutura = entrada.get(entrada.size()-1);
        entrada.remove(entrada.get(entrada.size()-1));
        entrada.remove(entrada.get(0));
        return new Entrada(entrada, estrutura);
    }
}