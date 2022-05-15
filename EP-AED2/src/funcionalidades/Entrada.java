package funcionalidades;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Entrada{
    public static List<String> pegarEntrada(String[] args) throws FileNotFoundException{
        List<String> entrada = new ArrayList<String>();
        
        File arquivo = new File(args[0]);
        Scanner scanner = new Scanner(arquivo);
        while (scanner.hasNextLine()){
            entrada.add(scanner.nextLine());
        }
        scanner.close();
        entrada.remove(entrada.get(0));

        /*System.out.println("Entrada");
        for(String item:entrada){
            System.out.println(item);
        }*/
        
        return entrada;

    }
}