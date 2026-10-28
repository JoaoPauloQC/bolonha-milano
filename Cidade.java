public class Cidade {
    private String nome;
    private int anoAtual;
    private Jogador dono;

    public Cidade(String nome, int anoAtual, Jogador jogador){
        this.nome = nome;
        this.anoAtual = anoAtual;
        this.dono = jogador;
    }

    public void avancarAno(){
        anoAtual = anoAtual +1;
    }

    public void exibirStatus(){
        System.out.println("Nome: "+nome + "\nAno Atual: "+ anoAtual+ "\nDono: " + dono.getNome());
    }
}
