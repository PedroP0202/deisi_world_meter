package pt.ulusofona.aed.deisiworldmeter;

public class Pais {
    private int id;
    private String alfa2;
    private String alfa3;
    private String nome;

    public Pais(int id, String alfa2, String alfa3, String nome) {
        this.id = id;
        this.alfa2 = alfa2;
        this.alfa3 = alfa3;
        this.nome = nome;
    }

    public int getId() { return id; }
    public String getAlfa2() { return alfa2; }
    public String getAlfa3() { return alfa3; }
    public String getNome() { return nome; }

    @Override
    public String toString() {
        return id + " | " + alfa2 + " | " + alfa3 + " | " + nome;
    }
}
