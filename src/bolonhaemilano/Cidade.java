package bolonhaemilano;

import java.util.ArrayList;
import java.util.List;

/**
 * O que e governado: a cidade-Estado, com seu ano atual, o jogador dono,
 * os recursos (florins, terras, graos, servos, soldados e construcoes)
 * e as configuracoes de governo (taxa de imposto e nivel de justica).
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
    private int soldados;
    private int construcoes;
    private boolean colheitaFeita;
    private boolean impostosArrecadados;
    private List<RegistroAnual> historico; // EXTRA: historico anual

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
        this.soldados = 0;
        this.construcoes = 0;
        this.colheitaFeita = false;
        this.impostosArrecadados = false;
        this.historico = new ArrayList<>();
    }

    /**
     * EXTRA: restaura uma cidade salva em arquivo (usado pela Persistencia).
     */
    Cidade(String nome, Jogador dono, int anoAtual, int florins, int terras, int graos, int servos,
           int taxaImposto, String nivelJustica, int soldados, int construcoes,
           boolean colheitaFeita, boolean impostosArrecadados) {
        this(nome, dono);
        this.anoAtual = anoAtual;
        this.florins = florins;
        this.terras = terras;
        this.graos = graos;
        this.servos = servos;
        this.taxaImposto = taxaImposto;
        this.nivelJustica = nivelJustica;
        this.soldados = soldados;
        this.construcoes = construcoes;
        this.colheitaFeita = colheitaFeita;
        this.impostosArrecadados = impostosArrecadados;
    }

    // ---------- historico anual (EXTRA) ----------
    public void registrarAno(RegistroAnual registro) {
        historico.add(registro);
    }

    public List<RegistroAnual> getHistorico() {
        return historico;
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

    public int getSoldados() {
        return soldados;
    }

    public int getConstrucoes() {
        return construcoes;
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

    /**
     * Soma 1 ao ano e libera de novo a colheita e a arrecadacao.
     */
    public void avancarAno() {
        anoAtual++;
        colheitaFeita = false;
        impostosArrecadados = false;
    }

    // ---------- indicadores do ano (Etapa 7) ----------
    public boolean isColheitaFeita() {
        return colheitaFeita;
    }

    public void marcarColheitaFeita() {
        colheitaFeita = true;
    }

    public boolean isImpostosArrecadados() {
        return impostosArrecadados;
    }

    public void marcarImpostosArrecadados() {
        impostosArrecadados = true;
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

    // ---------- soldados ----------
    public void adicionarSoldados(int quantidade) {
        if (quantidade > 0) {
            soldados += quantidade;
        }
    }

    public boolean removerSoldados(int quantidade) {
        if (quantidade < 0 || quantidade > soldados) {
            return false;
        }
        soldados -= quantidade;
        return true;
    }

    // ---------- construcoes ----------
    public void adicionarConstrucoes(int quantidade) {
        if (quantidade > 0) {
            construcoes += quantidade;
        }
    }

    public boolean removerConstrucoes(int quantidade) {
        if (quantidade < 0 || quantidade > construcoes) {
            return false;
        }
        construcoes -= quantidade;
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
        System.out.println("Soldados: " + soldados);
        System.out.println("Construcoes: " + construcoes);
        System.out.println("Colheita deste ano: " + (colheitaFeita ? "feita" : "pendente"));
        System.out.println("Impostos deste ano: " + (impostosArrecadados ? "arrecadados" : "pendentes"));
    }
}
