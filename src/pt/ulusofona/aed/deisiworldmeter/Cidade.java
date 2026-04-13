package pt.ulusofona.aed.deisiworldmeter;

public class Cidade {
    private String alfa2;
    private String nome;
    private String regiao;
    private int populacao;
    private double latitude;
    private double longitude;

    public Cidade(String alfa2, String nome, String regiao, int populacao, double latitude, double longitude) {
        this.alfa2 = alfa2;
        this.nome = nome;
        this.regiao = regiao;
        this.populacao = populacao;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getAlfa2() { return alfa2; }
    public String getNome() { return nome; }
    public String getRegiao() { return regiao; }
    public int getPopulacao() { return populacao; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }

    @Override
    public String toString() {
        return nome + " | " + alfa2.toUpperCase() + " | " + regiao + " | " + populacao + " | (" + latitude + "," + longitude + ")";
    }
}
