public class Jogador {
    private String nome;
    private String titulo;

    public Jogador(String nome, String titulo){
        this.nome = nome;
        this.titulo = titulo;
    }
    public Jogador(String nome){
        this.nome = nome;
        this.titulo = "jogador";
    }
    
    public String getNome(){
        return this.nome;
    }

}
