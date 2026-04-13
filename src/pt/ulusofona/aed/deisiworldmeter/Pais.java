package pt.ulusofona.aed.deisiworldmeter;

public class Pais {
    private int id;
    private String alfa2;
    private String alfa3;
    private String nome;
    private int numCidades;

    public Pais(int id, String alfa2, String alfa3, String nome) {
        this.id = id;
        this.alfa2 = alfa2;
        this.alfa3 = alfa3;
        this.nome = nome;
        this.numCidades = 0;
    }

    public int getId() { return id; }
    public String getAlfa2() { return alfa2; }
    public String getAlfa3() { return alfa3; }
    public String getNome() { return nome; }
    public int getNumCidades() { return numCidades; }

    public void addCidade() {
        this.numCidades++;
    }

    @Override
    public String toString() {
        if (id > 700) {
            return nome + " | " + id + " | " + alfa2.toUpperCase() + " | " + alfa3.toUpperCase() + " | " + numCidades;
        }
        return nome + " | " + id + " | " + alfa2.toUpperCase() + " | " + alfa3.toUpperCase();
    }
}
