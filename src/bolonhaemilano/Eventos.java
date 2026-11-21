package bolonhaemilano;

import java.util.Random;

/**
 * EXTRA (melhoria opcional): eventos aleatorios que acontecem em cada cidade
 * no comeco de um novo ano. Da sentido aos soldados (defendem contra
 * saqueadores) e ao estoque de graos (ratos).
 *
 * Sorteio de 0 a 99:
 *   0-14  Ratos: perdem-se 20% dos graos
 *  15-24  Peste: morrem 10% dos servos
 *  25-39  Saqueadores: sem 1 soldado para cada 10 servos, levam 25% dos florins
 *  40-54  Boa safra: +20% de graos
 *  55-64  Imigrantes: +5% de servos
 *  65-99  Ano tranquilo
 *
 * Nao guarda nenhuma Cidade (dependencia, como as outras classes de servico).
 */
public class Eventos {

    private static final int SERVOS_POR_SOLDADO_NA_DEFESA = 10;

    private Random random;

    public Eventos() {
        this(new Random());
    }

    public Eventos(Random random) {
        this.random = random;
    }

    /**
     * Sorteia e aplica um evento na cidade. Devolve a descricao do que aconteceu.
     */
    public String sortearEvento(Cidade cidade) {
        return aplicarEvento(cidade, random.nextInt(100));
    }

    /**
     * Aplica o evento correspondente ao numero sorteado (0 a 99).
     * Publico para os testes poderem escolher o evento.
     */
    public String aplicarEvento(Cidade cidade, int sorteio) {
        if (sorteio < 15) {
            int perda = cidade.getGraos() * 20 / 100;
            cidade.removerGraos(perda);
            return "Ratos invadiram os celeiros: -" + perda + " graos.";
        }
        if (sorteio < 25) {
            int mortos = cidade.getServos() * 10 / 100;
            cidade.removerServos(mortos);
            return "Uma peste atingiu a cidade: -" + mortos + " servos.";
        }
        if (sorteio < 40) {
            if (cidadeEstaDefendida(cidade)) {
                return "Saqueadores atacaram, mas os " + cidade.getSoldados() + " soldados os expulsaram!";
            }
            int roubo = cidade.getFlorins() * 25 / 100;
            cidade.removerFlorins(roubo);
            return "Saqueadores atacaram a cidade sem defesa: -" + roubo + " florins.";
        }
        if (sorteio < 55) {
            int ganho = cidade.getGraos() * 20 / 100;
            cidade.adicionarGraos(ganho);
            return "Clima favoravel, os celeiros renderam mais: +" + ganho + " graos.";
        }
        if (sorteio < 65) {
            int chegaram = cidade.getServos() * 5 / 100;
            cidade.adicionarServos(chegaram);
            return "Imigrantes chegaram atraidos pela cidade: +" + chegaram + " servos.";
        }
        return "Ano tranquilo, nada de especial aconteceu.";
    }

    /**
     * A cidade esta defendida se tem pelo menos 1 soldado para cada 10 servos.
     */
    public boolean cidadeEstaDefendida(Cidade cidade) {
        return cidade.getSoldados() * SERVOS_POR_SOLDADO_NA_DEFESA >= cidade.getServos()
                && cidade.getSoldados() > 0;
    }
}
