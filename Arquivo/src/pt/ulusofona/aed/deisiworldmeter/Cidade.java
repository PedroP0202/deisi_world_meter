public class Cidade {
    private String alfa2;
    private String nome;
    private String regiao;
    private double populacao;
    private double latitude;
    private double longitude;



    
    public Cidade(String alfa2, String nome, String regiao, double populacao, double latitude, double longitude) {
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
    public double getPopulacao() { return populacao; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }

    @Override
    public String toString() {
        return "Cidade{" +
                "alfa2='" + alfa2 + '\'' +
                ", nome='" + nome + '\'' +
                ", regiao='" + regiao + '\'' +
                ", populacao=" + populacao +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
