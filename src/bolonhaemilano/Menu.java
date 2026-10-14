package bolonhaemilano;

import java.util.Scanner;

/**
 * Controla o laco principal do jogo: exibe as opcoes, le e valida a escolha
 * do jogador e decide o que fazer com ela.
 */
public class Menu {

    private Scanner scanner;
    private boolean emExecucao;

    public Menu() {
        scanner = new Scanner(System.in);
        emExecucao = true;
    }

    public void executar() {
        while (emExecucao) {
            exibirOpcoes();
            int opcao = lerOpcao();
            processarOpcao(opcao);
        }
        scanner.close();
    }

    private void exibirOpcoes() {
        System.out.println();
        System.out.println("==========================================");
        System.out.println("             BOLONHA E MILANO");
        System.out.println("  Um jogo de estrategia economica medieval");
        System.out.println("==========================================");
        System.out.println("1 - Novo jogo");
        System.out.println("2 - Sobre o jogo");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opcao: ");
    }

    private int lerOpcao() {
        if (!scanner.hasNextLine()) {
            return 0; // fim da entrada: encerra o jogo
        }
        String linha = scanner.nextLine().trim();
        try {
            return Integer.parseInt(linha);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                System.out.println("Novo jogo: em construcao (disponivel na Etapa 2).");
                break;
            case 2:
                exibirSobre();
                break;
            case 0:
                System.out.println("Ate a proxima!");
                emExecucao = false;
                break;
            default:
                System.out.println("Opcao invalida. Tente novamente.");
                break;
        }
    }

    private void exibirSobre() {
        System.out.println();
        System.out.println("Bolonha e Milano e um jogo de estrategia economica e politica");
        System.out.println("ambientado na Italia medieval. Cada jogador governa uma");
        System.out.println("cidade-Estado e decide como usar seus recursos para desenvolver");
        System.out.println("o territorio e conquistar titulos de nobreza, ate Rei ou Rainha.");
    }
}
