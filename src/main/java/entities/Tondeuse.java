package entities;

import java.util.Arrays;

public class Tondeuse {
    Surface surface;
    int x, y;
    Orientation orientation;

    public Tondeuse(int x, int y, Orientation orientation) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        nouvelleSurfaceAexplorer(new Surface(0,0));
    }

    public Tondeuse(Surface surface, int x, int y, Orientation orientation) {
        nouvelleSurfaceAexplorer(surface);
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }

    public void nouvelleSurfaceAexplorer(Surface surface){
        this.surface = surface;
    }

    public void avancer (){
        switch (orientation){
            case N:
                y = (y+1)>surface.getyMax()?y:y+1;
                break;
            case E:
                x = (x+1)>surface.getxMax()?x:x+1;
                break;
            case W:
                x = (x-1)<0?x:x-1;
                break;
            case S:
                y = (y-1)<0?y:y-1;
                break;
        }
    }

    public void tournerADroite (){
        fairePivoter(90);
    }

    public void tournerAGauche (){
        fairePivoter(-90);
    }

    public void fairePivoter (int angle){
        switch (orientation){
            case N:
                orientation=angle>0?Orientation.E:Orientation.W;
                break;
            case E:
                orientation=angle>0?Orientation.S:Orientation.N;
                break;
            case W:
                orientation=angle>0?Orientation.N:Orientation.S;
                break;
            case S:
                orientation=angle>0?Orientation.W:Orientation.E;
                break;
        }
    }

    public void executerInstruction(String instructions){
        var listAction = instructions.split("");
        System.out.println("________________________________");
        System.out.println(" Position initiale => "+positionActuelle());
        Arrays.stream(listAction).forEach(action->{
            switch (action){
                case "A":
                    avancer();
                    break;
                case "G":
                    tournerAGauche();
                    break;
                case "D":
                    tournerADroite();
                    break;
            }
        });
        System.out.println(" Position finale => "+positionActuelle());
        System.out.println("________________________________");
        System.out.println();

    }

    public String positionActuelle(){
        return x+" "+y+" "+orientation.name();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public enum Orientation {
        N,E,W,S
    }

    public enum Instruction {
        D{
            @Override
            public String toString() {
                return "Tourner à droite";
            }
        },
        G{
            @Override
            public String toString() {
                return "Tourner à gauche";
            }
        },
        A{
            @Override
            public String toString() {
                return "Avancer d'une grille";
            }
        }
    }
}