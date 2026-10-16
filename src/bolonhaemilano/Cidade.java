package bolonhaemilano;

/**
 * O que e governado: a cidade-Estado, com seu ano atual e o jogador dono.
 */
public class Cidade {

    private String nome;
    private int anoAtual;
    private Jogador dono;

    public Cidade(String nome, Jogador dono) {
        this.nome = nome;
        this.dono = dono;
        this.anoAtual = 1400;
    }

    public void avancarAno() {
        anoAtual++;
    }

    public void exibirStatus() {
        System.out.println();
        System.out.println("----------- STATUS DA CIDADE -----------");
        System.out.println("Cidade: " + nome);
        System.out.println("Governante: " + dono.getNome() + " (" + dono.getTitulo() + ")");
        System.out.println("Ano: " + anoAtual);
    }
}
