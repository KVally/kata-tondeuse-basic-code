import entities.Surface;
import entities.Tondeuse;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        System.out.println("Bonjour,\nQuel est le chemin de votre fichier d'instruction svp ?");
        Scanner stringScanner = new Scanner(System.in);
        String chemin = "C:\\Users\\ahmed\\Desktop\\FichierInstruction.txt" ;
        chemin = stringScanner.nextLine();
        try{
            // Le fichier d'entrée
            FileInputStream file = new FileInputStream(chemin);
            Scanner scanner = new Scanner(file);

            //renvoie true s'il y a une autre ligne à lire
            int nbLigne = 1;
            String _surface ="";
            List<String> positionInitialeTondeuse = new ArrayList<>();
            List<String> instructions = new ArrayList<>();
            while(scanner.hasNextLine()) {
                String strLigne = scanner.nextLine();
                if (nbLigne==1) _surface = strLigne;
                else {
                    if (nbLigne%2==0) positionInitialeTondeuse.add(strLigne);
                    else instructions.add(strLigne);
                }
                nbLigne++;
            }
            scanner.close();
            executerLesInstructions(_surface,positionInitialeTondeuse,instructions);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void executerLesInstructions(String _surface, List<String>positionInitialeTondeuses, List<String>instructions){
        System.out.println(_surface);
        System.out.println(positionInitialeTondeuses);
        System.out.println(instructions);
        var _s= Arrays.stream(_surface.replace(" ", "").split("")).map(Integer::parseInt)
                .collect(Collectors.toList());
        Surface surface = new Surface(_s.get(0),_s.get(1));
        List<Tondeuse> tondeuses = new ArrayList<>();
        positionInitialeTondeuses.forEach(init -> {
            tondeuses.add(stringVersTondeuse(surface,init));
        });
        int i = 0;
        for (Tondeuse tondeuse : tondeuses){
            tondeuse.executerInstruction(instructions.get(i));
            i++;
        }
    }

    public static Tondeuse stringVersTondeuse (Surface surface, String str_tondeuse) {
        str_tondeuse = str_tondeuse.replace(" ","");
        var _t = str_tondeuse.split("");
        return new Tondeuse(
                surface,
                Integer.parseInt(_t[0]),
                Integer.parseInt(_t[1]),
                Tondeuse.Orientation.valueOf(_t[2])
        );
    }

}