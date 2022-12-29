package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TondeuseTest {

    Surface surface;
    Tondeuse tondeuse;

    @BeforeEach
    void setUp() {
        surface = new Surface(4,9);
        tondeuse = new Tondeuse(surface,0,0, Tondeuse.Orientation.N);
    }

    @Test
    void avancer() {
        tondeuse.avancer();
        assertEquals(1,tondeuse.getY());
    }

    @Test
    void tournerADroite() {
        tondeuse.tournerADroite();
        assertEquals(Tondeuse.Orientation.E,tondeuse.getOrientation());
    }

    @Test
    void tournerAGauche() {
        tondeuse.tournerAGauche();
        assertEquals(Tondeuse.Orientation.W,tondeuse.getOrientation());
    }

    @Test
    void avancerDeDeuxGrillesTournerADroiteEtAvancerDe3Grilles(){
        String instruction = "AADAAA";
        tondeuse.executerInstruction(instruction);
        String expected = "3 2 E";
        assertEquals(expected,tondeuse.positionActuelle());
    }

    @Test
    void verifierQueLaTondeuseNeBougeraPasSiLaSurfaceNeLuiPermetPas() {
        String instruction = "GADAGAA";
        tondeuse.executerInstruction(instruction);
        String expected = "0 1 W";
        assertEquals(expected,tondeuse.positionActuelle());
    }

    @Test
    void verifierQueLaTondeuseSeTrouveAUnePositionPrecise() {
        tondeuse = new Tondeuse(new Surface(5,5),1,2, Tondeuse.Orientation.N);
        String instruction = "GAGAGAGAA";
        tondeuse.executerInstruction(instruction);
        String expected = "1 3 N";
        assertEquals(expected,tondeuse.positionActuelle());
    }

    @Test
    void verifierQueLaTondeuseSeTrouveAUnePositionPrecise2() {
        tondeuse = new Tondeuse(new Surface(5,5),3,3, Tondeuse.Orientation.E);
        String instruction = "AADAADADDA";
        tondeuse.executerInstruction(instruction);
        String expected = "5 1 E";
        assertEquals(expected,tondeuse.positionActuelle());
    }
}