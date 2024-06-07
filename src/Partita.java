import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Partita {
    private List<Giocatore> giocatori;
    private List<Giocatore> giocatoriEliminati;
    private Mazzo mazzo;
    private List<Carta> mazzoScarti;
    private Giocatore giocatoreCorrente;

    public Partita(int numeroGiocatori) {
        this.giocatori = creaGiocatori(numeroGiocatori);
        this.giocatoriEliminati = new ArrayList<>();
        this.mazzo = new Mazzo();
        this.mazzoScarti = new ArrayList<>();
    }

    public List<Giocatore> getGiocatori() {
        return giocatori;
    }

    public Giocatore getGiocatoreCorrente() {
        return giocatoreCorrente;
    }

    private List<Giocatore> creaGiocatori(int numeroGiocatori) {
        List<Giocatore> giocatori = new ArrayList<>();
        giocatori.add(new Giocatore("Sceriffo", Ruolo.SCERIFFO, 5));

        switch (numeroGiocatori) {
            case 4:
                giocatori.add(new Giocatore("Rinnegato", Ruolo.RINNEGATO, 4));
                giocatori.add(new Giocatore("Fuorilegge", Ruolo.FUORILEGGE, 4));
                giocatori.add(new Giocatore("Fuorilegge", Ruolo.FUORILEGGE, 4));
                break;
            case 5:
                giocatori.add(new Giocatore("Vice", Ruolo.VICE, 4));
                giocatori.add(new Giocatore("Vice", Ruolo.VICE, 4));
                giocatori.add(new Giocatore("Rinnegato", Ruolo.RINNEGATO, 4));
                giocatori.add(new Giocatore("Fuorilegge", Ruolo.FUORILEGGE, 4));
                giocatori.add(new Giocatore("Fuorilegge", Ruolo.FUORILEGGE, 4));
                break;
            case 6:
                giocatori.add(new Giocatore("Vice", Ruolo.VICE, 4));
                giocatori.add(new Giocatore("Vice", Ruolo.VICE, 4));
                giocatori.add(new Giocatore("Rinnegato", Ruolo.RINNEGATO, 4));
                giocatori.add(new Giocatore("Fuorilegge", Ruolo.FUORILEGGE, 4));
                giocatori.add(new Giocatore("Fuorilegge", Ruolo.FUORILEGGE, 4));
                giocatori.add(new Giocatore("Fuorilegge", Ruolo.FUORILEGGE, 4));
                break;
            case 7:
                giocatori.add(new Giocatore("Vice", Ruolo.VICE, 4));
                giocatori.add(new Giocatore("Vice", Ruolo.VICE, 4));
                giocatori.add(new Giocatore("Vice", Ruolo.VICE, 4));
                giocatori.add(new Giocatore("Vice", Ruolo.VICE, 4));
                giocatori.add(new Giocatore("Rinnegato", Ruolo.RINNEGATO, 4));
                giocatori.add(new Giocatore("Fuorilegge", Ruolo.FUORILEGGE, 4));
                giocatori.add(new Giocatore("Fuorilegge", Ruolo.FUORILEGGE, 4));
                giocatori.add(new Giocatore("Fuorilegge", Ruolo.FUORILEGGE, 4));
                break;
            default:
                throw new IllegalArgumentException("Numero di giocatori non supportato");
        }

        return giocatori;
    }

    public void iniziaPartita() {
        mazzo.mescola();
        for (Giocatore giocatore : giocatori) {
            for (int i = 0; i < giocatore.getPuntiFeritaGiocatore(); i++) {
                giocatore.pescaCarta(Mazzo.pesca());
            }
        }
        giocatoreCorrente = giocatori.get(0);
    }

    public void turno() {
        if (giocatoreCorrente.getPuntiFeritaGiocatore() <= 0) {
            System.out.println(giocatoreCorrente.getNomeGiocatore() + " è stato eliminato!");
            giocatoriEliminati.add(giocatoreCorrente);
            int indexCorrente = giocatori.indexOf(giocatoreCorrente);
            giocatori.remove(indexCorrente);
        }

        for (int i = 0; i < 2; i++) {
            pescaCarta(giocatoreCorrente);
        }

        List<Carta> mano = new ArrayList<>(giocatoreCorrente.getManoGioco());
        for (Carta carta : mano) {
            if (carta.getNomeCarta().equals("Bang!")) {
                Giocatore bersaglio = scegliBersaglio();
                if (bersaglio != null && calcolaDistanza(giocatoreCorrente, bersaglio) <= giocatoreCorrente.getArmaUsataGiocatore().getDistanzaTiro()) {
                    sparare(giocatoreCorrente, bersaglio);
                    giocatoreCorrente.getManoGioco().remove(carta);
                    mazzoScarti.add(carta);
                    break;
                } else {
                    System.out.println("Il bersaglio è fuori dalla portata dell'arma o non è stato selezionato.");
                }
            }
        }

        giocatoreCorrente.scartaCarte();

        if (!giocatori.isEmpty()) {
            int indexCorrente = giocatori.indexOf(giocatoreCorrente);
            giocatoreCorrente = giocatori.get((indexCorrente + 1) % giocatori.size());
        } else {
            terminaPartita();
        }
    }

    private Giocatore scegliBersaglio() {
        List<Giocatore> bersagliPossibili = new ArrayList<>();

        for (Giocatore giocatore : giocatori) {
            if (!giocatore.equals(giocatoreCorrente) && giocatore.getPuntiFeritaGiocatore() > 0) {
                bersagliPossibili.add(giocatore);
            }
        }

        if (bersagliPossibili.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Non ci sono bersagli disponibili.");
            return null;
        }

        String[] opzioni = new String[bersagliPossibili.size()];
        for (int i = 0; i < bersagliPossibili.size(); i++) {
            Giocatore bersaglio = bersagliPossibili.get(i);
            opzioni[i] = bersaglio.getNomeGiocatore() + " (Punti Ferita: " + bersaglio.getPuntiFeritaGiocatore() + ")";
        }

        String scelta = (String) JOptionPane.showInputDialog(
                null,
                "Scegli un bersaglio:",
                "Selezione Bersaglio",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opzioni,
                opzioni[0]
        );

        for (int i = 0; i < bersagliPossibili.size(); i++) {
            if (opzioni[i].equals(scelta)) {
                return bersagliPossibili.get(i);
            }
        }

        return null;
    }

    public void sparare(Giocatore attaccante, Giocatore difensore) {
        boolean mancato = false;
        for (Carta carta : difensore.getManoGioco()) {
            if (carta.getNomeCarta().equals("Mancato")) {
                mancato = true;
                difensore.getManoGioco().remove(carta);
                mazzoScarti.add(carta);
                System.out.println(difensore.getNomeGiocatore() + " ha usato una carta Mancato!");
                break;
            }
        }
        if (!mancato) {
            difensore.setPuntiFeritaGiocatore(difensore.getPuntiFeritaGiocatore() - 1);
            System.out.println(difensore.getNomeGiocatore() + " ha subito 1 punto di danno!");
        } else {
            System.out.println(difensore.getNomeGiocatore() + " ha evitato il danno!");
        }
    }

    public int calcolaDistanza(Giocatore giocatore1, Giocatore giocatore2) {
        int index1 = giocatori.indexOf(giocatore1);
        int index2 = giocatori.indexOf(giocatore2);

        // Calcola la distanza in senso orario
        int distanzaOraria = 0;
        for (int i = (index1 + 1) % giocatori.size(); i != index2; i = (i + 1) % giocatori.size()) {
            if (giocatori.get(i).getPuntiFeritaGiocatore() > 0) {
                distanzaOraria++;
            }
        }

        // Calcola la distanza in senso antiorario
        int distanzaAntioraria = 0;
        for (int i = (index1 - 1 + giocatori.size()) % giocatori.size(); i != index2; i = (i - 1 + giocatori.size()) % giocatori.size()) {
            if (giocatori.get(i).getPuntiFeritaGiocatore() > 0) {
                distanzaAntioraria++;
            }
        }

        return Math.min(distanzaOraria, distanzaAntioraria) + 1;
    }

    public void pescaCarta(Giocatore giocatore) {
        if (!mazzo.getCarte().isEmpty()) {
            Carta cartaPescata = mazzo.pesca();
            giocatore.pescaCarta(cartaPescata);
            System.out.println(giocatore.getNomeGiocatore() + " ha pescato una carta: " + cartaPescata);
        } else {
            System.out.println("Il mazzo è vuoto, mescolando le carte dal mazzo degli scarti...");
            mazzo.setCarte(new ArrayList<>(mazzoScarti));
            mazzoScarti.clear();
            mazzo.mescola();
            Carta cartaPescata = mazzo.pesca();
            giocatore.pescaCarta(cartaPescata);
            System.out.println(giocatore.getNomeGiocatore() + " ha pescato una carta: " + cartaPescata);
        }
    }

    public void scartaCarta(Giocatore giocatore, Carta carta) {
        if (giocatore.getManoGioco().contains(carta)) {
            mazzoScarti.add(carta);
            giocatore.getManoGioco().remove(carta);
            System.out.println(giocatore.getNomeGiocatore() + " ha scartato " + carta);
        } else {
            System.out.println("Il giocatore " + giocatore.getNomeGiocatore() + " non possiede questa carta nella mano.");
        }
    }

    public void visualizzaStatoGiocatori(Partita partita) {
        Scanner scanner = new Scanner(System.in);

        for (Giocatore giocatore : giocatori) {
            String ruolo = giocatore.getRuoloGiocatore().toString();
            String nome;
            if (giocatore.getRuoloGiocatore() == Ruolo.SCERIFFO) {
                System.out.print("Inserisci un nome per lo sceriffo: " + ruolo + ": ");
            } else {
                System.out.print("Inserisci un nome per il giocatore: " + ruolo + ": ");
            }
            System.out.flush();
            nome = scanner.nextLine();
            giocatore.setNome(nome);

            System.out.println("Nome giocatore: " + giocatore.getNome());
            System.out.println("Punti Ferita: " + giocatore.getPuntiFeritaGiocatore());
            System.out.println("----------------------");
        }
    }

    public boolean terminaPartita() {
        boolean sceriffoVivo = false;
        boolean rinnegatoVivo = false;
        boolean fuorileggiVivi = false;

        for (Giocatore giocatore : giocatori) {
            if (giocatore.getRuoloGiocatore() == Ruolo.SCERIFFO) {
                sceriffoVivo = true;
            } else if (giocatore.getRuoloGiocatore() == Ruolo.RINNEGATO) {
                rinnegatoVivo = true;
            } else if (giocatore.getRuoloGiocatore() == Ruolo.FUORILEGGE) {
                fuorileggiVivi = true;
            }
        }

        if (!sceriffoVivo && !fuorileggiVivi) {
            System.out.println("Il Rinnegato ha vinto!");
        } else if (!sceriffoVivo && !rinnegatoVivo) {
            System.out.println("I Fuorilegge hanno vinto!");
        } else if (sceriffoVivo && !fuorileggiVivi && !rinnegatoVivo) {
            System.out.println("Lo Sceriffo ha vinto!");
        }

        for (Giocatore eliminato : giocatoriEliminati) {
            System.out.println(eliminato.getNomeGiocatore() + " era un " + eliminato.getRuoloGiocatore() + " ed è stato eliminato.");
        }

        return true;
    }
}
