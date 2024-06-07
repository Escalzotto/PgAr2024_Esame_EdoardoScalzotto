import java.util.ArrayList;
import java.util.List;

public class Giocatore {
    private String nomeGiocatore;
    private Ruolo ruoloGiocatore;
    private int puntiFeritaGiocatore;
    private List<Carta> manoGioco;
    private Arma armaUsataGiocatore;
    private Mazzo mazzo;

    public Giocatore(String nome, Ruolo ruolo, int puntiFerita) {
        this.nomeGiocatore = nome;
        this.ruoloGiocatore = ruolo;
        this.puntiFeritaGiocatore = puntiFerita;
        this.manoGioco = new ArrayList<>();
        this.mazzo = mazzo;
    }

    public String getNomeGiocatore() {
        return nomeGiocatore;
    }

    public void setNomeGiocatore(String nomeGiocatore) {
        this.nomeGiocatore = nomeGiocatore;
    }

    public Ruolo getRuoloGiocatore() {
        return ruoloGiocatore;
    }

    public void setRuoloGiocatore(Ruolo ruoloGiocatore) {
        this.ruoloGiocatore = ruoloGiocatore;
    }

    public int getPuntiFeritaGiocatore() {
        return puntiFeritaGiocatore;
    }

    public void setPuntiFeritaGiocatore(int puntiFeritaGiocatore) {
        this.puntiFeritaGiocatore = puntiFeritaGiocatore;
    }

    public List<Carta> getManoGioco() {
        return manoGioco;
    }

    public void setManoGioco(List<Carta> manoGioco) {
        this.manoGioco = manoGioco;
    }

    public void setNome(String nome) {
        this.nomeGiocatore = nome;
    }

    public String getNome() {
        if (ruoloGiocatore.equals(Ruolo.SCERIFFO)) {
            return nomeGiocatore + ", " + ruoloGiocatore;
        } else {
            return nomeGiocatore;
        }
    }

    public Arma getArmaUsataGiocatore() {
        return armaUsataGiocatore;
    }

    public void setArmaUsataGiocatore(Arma armaUsataGiocatore) {
        this.armaUsataGiocatore = armaUsataGiocatore;
    }

    public void scartaCarte() {
        while (manoGioco.size() > getPuntiFeritaGiocatore()) {
            Carta cartaDaScartare = manoGioco.remove(0);
            mazzo.scarta(cartaDaScartare); // Utilizza l'istanza del mazzo per scartare la carta
        }
    }

    public void pescaCarta(Carta pesca) {
        Carta cartaPescata = mazzo.pesca(); // Utilizza l'istanza del mazzo per pescare una carta
        if (cartaPescata != null) {
            manoGioco.add(cartaPescata);
        }
    }
}
