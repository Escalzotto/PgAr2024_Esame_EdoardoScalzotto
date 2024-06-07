public class Carta {
    private String nomeCarta;
    private TipoCarta tipo;

    public Carta(String nome, TipoCarta tipo) {
        this.nomeCarta = nome;
        this.tipo = tipo;
    }

    public String getNomeCarta() {
        return nomeCarta;
    }

    public TipoCarta getTipo() {
        return tipo;
    }
}
