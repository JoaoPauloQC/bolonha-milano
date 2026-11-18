package bolonhaemilano;

import java.util.ArrayList;
import java.util.List;

/**
 * Transforma o modo livre em rodadas: guarda as cidades (1 a 4), controla de
 * quem e a vez, fecha o ano, decide quando o jogo termina e quem venceu.
 */
public class Partida {

    public static final int MAXIMO_JOGADORES = 4;
    private static final int ANO_FINAL = 1410;

    private List<Cidade> cidades;
    private int indiceAtual;

    public Partida() {
        cidades = new ArrayList<>();
        indiceAtual = 0;
    }

    /**
     * Coloca a cidade na partida. Recusa a quinta cidade.
     */
    public boolean adicionarCidade(Cidade cidade) {
        if (cidades.size() >= MAXIMO_JOGADORES) {
            return false;
        }
        cidades.add(cidade);
        return true;
    }

    public List<Cidade> getCidades() {
        return cidades;
    }

    public Cidade getCidadeAtual() {
        return cidades.get(indiceAtual);
    }

    public int getAnoAtual() {
        return cidades.get(0).getAnoAtual();
    }

    public int getAnoFinal() {
        return ANO_FINAL;
    }

    /**
     * Passa a vez para o proximo jogador. Devolve true se o ano acabou
     * (quem encerrou era o ultimo da rodada).
     */
    public boolean passarTurno() {
        indiceAtual++;
        if (indiceAtual >= cidades.size()) {
            indiceAtual = 0;
            return true; // o ano acabou
        }
        return false;
    }

    /**
     * Avanca o ano de todas as cidades e verifica promocoes.
     * Devolve a lista das cidades cujo governante foi promovido.
     */
    public List<Cidade> encerrarAno(Nobreza nobreza) {
        List<Cidade> promovidas = new ArrayList<>();
        for (Cidade cidade : cidades) {
            cidade.avancarAno();
            if (nobreza.verificarPromocao(cidade)) {
                promovidas.add(cidade);
            }
        }
        return promovidas;
    }

    /**
     * O jogo termina se alguem virou Rei/Rainha ou se o ano final ja passou.
     */
    public boolean terminou() {
        for (Cidade cidade : cidades) {
            if (cidade.getDono().ehMonarca()) {
                return true;
            }
        }
        return getAnoAtual() > ANO_FINAL;
    }

    /**
     * Vencedor: titulo mais alto; em caso de empate, maior prestigio.
     */
    public Cidade determinarVencedor(Nobreza nobreza) {
        Cidade vencedor = null;
        for (Cidade cidade : cidades) {
            if (vencedor == null || estaNaFrente(cidade, vencedor, nobreza)) {
                vencedor = cidade;
            }
        }
        return vencedor;
    }

    private boolean estaNaFrente(Cidade a, Cidade b, Nobreza nobreza) {
        int nivelA = a.getDono().getNivelTitulo();
        int nivelB = b.getDono().getNivelTitulo();
        if (nivelA != nivelB) {
            return nivelA > nivelB;
        }
        return nobreza.calcularPrestigio(a) > nobreza.calcularPrestigio(b);
    }
}
