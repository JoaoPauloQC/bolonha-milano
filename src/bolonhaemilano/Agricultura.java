package bolonhaemilano;

/**
 * Processa a colheita do ano e a distribuicao de alimentos em nome de uma Cidade.
 * Nao guarda nenhuma Cidade: cada operacao recebe a cidade como parametro.
 */
public class Agricultura {

    private static final int HECTARES_POR_SERVO = 5;
    private static final int RENDIMENTO_POR_HECTARE = 2;
    private static final int SERVOS_POR_GRAO = 5;
    private static final int CRESCIMENTO_PERCENTUAL = 5;
    private static final int PERCENTUAL_MINIMO = 20;
    private static final int PERCENTUAL_MAXIMO = 80;

    /**
     * Colhe a safra do ano. Devolve quantos hectares foram cultivados.
     */
    public int processarColheita(Cidade cidade) {
        int hectares = Math.min(cidade.getTerras(), cidade.getServos() * HECTARES_POR_SERVO);
        int sementes = hectares / 2;
        if (sementes > cidade.getGraos()) {
            // faltam graos para semear tudo: reduz a area ao que da para plantar
            sementes = cidade.getGraos();
            hectares = sementes * 2;
        }
        cidade.removerGraos(sementes);
        int producao = hectares * RENDIMENTO_POR_HECTARE;
        cidade.adicionarGraos(producao);
        return hectares;
    }

    /**
     * Distribui um percentual (20 a 80) do estoque de graos para a populacao.
     * Se o alimento cobre a necessidade (servos / 5), a populacao cresce 5%;
     * senao, perde o deficit em servos (nunca abaixo de zero).
     */
    public boolean distribuirAlimentos(Cidade cidade, int percentual) {
        if (percentual < PERCENTUAL_MINIMO || percentual > PERCENTUAL_MAXIMO) {
            return false;
        }
        int alimentoDistribuido = (cidade.getGraos() * percentual) / 100;
        cidade.removerGraos(alimentoDistribuido);
        int necessidade = cidade.getServos() / SERVOS_POR_GRAO;
        if (alimentoDistribuido >= necessidade) {
            int crescimento = cidade.getServos() * CRESCIMENTO_PERCENTUAL / 100;
            cidade.adicionarServos(crescimento);
        } else {
            int deficit = necessidade - alimentoDistribuido;
            cidade.removerServos(Math.min(deficit, cidade.getServos()));
        }
        return true;
    }
}
