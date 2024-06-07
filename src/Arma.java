public class Arma {
    private String tipoArma;
    private int distanzaTiro;
    private int danno;

    public Arma(String tipoArma, int distanzaTiro, int danno) {
        this.tipoArma = tipoArma;
        this.distanzaTiro = distanzaTiro;
        this.danno = danno;
    }

    public String getTipoArma() {
        return tipoArma;
    }

    public void setTipoArma(String tipoArma) {
        this.tipoArma = tipoArma;
    }

    public int getDistanzaTiro() {
        return distanzaTiro;
    }

    public void setDistanzaTiro(int distanzaTiro) {
        this.distanzaTiro = distanzaTiro;
    }

    public int getDanno() {
        return danno;
    }

    public void setDanno(int danno) {
        this.danno = danno;
    }
}
