package bolonhaemilano;

/**
 * Quem governa a cidade: nome e nivel do titulo de nobreza.
 * O Jogador sabe a ORDEM dos titulos; quem decide QUANDO promover e a Nobreza.
 */
public class Jogador {

    private static final String[] TITULOS = {
        "Cavaleiro", "Barao", "Conde", "Marques", "Duque", "Rei/Rainha"
    };

    private String nome;
    private int nivelTitulo;

    public Jogador(String nome) {
        this.nome = nome;
        this.nivelTitulo = 0; // Cavaleiro
    }

    /**
     * EXTRA: restaura um jogador salvo em arquivo (usado pela Persistencia).
     * Um nivel invalido volta a ser Cavaleiro.
     */
    Jogador(String nome, int nivelTitulo) {
        this(nome);
        if (nivelTitulo >= 0 && nivelTitulo < TITULOS.length) {
            this.nivelTitulo = nivelTitulo;
        }
    }

    public String getNome() {
        return nome;
    }

    public String getTitulo() {
        return TITULOS[nivelTitulo];
    }

    public int getNivelTitulo() {
        return nivelTitulo;
    }

    /**
     * Sobe um titulo. Devolve false se ja e Rei/Rainha.
     */
    public boolean promover() {
        if (ehMonarca()) {
            return false;
        }
        nivelTitulo++;
        return true;
    }

    public boolean ehMonarca() {
        return nivelTitulo == TITULOS.length - 1;
    }
}
