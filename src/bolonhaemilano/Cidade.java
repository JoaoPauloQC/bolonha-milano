package bolonhaemilano;

/**
 * O que e governado: a cidade-Estado, com seu ano atual, o jogador dono
 * e os recursos da economia (florins, terras, graos e servos).
 *
 * Padrao seguro: adicionar* sempre funciona; remover* devolve false (e nao
 * altera nada) se nao houver quantidade suficiente. Nenhum recurso fica negativo.
 */
public class Cidade {

    private String nome;
    private int anoAtual;
    private Jogador dono;
    private int florins;
    private int terras;
    private int graos;
    private int servos;
    private int taxaImposto;
    private String nivelJustica;

    public Cidade(String nome, Jogador dono) {
        this.nome = nome;
        this.dono = dono;
        this.anoAtual = 1400;
        this.florins = 1000;
        this.terras = 10000;
        this.graos = 500;
        this.servos = 200;
        this.taxaImposto = 10;
        this.nivelJustica = "Moderada";
    }

    public String getNome() {
        return nome;
    }

    public int getAnoAtual() {
        return anoAtual;
    }

    public Jogador getDono() {
        return dono;
    }

    public int getFlorins() {
        return florins;
    }

    public int getTerras() {
        return terras;
    }

    public int getGraos() {
        return graos;
    }

    public int getServos() {
        return servos;
    }

    // ---------- configuracoes de governo (getter/setter simples) ----------
    public int getTaxaImposto() {
        return taxaImposto;
    }

    public void setTaxaImposto(int taxaImposto) {
        this.taxaImposto = taxaImposto;
    }

    public String getNivelJustica() {
        return nivelJustica;
    }

    public void setNivelJustica(String nivelJustica) {
        this.nivelJustica = nivelJustica;
    }

    public void avancarAno() {
        anoAtual++;
    }

    // ---------- florins ----------
    public void adicionarFlorins(int quantidade) {
        if (quantidade > 0) {
            florins += quantidade;
        }
    }

    public boolean removerFlorins(int quantidade) {
        if (quantidade < 0 || quantidade > florins) {
            return false;
        }
        florins -= quantidade;
        return true;
    }

    // ---------- terras ----------
    public void adicionarTerras(int quantidade) {
        if (quantidade > 0) {
            terras += quantidade;
        }
    }

    public boolean removerTerras(int quantidade) {
        if (quantidade < 0 || quantidade > terras) {
            return false;
        }
        terras -= quantidade;
        return true;
    }

    // ---------- graos ----------
    public void adicionarGraos(int quantidade) {
        if (quantidade > 0) {
            graos += quantidade;
        }
    }

    public boolean removerGraos(int quantidade) {
        if (quantidade < 0 || quantidade > graos) {
            return false;
        }
        graos -= quantidade;
        return true;
    }

    // ---------- servos ----------
    public void adicionarServos(int quantidade) {
        if (quantidade > 0) {
            servos += quantidade;
        }
    }

    public boolean removerServos(int quantidade) {
        if (quantidade < 0 || quantidade > servos) {
            return false;
        }
        servos -= quantidade;
        return true;
    }

    public void exibirStatus() {
        System.out.println();
        System.out.println("----------- STATUS DA CIDADE -----------");
        System.out.println("Cidade: " + nome);
        System.out.println("Governante: " + dono.getNome() + " (" + dono.getTitulo() + ")");
        System.out.println("Ano: " + anoAtual);
        System.out.println("Florins: " + florins);
        System.out.println("Terras: " + terras + " hectares");
        System.out.println("Graos em estoque: " + graos);
        System.out.println("Servos: " + servos);
        System.out.println("Taxa de imposto: " + taxaImposto + "%");
        System.out.println("Nivel de justica: " + nivelJustica);
    }
}
