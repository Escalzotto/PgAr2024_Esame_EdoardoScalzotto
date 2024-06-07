import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mazzo {
    private static List<Carta> mazzoMescolato;
    private static List<Carta> mazzoScarti;

    public Mazzo() {
        List<Carta> carteEquipaggiabili = new ArrayList<>();
        List<Carta> carteGiocaScarta = new ArrayList<>();
        mazzoScarti = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            carteEquipaggiabili.add(new Carta("Schofield", TipoCarta.EQUIPAGGIABILE));
        }
        carteEquipaggiabili.add(new Carta("Remington", TipoCarta.EQUIPAGGIABILE));
        carteEquipaggiabili.add(new Carta("Rev.Carabine", TipoCarta.EQUIPAGGIABILE));
        carteEquipaggiabili.add(new Carta("Winchester", TipoCarta.EQUIPAGGIABILE));

        for (int i = 0; i < 50; i++) {
            carteGiocaScarta.add(new Carta("BANG!", TipoCarta.GIOCA_SCARTA));
        }
        for (int i = 0; i < 24; i++) {
            carteGiocaScarta.add(new Carta("Mancato!", TipoCarta.GIOCA_SCARTA));
        }

        mazzoMescolato = new ArrayList<>(carteEquipaggiabili);
        mazzoMescolato.addAll(carteGiocaScarta);

        mescola();
    }

    public void mescola() {
        Collections.shuffle(mazzoMescolato);
    }

    public static Carta pesca() {
        if (mazzoMescolato.isEmpty()) {
            return null;
        }
        return mazzoMescolato.removeFirst();
    }

    public static void scarta(Carta carta) {
        mazzoScarti.add(carta);
    }

    public List<Carta> getCarte() {
        return mazzoMescolato;
    }

    public void setCarte(List<Carta> carte) {
        mazzoMescolato = carte;
    }
}
