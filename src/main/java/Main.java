import entities.Surface;
import entities.Tondeuse;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        FileInputStream file = obtenirFichier();
        Scanner scanner = new Scanner(file);

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
    }

    public static FileInputStream obtenirFichier() {
        System.out.println("Bonjour,\nQuel est le chemin de votre fichier d'instruction svp ?");
        Scanner stringScanner = new Scanner(System.in);
        String chemin = stringScanner.nextLine();
        FileInputStream file = null;

        try {
            file = new FileInputStream(chemin);
        } catch (FileNotFoundException e) {
            System.out.println("Erreur ==> "+e.getMessage());
            System.out.println("Nous allons récupérer un fichier par défaut.\n");
            file = obtenirFichierParDefaut();
        }
        return file;
    }

    public static FileInputStream obtenirFichierParDefaut() {
        try {
            String path = String.valueOf(Main.class.getClassLoader().getResource("FichierInstructions.txt"))
                    .replace("file:\\","").replace("file:/","");
            FileInputStream file = new FileInputStream(path);
            return file;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }

        public static void executerLesInstructions(String _surface, List<String>positionInitialeTondeuses, List<String>instructions){
        System.out.println("Surface => "+ _surface);
        System.out.println("Positions initiales des tondeuses => " +positionInitialeTondeuses);
        System.out.println("Instructuctions respectives des tondeuses => "+instructions);
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