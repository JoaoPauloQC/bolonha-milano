package bolonhaemilano;

import java.io.Console;
import java.lang.reflect.Method;
import java.util.Scanner;

/**
 * EXTRA: recursos visuais do terminal -- limpar a tela, pausar ate o ENTER e
 * cores (verde = sucesso, vermelho = erro, amarelo = destaque).
 *
 * So funcionam quando o jogo roda num terminal de verdade. Se a entrada vem de
 * um arquivo (java ... < jogadas.txt), nada disso e usado e a saida fica igual
 * a das etapas anteriores. Para desligar as cores: java -Dcores=false ...
 */
public class Tela {

    private static final String RESET = "\033[0m";
    private static final String VERDE = "\033[32m";
    private static final String VERMELHO = "\033[31m";
    private static final String AMARELO = "\033[33m";

    private boolean interativo;
    private boolean comCores;

    public Tela() {
        interativo = terminalInterativo();
        comCores = interativo && !"false".equalsIgnoreCase(System.getProperty("cores"));
    }

    public boolean isInterativo() {
        return interativo;
    }

    public void limparTela() {
        if (!interativo) {
            return;
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Segura o resultado na tela ate o jogador apertar ENTER.
     */
    public void pausar(Scanner scanner) {
        if (!interativo) {
            return;
        }
        System.out.println();
        System.out.print("Pressione ENTER para continuar...");
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }

    public String sucesso(String texto) {
        return colorir(VERDE, texto);
    }

    public String erro(String texto) {
        return colorir(VERMELHO, texto);
    }

    public String destaque(String texto) {
        return colorir(AMARELO, texto);
    }

    private String colorir(String cor, String texto) {
        if (!comCores) {
            return texto;
        }
        return cor + texto + RESET;
    }

    /**
     * Ate o Java 21, System.console() devolve null quando a entrada/saida e
     * redirecionada. A partir do Java 22 ele sempre existe e e preciso perguntar
     * isTerminal() -- chamado por reflexao para o codigo compilar no Java 17.
     */
    private static boolean terminalInterativo() {
        Console console = System.console();
        if (console == null) {
            return false;
        }
        try {
            Method isTerminal = Console.class.getMethod("isTerminal");
            return (Boolean) isTerminal.invoke(console);
        } catch (ReflectiveOperationException e) {
            return true; // Java 17 a 21: se ha console, e um terminal
        }
    }
}
