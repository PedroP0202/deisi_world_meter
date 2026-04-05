package pt.ulusofona.aed.deisiworldmeter;

public class Populacao {
    private int id;
    private int ano;
    private int populacaoMasculina;
    private int populacaoFeminina;
    private double densidade;

    public Populacao(int id, int ano, int populacaoMasculina, int populacaoFeminina, double densidade) {
        this.id = id;
        this.ano = ano;
        this.populacaoMasculina = populacaoMasculina;
        this.populacaoFeminina = populacaoFeminina;
        this.densidade = densidade;
    }

    public int getId() { return id; }
    public int getAno() { return ano; }
    public int getPopulacaoMasculina() { return populacaoMasculina; }
    public int getPopulacaoFeminina() { return populacaoFeminina; }
    public double getDensidade() { return densidade; }

    @Override
    public String toString() {
        return id + " | " + ano + " | " + populacaoMasculina + " | " + populacaoFeminina + " | " + densidade;
    }
}
