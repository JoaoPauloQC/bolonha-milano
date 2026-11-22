package bolonhaemilano;

/**
 * EXTRA: foto de uma cidade no fechamento de um ano, para o historico anual.
 * So guarda dados (nao tem regra de negocio).
 */
public class RegistroAnual {

    private int ano;
    private String titulo;
    private int prestigio;
    private int florins;
    private int graos;
    private int servos;
    private int soldados;
    private int construcoes;

    public RegistroAnual(int ano, String titulo, int prestigio, int florins, int graos,
                         int servos, int soldados, int construcoes) {
        this.ano = ano;
        this.titulo = titulo;
        this.prestigio = prestigio;
        this.florins = florins;
        this.graos = graos;
        this.servos = servos;
        this.soldados = soldados;
        this.construcoes = construcoes;
    }

    public int getAno() {
        return ano;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getPrestigio() {
        return prestigio;
    }

    public int getFlorins() {
        return florins;
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
}
